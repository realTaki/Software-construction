/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

	private final Set<L> vertices = new HashSet<>();
	private final List<Edge<L>> edges = new ArrayList<>();

	// Abstraction function:
	// 存储边来记录一个图

	// Representation invariant:
	// 点应当为集合来防止重复，边记录两个点：source和target，还有两者之间的距离
	// 此处应当防止边重复，即source和target不能同时相同

	// Safety from rep exposure:
	// 两个字段（顶点和边）用final和private修饰

	// constructor
	public ConcreteEdgesGraph() {
		this.edges.clear();
		this.vertices.clear();

		checkRep();
	}

	public ConcreteEdgesGraph(Set<L> vertices, List<Edge<L>> edges) {
		this.edges.addAll(edges);
		this.vertices.addAll(vertices);

		checkRep();
	}

	// checkRep
	private void checkRep() {
		// 将List导入set，比较大小来检查重复
		Set<Edge<L>> edgeSet = new HashSet<>(this.edges);

		assert (edgeSet.size() == edges.size());
		// 确保没有一个边指向不存在的点，并且所有边的长度都是大于0的
		for (Edge<L> edge : edgeSet) {
			assert (edge.getWeight() > 0);
			assert (this.vertices.contains(edge.getTarget()));
			assert (this.vertices.contains(edge.getSource()));

		}
	}

	@Override
	public boolean add(L vertex) {
		// 检查该顶点是否是已经存在的
		boolean flag = !(this.vertices.contains(vertex));
		if (flag) {
			this.vertices.add(vertex);
		}

		checkRep();
		return flag;
	}

	@Override
	public int set(L source, L target, int weight) {
		// 确定对应的两个顶点是存在的
		boolean flag1 = this.vertices.contains(source);
		boolean flag2 = this.vertices.contains(target);

		int oldWeight = 0;
		//如果两个点有一个不存在，先加入点再新建边,注意两个点不能一样
		if (flag1 && flag2) {
			// 如果是已经存在的边，则更新这条边，否则新建，注意传入的weight应该大于0
			for (Edge<L> edge : this.edges) {
				if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
					oldWeight = edge.getWeight();

					if (weight > 0) {
						this.edges.remove(edge);
						Edge<L> updateEdge = new Edge<L>(source, target, weight);
						this.edges.add(updateEdge);
					}

					break;
				}
			}

			if ((oldWeight == 0) && (weight > 0)) {
				Edge<L> newEdge = new Edge<L>(source, target, weight);
				this.edges.add(newEdge);
			}

		}else if (!source.equals(target)&&weight>0){
			if(flag1)this.vertices.add(source);
			if(flag1)this.vertices.add(target);

			Edge<L> newEdge = new Edge<L>(source, target, weight);
			this.edges.add(newEdge);
		}
		
		checkRep();
		return oldWeight;
	}

	@Override
	public boolean remove(L vertex) {
		boolean flag = this.vertices.contains(vertex);

		// 先检查该点是否存在，存在再移除
		if (flag) {
			this.vertices.remove(vertex);
			//同时移除与该顶点有关的所有边
			for (Edge<L> edge : this.edges) {
				if(edge.getSource().equals(vertex)||edge.getTarget().equals(vertex))this.edges.remove(edge);;

			}
		}

		
		return flag;
	}

	@Override
	public Set<L> vertices() {
		//返回一个新建的set
		Set<L> v = new HashSet<>(vertices);
		return v;
	}

	@Override
	public Map<L, Integer> sources(L target) {

		Map<L, Integer> SourcesWeight = new HashMap<>();

		for (Edge<L> edge : this.edges) {
			//遍历寻找
			if(edge.getTarget().equals(target)) {
				L source = edge.getSource();
				int weight = edge.getWeight();
				SourcesWeight.put(source, weight);
			}
		}
		
		return SourcesWeight;
	}

	@Override
	public Map<L, Integer> targets(L source) {

		Map<L, Integer> TargetsWeight = new HashMap<>();
		for (Edge<L> edge : this.edges) {
			//遍历寻找
			if(edge.getSource().equals(source)) {
				L target = edge.getSource();
				int weight = edge.getWeight();
				TargetsWeight.put(target, weight);
			}
		}
		return TargetsWeight;
	}

	//  toString()
	@Override
	public String toString() {
		String result = "";


		String vertexResult = "图中有这些顶点：";
		String edgeResult = "有这些边： ";

		if (this.edges.isEmpty()) {
			if (this.vertices.isEmpty()) {
				vertexResult += ", ";
			}
			else {
				for (L vertex : this.vertices) {
					vertexResult += vertex.toString();
					vertexResult += ", ";
				}
			}
		}else {
			for (Edge<L> edge : this.edges) {
				L target = edge.getTarget();
				L source = edge.getSource();

				if ((target == "") || (source == "")) {
					vertexResult += ", ";
				}


				if (!vertexResult.contains(source.toString())) {
					vertexResult += source;
					vertexResult += ", ";
				}

				if (!vertexResult.contains(target.toString())) {
					vertexResult += target;
					vertexResult += ", ";
				}

				edgeResult += edge.toString();
				edgeResult += ", ";
			}
		}
		

		
		vertexResult = vertexResult.substring(0, vertexResult.length() - 2);
		vertexResult += "; ";


		result = vertexResult + edgeResult;

		if (!this.edges.isEmpty()) {
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}

}

/**
 * Specification:
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * Each edge has a source vertex and a target vertex.
 * Both vertices must have the same immutable type.
 * Edges are directed (point from source to target).
 * Each edge has a positive weight of type int.
 * The vertices of an edge don't necessarily have to exist in the graph
 *  (we could just be checking to see if the graph contains this edge).
 *  
 *  @param source : the vertex from which the edge originates
 *  @param target : the vertex to which the edge points
 *  @param weight : the positive int value representing the cost/weight of the edge
 * 
 */
class Edge<L> {

	//  fields
	private final int weight;
	private final L source;
	private final L target;

	// Abstraction function:
	// source and target and a int number weight

	// Representation invariant:
	// source 和 target都不能为空，权值应当大于0
	// Safety from rep exposure:
	// 所有的变量都是私有、final的，weight是固定类型int

	//  constructor
	public Edge(L source, L target, int weight) {
		this.weight = weight;
		this.source = source;
		this.target = target;
		
		checkRep();
	}

	//  checkRep
	private void checkRep() {
		assert this.source != null;
		assert this.target != null;
		assert this.weight > 0;

	}

	//  methods
	public int getWeight() {
		return this.weight;
	}

	public L getTarget() {
		return this.target;
	}

	public L getSource() {
		return this.source;
	}

	//  toString()
	@Override
    public String toString() {
        return "起点" + this.source + "到目标" + this.target
                + "的距离为:" + this.weight;
    }
}
