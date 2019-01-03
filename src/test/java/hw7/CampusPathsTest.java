package hw7;

import java.io.*;

import hw7.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CampusPathsTest { // Rename to the name of your "main" class

	/**
	 * @param file1 
	 * @param file2
	 * @return true if file1 and file2 have the same content, false otherwise
	 * @throws IOException
	 */	
	/* compares two text files, line by line */
	private static boolean compare(String file1, String file2) throws IOException {
		BufferedReader is1 = new BufferedReader(new FileReader(file1)); // Decorator design pattern!
		BufferedReader is2 = new BufferedReader(new FileReader(file2));
		String line1, line2;
		boolean result = true;
		while ((line1=is1.readLine()) != null) {
			// System.out.println(line1);
			line2 = is2.readLine();
			if (line2 == null) {
				System.out.println(file1+" longer than "+file2);
				result = false;
				break;
			}
			if (!line1.equals(line2)) {
				System.out.println("Lines: "+line1+" and "+line2+" differ.");
				result = false;
				break;
			}
		}
		if (result == true && is2.readLine() != null) {
			System.out.println(file1+" shorter than "+file2);
			result = false;
		}
		is1.close();
		is2.close();
		return result;		
	}
	
	private void runTest(String filename) throws IOException {
		InputStream in = System.in; 
		PrintStream out = System.out;				
		String inFilename = "data/"+filename+".test"; // Input filename: [filename].test here  
		String expectedFilename = "data/"+filename+".expected"; // Expected result filename: [filename].expected
		String outFilename = "data/"+filename+".out"; // Output filename: [filename].out
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(inFilename));
		System.setIn(is); // redirects standard input to a file, [filename].test 
		PrintStream os = new PrintStream(new FileOutputStream(outFilename));
		System.setOut(os); // redirects standard output to a file, [filename].out 
		CampusPaths.main(null); // Call to YOUR main. May have to rename.
		System.setIn(in); // restores standard input
		System.setOut(out); // restores standard output
		assertTrue(compare(expectedFilename,outFilename)); 
		// TODO: More informative file comparison will be nice.
		
	}
	
	@Test 
	//test "b" command
	public void testListBuildings() throws IOException {
		runTest("test1");
	}
	@Test
	//test createCampusModel method
	public void testCreate2() throws IOException {
		runTest("createTest");
	}
	@Test
	//test findShortestPath method
	public void testShortestPath3() throws IOException {
		runTest("shortestPathTest");
	}
	
	@Test
	public void testMenu() throws IOException {
		runTest("testMenu");
	}
	
	@Test
	//some corner cases
	public void testCorner3() throws IOException {
		runTest("testCorner");
	}
	
	@Test
	//test methods other than findShortestPath and createCampusModel
	public void testToolFunctions() throws Exception {
		CampusModel c = new CampusModel();
		c.CreateCampusModel("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");
		//test IsName
		assertEquals(false, c.IsName("c"));
		assertEquals(true, c.IsName("Rensselaer Union"));
		//test IsId
		assertEquals(true, c.IsId("100"));
		assertEquals(false, c.IsId("300"));
		//test idToName
		assertEquals("", c.idToName("140"));
		assertEquals("Academy Hall", c.idToName("67"));
		//test nameToId
		assertEquals("67", c.nameToId("Academy Hall"));
		assertEquals(null, c.nameToId(""));
	}
}
