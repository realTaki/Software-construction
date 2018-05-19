/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
	 
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    /*
     * Partition Into:
     *      -all elements in vertices are empty strings
     *      -vertices has some empty strings
     *      -vertices is an empty list
     *      -vertices has one element
     *      -vertices has 2+ elements
     *      -vertices is non-empty and has non-empty strings only
     */
    
    // tests for ConcreteVerticesGraph.toString()
    
    // vertices is an empty list
    @Test
    public void testToStringVerticesEmptyList() {
        List<Vertex<String>> emptyVertexList = new ArrayList<>();
        ConcreteVerticesGraph<String> graphWithEmptyVerticesList = new ConcreteVerticesGraph<String>(emptyVertexList);
        String actualString = graphWithEmptyVerticesList.toString();
        String correctString = "This graph has vertices: ";
        assertEquals(correctString, actualString);
    }
    
    // all elements in vertices are empty strings
    @Test
    public void testToStringVerticesHasAllEmptyStrings() {
        Vertex<String> emptyVertex = new Vertex<String>("");
        List<Vertex<String>> emptyStringVertexList = new ArrayList<>(Arrays.asList(emptyVertex));
        ConcreteVerticesGraph<String> graphWithEmptyStringVertices = new ConcreteVerticesGraph<String>(emptyStringVertexList);
        String actualString = graphWithEmptyStringVertices.toString();
        String correctString = "This graph has vertices: Vertex label: ";
        assertEquals(correctString, actualString);
    }
    
    // all elements in vertices are non-empty strings
    @Test
    public void testToStringVerticesHasAllNonEmptyStrings() {
        Vertex<String> vertex1 = new Vertex<String>("label 1");
        Vertex<String> vertex2 = new Vertex<String>("label 2");
        Vertex<String> vertex3 = new Vertex<String>("label 3");
        List<Vertex<String>> stringVertexList = new ArrayList<>(Arrays.asList(vertex1, vertex2, vertex3));
        ConcreteVerticesGraph<String> graphWithStringVertices = new ConcreteVerticesGraph<String>(stringVertexList);
        String actualString = graphWithStringVertices.toString();
        String correctString = "This graph has vertices: " + "Vertex label: label 1, " + 
                "Vertex label: label 2, " + "Vertex label: label 3";
        assertEquals(correctString, actualString);
    }
    
    // vertices has one element
    @Test
    public void testToStringVerticesHasOneElement() {
        Vertex<String> singleVertex = new Vertex<String>("only label");
        List<Vertex<String>> singleVertexList = new ArrayList<>(Arrays.asList(singleVertex));
        ConcreteVerticesGraph<String> graphWithOneVertex = new ConcreteVerticesGraph<String>(singleVertexList);
        String actualString = graphWithOneVertex.toString();
        String correctString = "This graph has vertices: " + "Vertex label: only label";
        assertEquals(correctString, actualString);
    }
    
    // some elements in vertices are empty strings
    @Test
    public void testToStringVerticesHasSomeEmptyStrings() {
        Vertex<String> vertex1 = new Vertex<String>("label 1");
        Vertex<String> vertex2 = new Vertex<String>("label 2");
        Vertex<String> vertex3 = new Vertex<String>("label 3");
        Vertex<String> emptyVertex = new Vertex<String>("");
        List<Vertex<String>> vertexList = new ArrayList<>(Arrays.asList(vertex1, vertex2, vertex3, emptyVertex));
        ConcreteVerticesGraph<String> graphWithStringVertices = new ConcreteVerticesGraph<String>(vertexList);
        String actualString = graphWithStringVertices.toString();
        String correctString = "This graph has vertices: " + "Vertex label: label 1, " + 
                "Vertex label: label 2, " + "Vertex label: label 3, " + "Vertex label: ";
        assertEquals(correctString, actualString);
    }
    
    /*
     * Testing Vertex...
     */
    
    private Vertex<String> labelIsEmptyString;
    private Vertex<String> labelIsRegularString;
    
    private Vertex<String> outgoingEdgesMapIsEmpty;
    private Vertex<String> outgoingEdgesMapHasOneKey;
    private Vertex<String> outgoingEdgesMapHasLabel;
    private Vertex<String> outgoingEdgesMapNoLabel;
    
    private Vertex<String> incomingEdgesMapIsEmpty;
    private Vertex<String> incomingEdgesMapHasOneKey;
    private Vertex<String> incomingEdgesMapHasLabel;
    private Vertex<String> incomingEdgesMapNoLabel;
    
    // Testing strategy for Vertex
    
    /* Methods to be tested:
     *  -vertex.getLabel()
     *  -vertex.getOutgoingEdges()
     *  -vertex.getIncomingEdges()
     *  -vertex.toString()
     *  
     *************************************************************************************
     *
     *  Partitions for getLabel():
     *      -label is empty string
     *      -label is non-empty string
     *      
     *  Partitions for getOutgoingEdges():
     *      -map is empty
     *      -map has one key
     *      
     *  Partitions for getIncomingEdges():
     *      -map is empty
     *      -map has one key
     *      -map has 2+ keys
     *      -map contains vertex label
     *      -map doesn't contain vertex label
     *      
     *  Partitions for get
     *      -map is empty
     *      -map has one key
     *      -map has 2+ keys
     *      -map contains vertex label
     *      -map doesn't contain vertex label
     *      
     *  Partitions for toString():
     *      -label is empty string
     *      -no empty strings
     * 
     */

    
    // label is empty string
    @Test
    public void testGetLabelEmptyString() {
        labelIsEmptyString = new Vertex<String>("");
        String label = labelIsEmptyString.getLabel();
        assertEquals("", label);
    }
    
    // label is non-empty string
    @Test
    public void testGetLabelGeneral() {
        labelIsRegularString = new Vertex<String>("this is a vertex label");
        String label = labelIsRegularString.getLabel();
        assertEquals("this is a vertex label", label);
    }

    
    // test empty map
    @Test
    public void testGetOutgoingEdgesEmpty() {
        outgoingEdgesMapIsEmpty = new Vertex<String>("label");
        Map<String, Integer> emptyMap = outgoingEdgesMapIsEmpty.getOutgoingEdges();
        assertTrue(emptyMap.isEmpty());
    }
    
    // test map with only one key
    @Test
    public void testGetOutgoingEdgesOneKey() {
        Map<String, Integer> singleKeyMap = new HashMap<>();
        singleKeyMap.put("target1", 1);
        Map<String, Integer> emptyMap = new HashMap<>();
        outgoingEdgesMapHasOneKey = new Vertex<String>("label", singleKeyMap, emptyMap);
        
        Map<String, Integer> outgoingEdgesMap = outgoingEdgesMapHasOneKey.getOutgoingEdges();
        assertEquals(1, outgoingEdgesMap.keySet().size());
        assertTrue(outgoingEdgesMap.keySet().contains("target1"));
        assertEquals(1, (int) outgoingEdgesMap.get("target1"));
    }
    
    // test map with 2+ keys and not containing vertex label
    @Test
    public void testGetOutgoingEdgesNoLabel() {
        Map<String, Integer> manyKeyMap = new HashMap<>();
        manyKeyMap.put("target1", 3);
        manyKeyMap.put("target2", 5);
        manyKeyMap.put("target3", 9);
        Map<String, Integer> emptyMap = new HashMap<>();
        outgoingEdgesMapNoLabel = new Vertex<String>("label", manyKeyMap, emptyMap);
        
        Map<String, Integer> outgoingEdgesMap = outgoingEdgesMapNoLabel.getOutgoingEdges();
        assertEquals(3, outgoingEdgesMap.keySet().size());
        assertTrue(outgoingEdgesMap.keySet().contains("target1"));
        assertTrue(outgoingEdgesMap.keySet().contains("target2"));
        assertTrue(outgoingEdgesMap.keySet().contains("target3"));
        assertEquals(3, (int) outgoingEdgesMap.get("target1"));
        assertEquals(5, (int) outgoingEdgesMap.get("target2"));
        assertEquals(9, (int) outgoingEdgesMap.get("target3"));
    }
    
    // test map containing vertex label
    @Test
    public void testGetOutgoingEdgesLabel() {
        Map<String, Integer> manyKeyMap = new HashMap<>();
        String vertexLabel = "label";
        manyKeyMap.put("target1", 3);
        manyKeyMap.put("target2", 5);
        manyKeyMap.put("target3", 9);
        manyKeyMap.put(vertexLabel, 1);
        Map<String, Integer> emptyMap = new HashMap<>();
        outgoingEdgesMapHasLabel = new Vertex<String>(vertexLabel, manyKeyMap, emptyMap);
        
        Map<String, Integer> outgoingEdgesMap = outgoingEdgesMapHasLabel.getOutgoingEdges();
        assertEquals(4, outgoingEdgesMap.keySet().size());
        assertTrue(outgoingEdgesMap.keySet().contains("target1"));
        assertTrue(outgoingEdgesMap.keySet().contains("target2"));
        assertTrue(outgoingEdgesMap.keySet().contains("target3"));
        assertTrue(outgoingEdgesMap.keySet().contains(vertexLabel));
        assertEquals(3, (int) outgoingEdgesMap.get("target1"));
        assertEquals(5, (int) outgoingEdgesMap.get("target2"));
        assertEquals(9, (int) outgoingEdgesMap.get("target3"));
        assertEquals(1, (int) outgoingEdgesMap.get(vertexLabel));
    }
    

    
 // test empty map
    @Test
    public void testGetIncomingEdgesEmpty() {
        incomingEdgesMapIsEmpty = new Vertex<String>("label");
        Map<String, Integer> emptyMap = incomingEdgesMapIsEmpty.getIncomingEdges();
        assertTrue(emptyMap.isEmpty());
    }
    
    // test map with only one key
    @Test
    public void testGetIncomingEdgesOneKey() {
        Map<String, Integer> singleKeyMap = new HashMap<>();
        singleKeyMap.put("source1", 1);
        Map<String, Integer> emptyMap = new HashMap<>();
        incomingEdgesMapHasOneKey = new Vertex<String>("label", emptyMap, singleKeyMap);
        
        Map<String, Integer> incomingEdgesMap = incomingEdgesMapHasOneKey.getIncomingEdges();
        assertEquals(1, incomingEdgesMap.keySet().size());
        assertTrue(incomingEdgesMap.keySet().contains("source1"));
    }
    
    // test map with 2+ keys and not containing vertex label
    @Test
    public void testGetIncomingEdgesNoLabel() {
        Map<String, Integer> manyKeyMap = new HashMap<>();
        manyKeyMap.put("source1", 3);
        manyKeyMap.put("source2", 5);
        manyKeyMap.put("source3", 9);
        Map<String, Integer> emptyMap = new HashMap<>();
        incomingEdgesMapNoLabel = new Vertex<String>("label", emptyMap, manyKeyMap);
        
        Map<String, Integer> incomingEdgesMap = incomingEdgesMapNoLabel.getIncomingEdges();
        assertEquals(3, incomingEdgesMap.keySet().size());
        assertTrue(incomingEdgesMap.keySet().contains("source1"));
        assertTrue(incomingEdgesMap.keySet().contains("source2"));
        assertTrue(incomingEdgesMap.keySet().contains("source3"));
        assertEquals(3, (int) incomingEdgesMap.get("source1"));
        assertEquals(5, (int) incomingEdgesMap.get("source2"));
        assertEquals(9, (int) incomingEdgesMap.get("source3"));
    }
    
    // test map containing vertex label
    @Test
    public void testGetIncomingEdgesLabel() {
        Map<String, Integer> manyKeyMap = new HashMap<>();
        String vertexLabel = "label";
        manyKeyMap.put("source1", 3);
        manyKeyMap.put("source2", 5);
        manyKeyMap.put("source3", 9);
        manyKeyMap.put(vertexLabel, 1);
        Map<String, Integer> emptyMap = new HashMap<>();
        incomingEdgesMapHasLabel = new Vertex<String>(vertexLabel, emptyMap, manyKeyMap);
        
        Map<String, Integer> incomingEdgesMap = incomingEdgesMapHasLabel.getIncomingEdges();
        assertEquals(4, incomingEdgesMap.keySet().size());
        assertTrue(incomingEdgesMap.keySet().contains("source1"));
        assertTrue(incomingEdgesMap.keySet().contains("source2"));
        assertTrue(incomingEdgesMap.keySet().contains("source3"));
        assertTrue(incomingEdgesMap.keySet().contains(vertexLabel));
        assertEquals(3, (int) incomingEdgesMap.get("source1"));
        assertEquals(5, (int) incomingEdgesMap.get("source2"));
        assertEquals(9, (int) incomingEdgesMap.get("source3"));
        assertEquals(1, (int) incomingEdgesMap.get(vertexLabel));
    }

    // label is empty string
    @Test
    public void testToStringLabelIsEmptyString() {
        labelIsEmptyString = new Vertex<String>("");
        String resultString = labelIsEmptyString.toString();
        String correctString = "Vertex label: ";
        assertEquals(correctString, resultString);
    }
    
    // no empty strings
    @Test
    public void testToStringNoEmptyStrings() {
        labelIsRegularString = new Vertex<String>("this is a vertex label");
        String resultString = labelIsRegularString.toString();
        String correctString = "Vertex label: this is a vertex label";
        assertEquals(correctString, resultString);
    }
    
}
