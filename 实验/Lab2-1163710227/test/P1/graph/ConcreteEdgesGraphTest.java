/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;


/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   所有边为空或者所有点为空
    //   所有边和点都不为空
    //   有的点是空的，或者有些边是空的
    
    //  tests for ConcreteEdgesGraph.toString()

    // test graph with vertices and edges (not empty)
    @Test
    public void testToStringVerticesAndEdges() {
        Set<String> vertexSet = new HashSet<>(Arrays.asList("vertex 1", "vertex 2", "vertex 3", "vertex 4"));
        Edge<String> edge1 = new Edge<String>("vertex 1", "vertex 2", 2);
        Edge<String> edge2 = new Edge<String>("vertex 2", "vertex 3", 5);
        Edge<String> edge3 = new Edge<String>("vertex 3", "vertex 4", 1);
        Edge<String> edge4 = new Edge<String>("vertex 4", "vertex 2", 3);
        List<Edge<String>> edgeList = new ArrayList<>(Arrays.asList(edge1, edge2, edge3, edge4));
        ConcreteEdgesGraph<String> graphWithVerticesAndEdges = new ConcreteEdgesGraph<String>(vertexSet, edgeList);
        String actualString = graphWithVerticesAndEdges.toString();
        
        String edge1String = "起点vertex 1到目标vertex 2的距离为:2";
        String edge2String = "起点vertex 2到目标vertex 3的距离为:5";
        String edge3String = "起点vertex 3到目标vertex 4的距离为:1";
        String edge4String = "起点vertex 4到目标vertex 2的距离为:3";
        
        String correctString = "图中有这些顶点：vertex 1, vertex 2, vertex 3, vertex 4; 有这些边： "
                + edge1String + ", " + edge2String + ", " + edge3String + ", " + edge4String;
        
        assertEquals(correctString, actualString);
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    
    //  tests for operations of Edge
    private Edge<String> sourceEqualsTarget;
    private Edge<String> sourceIsEmptyString;
    private Edge<String> targetIsEmptyString;
    private Edge<String> targetAndSourceAreEmptyStrings;
    private Edge<String> sourceAndTargetAreDifferent;
    private Edge<String> weightIsMinValue;
    private Edge<String> weightIsReasonable;
    private Edge<String> weightIsHuge;
    
    
    // source vertex and target vertex are the same
    @Test
    public void testGetSourceSameSourceAndTarget() {
        sourceEqualsTarget = new Edge<String>("same string", "same string", 4);
        assertEquals("same string", sourceEqualsTarget.getSource());
    }
    
    // source vertex and target vertex are different
    @Test
    public void testGetSourceDifferentSourceAndTarget() {
        sourceAndTargetAreDifferent = new Edge<String>("source vertex", "target vertex", 1);
        assertEquals("source vertex", sourceAndTargetAreDifferent.getSource());
    }
    
    // source vertex is empty string
    @Test
    public void testGetSourceEmptyString() {
        sourceIsEmptyString = new Edge<String>("", "target vertex", 80);
        assertEquals("", sourceIsEmptyString.getSource());
    }
    
    
    // source vertex and target vertex are the same
    @Test
    public void testGetTargetSameSourceAndTarget() {
        sourceEqualsTarget = new Edge<String>("same string", "same string", 5);
        assertEquals("same string", sourceEqualsTarget.getTarget());
    }
    
    // source vertex and target vertex are different
    @Test
    public void testGetTargetDifferentSourceAndTarget() {
        sourceAndTargetAreDifferent = new Edge<String>("source vertex", "target vertex", 2);
        assertEquals("target vertex", sourceAndTargetAreDifferent.getTarget());
    }
    
    // target vertex is empty string
    @Test
    public void testGetTargetEmptyString() {
        targetIsEmptyString = new Edge<String>("source vertex", "", 77);
        assertEquals("", targetIsEmptyString.getTarget());
    }
    

    
    // weight = 1 (min value)
    @Test
    public void testGetWeightMinValue() {
        weightIsMinValue = new Edge<String>("source", "target", 1);
        assertEquals(1, weightIsMinValue.getWeight());
    }
    
    // weight > 1 and < 2^31 - 1
    @Test
    public void testGetWeightMiddleValue() {
        weightIsReasonable = new Edge<String>("source", "target", 978);
        assertEquals(978, weightIsReasonable.getWeight());
    }
    
    // weight close to 2^31 - 1 (max value)
    @Test
    public void testGetWeightMaxValue() {
        weightIsHuge = new Edge<String>("source", "target", 2000000000);
        assertEquals(2000000000, weightIsHuge.getWeight());
    }
    

    
    // source is empty string
    @Test
    public void testToStringSourceIsEmptyString() {
        sourceIsEmptyString = new Edge<String>("", "target", 2);
        String resultString = sourceIsEmptyString.toString();
        String correctString = "起点" + "到目标" + "target" + "的距离为:" + "2";
        assertEquals(correctString, resultString);
    }
    
    // target is empty string
    @Test
    public void testToStringTargetIsEmptyString() {
        targetIsEmptyString = new Edge<String>("source", "", 2);
        String resultString = targetIsEmptyString.toString();
        String correctString = "起点" + "source" + "到目标" + "的距离为:" + "2";
        assertEquals(correctString, resultString);
    }
    
    // source and target are empty strings
    @Test
    public void testToStringBothAreEmptyStrings() {
        targetAndSourceAreEmptyStrings = new Edge<String>("", "", 2);
        String resultString = targetAndSourceAreEmptyStrings.toString();
        String correctString = "起点" + "到目标" + "的距离为:" + "2";
        assertEquals(correctString, resultString);
    }
    
    // neither source nor target are empty strings
    @Test
    public void testToStringBothNotEmptyStrings() {
        sourceAndTargetAreDifferent = new Edge<String>("source", "target", 2);
        String resultString = sourceAndTargetAreDifferent.toString();
        String correctString = "起点" + "source" + "到目标" + "target" + "的距离为:" + "2";
        assertEquals(correctString, resultString);
    }
}
