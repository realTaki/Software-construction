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
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<Vertex<L>>();

	// Abstraction function:
	//

	// Representation invariant:
	//
	//
	// Safety from rep exposure:
	//

	// constructors
	public ConcreteVerticesGraph(List<Vertex<L>> vertices) {
		this.vertices.addAll(vertices);
		checkRep();
	}

	public ConcreteVerticesGraph() {
		this.vertices.clear();
		checkRep();
	}

	// checkRep
	private void checkRep() {
		Set<Vertex<L>> vertexSet = new HashSet<>(this.vertices);
		assert (vertexSet.size() == this.vertices.size());
		for (L vertex : this.vertices()) {
			Map<L, Integer> sources = this.sources(vertex);
			for (L key : sources.keySet()) {
				assert (this.vertices().contains(key));
				assert (sources.get(key) > 0);
			}
			Map<L, Integer> targets = this.targets(vertex);
			for (L key : targets.keySet()) {
				assert (this.vertices().contains(key));
				assert (targets.get(key) > 0);
			}
		}
	}

	@Override
	public boolean add(L vertex) {

		Vertex<L> vertexInstance = new Vertex<L>(vertex);

		for (L currentVertexLabel : this.vertices()) {

			boolean graphContainedVertex = (currentVertexLabel.equals(vertex));

			if (graphContainedVertex) {
				return false;
			}
		}

		this.vertices.add(vertexInstance);
		this.vertices().add(vertex);

		checkRep();

		return true;
	}

	@Override
	public int set(L source, L target, int weight) {

		boolean graphContainedSource = this.vertices().contains(source);
		boolean graphContainedTarget = this.vertices().contains(target);

		int oldWeight = 0;

		boolean sourceAndTargetEqual = source.equals(target);

		if (graphContainedSource && graphContainedTarget && !sourceAndTargetEqual) {

			for (Vertex<L> vertex : this.vertices) {
				L vertexLabel = vertex.getLabel();

				boolean vertexIsSource = vertexLabel.equals(source);
				boolean vertexHasEdgeWithTarget = vertex.getOutgoingEdges().keySet().contains(target);

				if (vertexIsSource && vertexHasEdgeWithTarget) {

					oldWeight = vertex.getOutgoingEdges().get(target);
					vertex.getOutgoingEdges().remove(target);

					if (weight > 0) {
						vertex.getOutgoingEdges().put(target, weight);
					}
				}

				else if (vertexIsSource && !vertexHasEdgeWithTarget) {
					if (weight > 0) {
						vertex.getOutgoingEdges().put(target, weight);
					}
				}

				boolean vertexIsTarget = vertexLabel.equals(target);
				boolean vertexHasEdgeWithSource = vertex.getIncomingEdges().keySet().contains(source);
				if (vertexIsTarget && vertexHasEdgeWithSource) {

					oldWeight = vertex.getIncomingEdges().get(source);
					vertex.getIncomingEdges().remove(source);

					if (weight > 0) {
						vertex.getIncomingEdges().put(source, weight);
					}
				}

				else if (vertexIsTarget && !vertexHasEdgeWithSource) {
					if (weight > 0) {
						vertex.getIncomingEdges().put(source, weight);
					}
				}
			}
			checkRep();

			return oldWeight;
		}

		else if (graphContainedSource && graphContainedTarget && sourceAndTargetEqual) {

			for (Vertex<L> vertex : this.vertices) {
				L vertexLabel = vertex.getLabel();

				boolean vertexIsSource = vertexLabel.equals(source);
				boolean vertexHasEdgeWithTarget = vertex.getOutgoingEdges().keySet().contains(target);
				boolean vertexIsTarget = vertexLabel.equals(target);
				boolean vertexHasEdgeWithSource = vertex.getIncomingEdges().keySet().contains(source);

				if (vertexIsSource && vertexHasEdgeWithTarget) {

					oldWeight = vertex.getOutgoingEdges().get(target);
					vertex.getOutgoingEdges().remove(target);

					if (weight > 0) {
						vertex.getOutgoingEdges().put(target, weight);
					}
				}

				else if (vertexIsSource && !vertexHasEdgeWithTarget) {
					vertex.getOutgoingEdges().put(target, weight);
				}

				if (vertexIsTarget && vertexHasEdgeWithSource) {

					oldWeight = vertex.getIncomingEdges().get(source);
					vertex.getIncomingEdges().remove(source);

					if (weight > 0) {
						vertex.getIncomingEdges().put(source, weight);
					}
				} else if (vertexIsSource && !vertexHasEdgeWithTarget) {
					vertex.getIncomingEdges().put(source, weight);
				}
			}

			return oldWeight;
		}

		else {

			Map<L, Integer> sourceOutgoingMap = new HashMap<>();
			Map<L, Integer> sourceIncomingMap = new HashMap<>();
			Map<L, Integer> targetOutgoingMap = new HashMap<>();
			Map<L, Integer> targetIncomingMap = new HashMap<>();

			if (weight > 0) {
				sourceOutgoingMap.put(target, weight);
				targetIncomingMap.put(source, weight);
			}

			Vertex<L> sourceVertex = new Vertex<L>(source, sourceOutgoingMap, sourceIncomingMap);
			Vertex<L> targetVertex = new Vertex<L>(target, targetOutgoingMap, targetIncomingMap);

			if (!this.vertices.contains(sourceVertex)) {
				this.vertices.add(sourceVertex);
				this.vertices().add(sourceVertex.getLabel());
			}

			if (!this.vertices.contains(targetVertex)) {
				this.vertices.add(targetVertex);
				this.vertices().add(targetVertex.getLabel());
			}
		}
		checkRep();
		return oldWeight;
	}

	@Override
	public boolean remove(L vertex) {

		boolean graphContainedVertex = this.vertices().contains(vertex);
		Set<Vertex<L>> toBeRemovedFromThisVertices = new HashSet<>();
		Set<Vertex<L>> toBeRemovedFromOutgoingEdges = new HashSet<>();
		Set<Vertex<L>> toBeRemovedFromIncomingEdges = new HashSet<>();

		if (graphContainedVertex) {

			for (Vertex<L> currentVertex : this.vertices) {
				L currentLabel = currentVertex.getLabel();

				if (currentLabel.equals(vertex)) {
					toBeRemovedFromThisVertices.add(currentVertex);
				}

				if (currentVertex.getOutgoingEdges().keySet().contains(vertex)) {
					toBeRemovedFromOutgoingEdges.add(currentVertex);
				}

				if (currentVertex.getIncomingEdges().keySet().contains(vertex)) {
					toBeRemovedFromIncomingEdges.add(currentVertex);
				}
			}

			for (Vertex<L> vertexToBeRemoved : toBeRemovedFromThisVertices) {
				this.vertices.remove(vertexToBeRemoved);
				this.vertices().remove(vertex);
			}

			for (Vertex<L> vertexWithTargetToBeRemoved : toBeRemovedFromOutgoingEdges) {
				vertexWithTargetToBeRemoved.getOutgoingEdges().remove(vertex);
			}

			for (Vertex<L> vertexWithSourceToBeRemoved : toBeRemovedFromIncomingEdges) {
				vertexWithSourceToBeRemoved.getIncomingEdges().remove(vertex);
			}
		}
		checkRep();

		return graphContainedVertex;
	}

	@Override
	public Set<L> vertices() {

		Set<L> setOfVertices = new HashSet<>();

		for (Vertex<L> vertex : this.vertices) {
			L label = vertex.getLabel();
			setOfVertices.add(label);
		}
		return setOfVertices;
	}

	@Override
	public Map<L, Integer> sources(L target) {

		Map<L, Integer> sourceToWeightMap = new HashMap<>();

		for (Vertex<L> currentVertex : this.vertices) {

			boolean vertexIsTarget = currentVertex.getLabel().equals(target);

			if (vertexIsTarget) {
				for (L source : currentVertex.getIncomingEdges().keySet()) {
					int weight = (int) currentVertex.getIncomingEdges().get(source);
					sourceToWeightMap.put(source, weight);
				}
			}
		}
		return sourceToWeightMap;
	}

	@Override
	public Map<L, Integer> targets(L source) {

		Map<L, Integer> targetToWeightMap = new HashMap<>();
		for (Vertex<L> currentVertex : this.vertices) {

			boolean vertexIsSource = currentVertex.getLabel().equals(source);
			if (vertexIsSource) {
				for (L target : currentVertex.getOutgoingEdges().keySet()) {
					int weight = (int) currentVertex.getOutgoingEdges().get(target);
					targetToWeightMap.put(target, weight);
				}
			}
		}
		return targetToWeightMap;
	}

	@Override
	public String toString() {

		String result = "This graph has vertices: ";

		if (!this.vertices().isEmpty()) {

			for (Vertex<L> vertex : this.vertices) {
				L vertexLabel = vertex.getLabel();
				String vertexString = "Vertex label: ";

				vertexString += vertexLabel;
				vertexString += ", ";
				result += vertexString;
			}

			result = result.substring(0, result.length() - 2);
		}
		return result;
	}
}

/**
 * Specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph. A vertex must have: -a label of type String. -a list
 * of outgoing edges (the vertex must be the source) -a list of incoming edges
 * (the vertex must be the target) There can be no duplicate vertices in a
 * graph. the list of outgoing edges is mutable and private the list of incoming
 * edges is mutable and private A vertex can have an edge going to itself
 * 
 */
class Vertex<L> {

	// fields
	private L label;
	private Map<L, Integer> outgoingEdges;
	private Map<L, Integer> incomingEdges;

	// Abstraction function:
	//
	// Representation invariant:
	//

	// Safety from rep exposure:
	//

	// constructor
	public Vertex(L label) {
		this.label = label;
		this.outgoingEdges = new HashMap<L, Integer>();
		this.incomingEdges = new HashMap<L, Integer>();
		checkRep();
	}

	public Vertex(L label, Map<L, Integer> outgoingEdges, Map<L, Integer> incomingEdges) {
		this.label = label;
		this.outgoingEdges = outgoingEdges;
		this.incomingEdges = incomingEdges;
		checkRep();
	}

	// checkRep
	private void checkRep() {
		for (Integer value : this.outgoingEdges.values()) {
			assert (value > 0);
		}
		for (Integer value : this.incomingEdges.values()) {
			assert (value > 0);
		}
	}

	// methods
	/** return String label of vertex **/
	public L getLabel() {
		return this.label;
	}

	/** return outgoing edges map of vertex **/
	public Map<L, Integer> getOutgoingEdges() {
		return this.outgoingEdges;
	}

	/** return incoming edges map of vertex **/
	public Map<L, Integer> getIncomingEdges() {
		return this.incomingEdges;
	}

	// toString()
	@Override
	public String toString() {
		String result = "Vertex label: " + this.getLabel();
		return result;
	}
}