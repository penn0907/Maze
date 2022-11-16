import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import org.junit.jupiter.api.*;
import java.util.*;

class MazeTester {

	private String[] mazes;
	private static final int[] sizes = { 4, 6, 8, 10, 20 };
	private static final int[] expectedLength = {13, 15, 45, 21, 61};

	@BeforeEach
	void setUp() {
		try {
			mazes = new String[sizes.length];
			for (int i = 0; i < sizes.length; i++) {
				String maze = "";
				File file = new File("maze" + sizes[i] + ".txt");
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				line = br.readLine();
				while (line != null) {
					maze += line + "\n";
					line = br.readLine();
				}
				mazes[i] = maze;
				br.close();
				fr.close();
			}
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}

	}
	
	@Test
	void test() {
		
		//Given input tests
		Graph g;
		LinkedList<Integer> bfsPath, dfsPath;
		
		for(int i = 0; i < sizes.length; i ++) {
			System.out.println("Testing Maze " + sizes[i] + ": \n");
			g = new Graph(sizes[i], mazes[i]);
			g.BFSPath();
			bfsPath = g.getPath();
			assertEquals(g.getShortestPathLength(), expectedLength[i]);
			g.DFSPath();
			dfsPath = g.getPath();
			assertEquals(g.getShortestPathLength(), expectedLength[i]);
			assertTrue(bfsPath.toString().equals(dfsPath.toString()));
		}
		
		//Edge Cases
		g = new Graph(1); //Maze size of 1
		g.BFSPath();
		assertEquals(g.getShortestPathLength(), 1);
		g.DFSPath();
		assertEquals(g.getShortestPathLength(), 1);
		
		g = new Graph(0); //Maze size of 0
		g.BFSPath();
		assertEquals(g.getShortestPathLength(), 0);
		g.DFSPath();
		assertEquals(g.getShortestPathLength(), 0);
	}

}
