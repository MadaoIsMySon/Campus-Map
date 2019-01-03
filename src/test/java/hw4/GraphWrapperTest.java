package hw4;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import org.junit.Test;

public class GraphWrapperTest {
	@Test
	public void ConstructorTest1() {
		Graph<String, String> testGraph = new Graph<String,String>();
		Iterator<String> iter = testGraph.listNodes();
		assertEquals(false, iter.hasNext());
	}
	
	@Test
	public void ConstructorTest2() {
		GraphWrapper<String, String> testWrapper = new GraphWrapper<String, String>();
		Iterator<String> iter = testWrapper.listNodes();
		assertEquals(false, iter.hasNext());
	}

	@Test
	public void addNodeTest1() {
		Graph<String, String> testGraph = new Graph<String, String>();
		testGraph.addNode("1");
		testGraph.addNode("2");
		testGraph.addNode("A");
		testGraph.addNode("B");

		Iterator<String> iter1 = testGraph.listNodes();

		assertEquals("1", iter1.next());
		assertEquals("2", iter1.next());
		assertEquals("A", iter1.next());
		assertEquals("B", iter1.next());

		testGraph.addNode("1");
		iter1 = testGraph.listNodes();
		testGraph.addNode("1");
		assertEquals("1", iter1.next());
		assertEquals("2", iter1.next());
		assertEquals("A", iter1.next());
		assertEquals("B", iter1.next());
		assertEquals(false, iter1.hasNext());

	}
	
	@Test
	public void addNodeTest2() {
		GraphWrapper<String, String> testWrapper = new GraphWrapper<String, String>();
		testWrapper.addNode("1");
		testWrapper.addNode("2");
		testWrapper.addNode("A");
		testWrapper.addNode("B");

		Iterator<String> iter1 = testWrapper.listNodes();

		assertEquals("1", iter1.next());
		assertEquals("2", iter1.next());
		assertEquals("A", iter1.next());
		assertEquals("B", iter1.next());

		testWrapper.addNode("1");
		iter1 = testWrapper.listNodes();
		testWrapper.addNode("1");
		assertEquals("1", iter1.next());
		assertEquals("2", iter1.next());
		assertEquals("A", iter1.next());
		assertEquals("B", iter1.next());
		assertEquals(false, iter1.hasNext());

	}

	@Test
	public void addEdge_getEdgeTest1() {
		Graph<String, String> testGraph = new Graph<String, String>();
		testGraph.addEdge("1", "2", "10");
		testGraph.addEdge("1", "1", "10");
		testGraph.addEdge("2", "2", "10");
		TreeSet<Edge<String, String>> oneEdges = testGraph.getEdges("1");
		assertEquals(2, oneEdges.size());
		Iterator<Edge<String, String>> iter = oneEdges.iterator();
		assertEquals("1", iter.next().getEndNode());
		assertEquals("2", iter.next().getEndNode());
		oneEdges = testGraph.getEdges("2");
		assertEquals(1, oneEdges.size());
		testGraph.addEdge("2", "2", "10");
		assertEquals(1, oneEdges.size());
		testGraph.addEdge("2", "2", "9");
		oneEdges = testGraph.getEdges("2");
		assertEquals(2, oneEdges.size());
		oneEdges = testGraph.getEdges("3");
		assertEquals(0, oneEdges.size());
	}
	
	@Test
	public void addEdgeTest2() {
		GraphWrapper<String, String> testWrapper = new GraphWrapper<String, String>();
		testWrapper.addEdge("1", "2", "10");
		testWrapper.addEdge("1", "1", "10");
		testWrapper.addEdge("2", "2", "10");
		Iterator<String> iter = testWrapper.listChildren("1");
		assertEquals("1(10)", iter.next());
		assertEquals("2(10)", iter.next());
		assertEquals(false, iter.hasNext());
		iter = testWrapper.listChildren("2");
		assertEquals("2(10)", iter.next());
		assertEquals(false, iter.hasNext());
		testWrapper.addEdge("2", "2", "10");
		iter = testWrapper.listChildren("2");
		assertEquals("2(10)", iter.next());
		assertEquals(false, iter.hasNext());
		testWrapper.addEdge("2", "2", "9");
		iter = testWrapper.listChildren("2");
		assertEquals("2(10)", iter.next());
		assertEquals("2(9)", iter.next());
		assertEquals(false, iter.hasNext());
		iter = testWrapper.listChildren("3");
		assertEquals(false, iter.hasNext());
	}

	@Test
	public void getAllNodesTest() {
		Graph<String, String> testGraph = new Graph<String, String>();
		TreeSet<String> treeSet = testGraph.getAllNodes();
		assertEquals(0, treeSet.size());
		testGraph.addNode("1");
		testGraph.addNode("2");
		testGraph.addNode("A");
		testGraph.addNode("B");
		testGraph.addEdge("1", "2", "10");
		testGraph.addEdge("1", "1", "10");
		testGraph.addEdge("2", "2", "10");
		treeSet = testGraph.getAllNodes();
		assertEquals(4, treeSet.size());
		Iterator<String> iter = treeSet.iterator();
		assertEquals("1", iter.next());
		assertEquals("2", iter.next());
		assertEquals("A", iter.next());
		assertEquals("B", iter.next());

	}

	@Test
	public void listNodesTest1() {
		Graph<String, String> testGraph = new Graph<String, String>();
		testGraph.addNode("A");
		testGraph.addNode("1");
		testGraph.addNode("B");
		testGraph.addNode("2");
		testGraph.addEdge("1", "2", "10");
		testGraph.addEdge("1", "1", "10");
		testGraph.addEdge("2", "3", "10");
		Iterator<String> iter = testGraph.listNodes();
		assertEquals("1", iter.next());
		assertEquals("2", iter.next());
		assertEquals("3", iter.next());
		assertEquals("A", iter.next());
		assertEquals("B", iter.next());
		assertEquals(false, iter.hasNext());
	}
	
	@Test
	public void listNodesTest2() {
		GraphWrapper<String, String> testWrapper = new GraphWrapper<String, String>();
		testWrapper.addNode("A");
		testWrapper.addNode("1");
		testWrapper.addNode("B");
		testWrapper.addNode("2");
		testWrapper.addEdge("1", "2", "10");
		testWrapper.addEdge("1", "1", "10");
		testWrapper.addEdge("2", "3", "10");
		Iterator<String> iter = testWrapper.listNodes();
		assertEquals("1", iter.next());
		assertEquals("2", iter.next());
		assertEquals("3", iter.next());
		assertEquals("A", iter.next());
		assertEquals("B", iter.next());
		assertEquals(false, iter.hasNext());
	}

	@Test
	public void childNodesTest1() {
		Graph<String, String> testGraph = new Graph<String, String>();
		testGraph.addEdge("1", "2", "10");
		testGraph.addEdge("1", "1", "9");
		testGraph.addEdge("1", "3", "10");
		testGraph.addEdge("1", "A", "11");
		testGraph.addEdge("1", "f", "20");
		Iterator<String> iter = testGraph.childNode("1");
		assertEquals("1(9)", iter.next());
		assertEquals("2(10)", iter.next());
		assertEquals("3(10)", iter.next());
		assertEquals("A(11)", iter.next());
		assertEquals("f(20)", iter.next());
		assertEquals(false, iter.hasNext());
		testGraph.addEdge("1", "f", "20");
		iter = testGraph.childNode("1");
		assertEquals("1(9)", iter.next());
		assertEquals("2(10)", iter.next());
		assertEquals("3(10)", iter.next());
		assertEquals("A(11)", iter.next());
		assertEquals("f(20)", iter.next());
		assertEquals(false, iter.hasNext());
	}
	
	@Test
	public void childNodesTest2() {
		GraphWrapper<String, String> testWrapper = new GraphWrapper<String, String>();
		testWrapper.addEdge("1", "2", "10");
		testWrapper.addEdge("1", "1", "9");
		testWrapper.addEdge("1", "3", "10");
		testWrapper.addEdge("1", "A", "11");
		testWrapper.addEdge("1", "f", "20");
		Iterator<String> iter = testWrapper.listChildren("1");
		assertEquals("1(9)", iter.next());
		assertEquals("2(10)", iter.next());
		assertEquals("3(10)", iter.next());
		assertEquals("A(11)", iter.next());
		assertEquals("f(20)", iter.next());
		assertEquals(false, iter.hasNext());
		testWrapper.addEdge("1", "f", "20");
		iter = testWrapper.listChildren("1");
		assertEquals("1(9)", iter.next());
		assertEquals("2(10)", iter.next());
		assertEquals("3(10)", iter.next());
		assertEquals("A(11)", iter.next());
		assertEquals("f(20)", iter.next());
		assertEquals(false, iter.hasNext());
	}
}