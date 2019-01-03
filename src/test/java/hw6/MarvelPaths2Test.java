package hw6;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import hw5.MarvelPaths;

public class MarvelPaths2Test {
	private MarvelPaths2 marvelPath2;
	
	@Test
	public void TestGraphCreation() 
	{
		this.marvelPath2 = new MarvelPaths2();
		boolean flag = true;
		try {
			marvelPath2.createNewGraph("data/MarvelTest2.csv");
		}
		catch(Exception e) {
			flag = false;
		}
		assertEquals(flag,true);
		assertEquals(this.marvelPath2.findPath("A", "B"),"path from A to B:\nA to B with weight 0.500\ntotal cost: 0.500\n");
		assertEquals(this.marvelPath2.findPath("B", "C"),"path from B to C:\nB to C with weight 1.000\ntotal cost: 1.000\n");
	}
	 
	@Test
	public void testExistPath()
	{
		this.marvelPath2 = new MarvelPaths2();
		this.marvelPath2.createNewGraph("data/MarvelTest2.csv");
		assertEquals(this.marvelPath2.findPath("A", "G"),"path from A to G:\nA to F with weight 1.000\nF to G with weight 1.000\ntotal cost: 2.000\n");
		assertEquals(this.marvelPath2.findPath("B", "E"),"path from B to E:\nB to E with weight 1.000\ntotal cost: 1.000\n");
	}
	
	@Test
	public void testSameNode()
	{
		this.marvelPath2 = new MarvelPaths2();
		this.marvelPath2.createNewGraph("data/MarvelTest2.csv");
		assertEquals(this.marvelPath2.findPath("A", "A"),"path from A to A:\ntotal cost: 0.000\n");
	}
	
	@Test
	public void testUnknownNode()
	{
		this.marvelPath2 = new MarvelPaths2();
		this.marvelPath2.createNewGraph("data/MarvelTest2.csv");
		assertEquals(this.marvelPath2.findPath("Z", "A"),"unknown character Z\n");
		assertEquals(this.marvelPath2.findPath("A", "Z"),"unknown character Z\n");
		assertEquals(this.marvelPath2.findPath("Z", "Z"),"unknown character Z\n");
	}
	
	@Test
	public void testNoPath()
	{
		this.marvelPath2 = new MarvelPaths2();
		this.marvelPath2.createNewGraph("data/MarvelTest2.csv");
		assertEquals(this.marvelPath2.findPath("G", "I"),"path from G to I:\nno path found\n");
	}
	
	@Test
	public void TestMarvel(){
		this.marvelPath2 = new MarvelPaths2();
		this.marvelPath2.createNewGraph("data/marvel.csv");
		assertEquals(this.marvelPath2.findPath("HUMAN ROBOT", "WOODGOD"), "path from HUMAN ROBOT to WOODGOD:\nHUMAN ROBOT to JONES, RICHARD MILHO with weight 0.500\nJONES, RICHARD MILHO to HULK/DR. ROBERT BRUC with weight 0.006\nHULK/DR. ROBERT BRUC to WOODGOD with weight 0.143\ntotal cost: 0.649\n");
	}
}

