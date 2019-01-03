package hw5;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MarvelPathsTest
{
	private MarvelPaths P;
	private MarvelPaths path;
	@Test
	public void testGraphCreation()
	{
		P = new MarvelPaths();
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		Set<String> chars = new HashSet<String>();
		boolean success = true;
		try {
    		P.createNewGraph("data/test2.csv");
    	} catch (Exception e) {
    		success = false;
    	}
		assertEquals(success,true);
		assertEquals(P.findPath("A", "B"),"path from A to B:\nA to B via 1\n");
		assertEquals(P.findPath("B", "C"),"path from B to C:\nB to C via 3\n");
	}
	
	@Test
	public void testExistPath()
	{
		P = new MarvelPaths();
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		Set<String> chars = new HashSet<String>();
		boolean success = true;
    	P.createNewGraph("data/test2.csv");
		assertEquals(P.findPath("A", "G"),"path from A to G:\nA to F via 1\nF to G via 4\n");
		assertEquals(P.findPath("B", "E"),"path from B to E:\nB to E via 2\n");
	}
	
	@Test
	public void testMultipleNode()
	{
		P = new MarvelPaths();
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		Set<String> chars = new HashSet<String>();
		boolean success = true;
		P.createNewGraph("data/test2.csv");
		assertEquals(P.findPath("C", "E"),"path from C to E:\nC to B via 3\nB to E via 2\n");
	}
	
	@Test
	public void testMultipleEdge()
	{
		P = new MarvelPaths();
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		Set<String> chars = new HashSet<String>();
		boolean success = true;
		P.createNewGraph("data/test2.csv");	
		assertEquals(P.findPath("A", "H"),"path from A to H:\nA to B via 1\nB to H via 3\n");
	}
	
	@Test
	public void testEdgeNotExist()
	{
		P = new MarvelPaths();
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		Set<String> chars = new HashSet<String>();
		boolean success = true;
		P.createNewGraph("data/test2.csv");	
		assertEquals(P.findPath("A", "I"),"path from A to I:\nno path found\n");
	}
	
	@Test
	public void testNodeNotExist()
	{
		P = new MarvelPaths();
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		Set<String> chars = new HashSet<String>();
		boolean success = true;
		P.createNewGraph("data/test2.csv");	
		assertEquals(P.findPath("X", "A"),"unknown character X\n");
		assertEquals(P.findPath("A", "Y"),"unknown character Y\n");
		assertEquals(P.findPath("X", "Y"),"unknown character X\nunknown character Y\n");
	}
	
	@Test
	public void testNodeToSelf()
	{
		P = new MarvelPaths();
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		Set<String> chars = new HashSet<String>();
		boolean success = true;
		P.createNewGraph("data/test2.csv");	
		assertEquals(P.findPath("A", "A"),"path from A to A:\n");
	}
	
	@Test
	public void TestMarvel(){
		path = new MarvelPaths();
		path.createNewGraph("data/marvel.csv"); 
		assertEquals(path.findPath("HUMAN ROBOT", "GORILLA-MAN"), "path from HUMAN ROBOT to GORILLA-MAN:\nHUMAN ROBOT to GORILLA-MAN via AVF 4\n");
	}
}