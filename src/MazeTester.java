import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import org.junit.jupiter.api.*;
import java.util.*;

class MazeTester {

	private String[] mazes; //Stores Mazes Of The Sample Inputs
	private String[] bfsSolutions; //Stores Solved Mazes Of The Sample Inputs Using BFS
	private String[] dfsSolutions; //Stores Solved Mazes Of The Sample Inputs Using DFS
	private String[] bfsOrder; //Stores The Order Of The Sample Inputs Using BFS
	private String[] dfsOrder; //Stores The Order Of The Sample Inputs Using DFS
	
	private static final int[] sizes = {4, 6, 8, 10, 20}; //Sizes Of The Sample Inputs
	private static final int[] expectedLength = {13, 15, 45, 21, 61}; //Expected Lengths Of The Sample Inputs With Respect Of Their Sizes
	private static final int[] bfsVisitedCells = {0}; //BFS Visited Cells Of The Sample Inputs With Respect Of Their Sizes
	private static final int[] dfsVisitedCells = {0}; //BFS Visited Cells Of The Sample Inputs With Respect Of Their Sizes
	
	@BeforeEach
	void setUp() {
		try {
			mazes = new String[sizes.length];
			bfsSolutions = new String[sizes.length];
			dfsSolutions = new String[sizes.length];
			bfsOrder = new String[sizes.length];
			dfsOrder = new String[sizes.length];
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
		
		for(int i = 0; i < sizes.length; i ++) {
			//Size Test Indicator
			System.out.println("Testing Maze " + sizes[i] + ": \n");
			g = new Graph(sizes[i], mazes[i]);
			
			//BFS Test
			g.BFSPath();
			bfsOrder[i] = g.getMaze().getOrderMaze();
			bfsSolutions[i] = g.getMaze().getSolvedMaze();
			assertEquals(g.getShortestPathLength(), expectedLength[i]); //Length test
			
			//DFS Test
			g.DFSPath();
			dfsOrder[i] = g.getMaze().getOrderMaze();
			dfsSolutions[i] = g.getMaze().getSolvedMaze();
			assertEquals(g.getShortestPathLength(), expectedLength[i]); //Length test
			
			//DFS and BFS Solutions Test
			assertEquals(dfsSolutions[i], bfsSolutions[i]); //BFS and DFS shortest path test
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
