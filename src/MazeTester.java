import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MazeTester {

	// key - Sizes Of The Sample Inputs;
	// value - Expected Lengths OF The Sample Inputs With
	private final static Map<Integer, Integer> SAMPLE_INPUT_EXPECTED_LENGTH = new HashMap<Integer, Integer>();
	private final static Map<Integer, Integer> TEST_INPUT_EXPECTED_LENGTH = new HashMap<Integer, Integer>();

	@BeforeEach
	void setUp() {
		// set up the expected path length for each maze size
		SAMPLE_INPUT_EXPECTED_LENGTH.put(4, 13);
		SAMPLE_INPUT_EXPECTED_LENGTH.put(6, 15);
		SAMPLE_INPUT_EXPECTED_LENGTH.put(8, 45);
		SAMPLE_INPUT_EXPECTED_LENGTH.put(10, 21);
		SAMPLE_INPUT_EXPECTED_LENGTH.put(20, 61);

		TEST_INPUT_EXPECTED_LENGTH.put(1, 1);
		TEST_INPUT_EXPECTED_LENGTH.put(3, 7);
		TEST_INPUT_EXPECTED_LENGTH.put(5, 19);
		TEST_INPUT_EXPECTED_LENGTH.put(7, 19);
		TEST_INPUT_EXPECTED_LENGTH.put(9, 39);
	}

	/**
	 * create and save maze input in file for test, only run once
	 */
	@Test
	void generateMaze() {
		// generate maze and write the maze to file

		/*for (int i = 1; i < 10; i += 2) {
			int r = i;
			MazeSolver ms = new MazeSolver(r, "test-input", "maze" + r);
			ms.generateMaze();
		}*/

	}

	/**
	 * solve and output the mazes in test-input folder
	 */
	@Test
	void myInputTest() {

		// test generated mazes in "test-input" folder
		inputTest(TEST_INPUT_EXPECTED_LENGTH, "test-input");

	}

	@Test
	void sampleInputTest() {

		// test mazes in "sample-input" folder
		inputTest(SAMPLE_INPUT_EXPECTED_LENGTH, "sample-input");

	}

	/**
	 * test the my maze output
	 */
	@Test
	void myOutputTest() {

		outputTest(TEST_INPUT_EXPECTED_LENGTH, "test-input");
	}

	/**
	 * test the sample maze output
	 */
	@Test
	void sampleOutputTest() {

		outputTest(SAMPLE_INPUT_EXPECTED_LENGTH, "sample-input");
	}

	@Test
	void edgeCaseTest() {
		// test create size 0 maze
		int r = 0;
		// expected throw IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> {
			new MazeSolver(r, "test-input", "maze" + r);
		});

	}

	/**
	 * check if the output is valid, and write output file
	 * 
	 * @param expectedMap expected path length for each size of maze, key-size,
	 *                    value-expected length
	 * @param folder      input file folder name
	 */
	private void inputTest(Map<Integer, Integer> expectedMap, String folder) {
		for (Integer key : expectedMap.keySet()) {
			// maze size
			int r = key;
			// set the maze from txt file
			MazeSolver ms = new MazeSolver(r, folder, "maze" + r);
			ms.solveMaze();

			// compare bfs and dfs path
			assertEquals(ms.getBfsPath().toString(), ms.getDfsPath().toString());
			// compare bfs and dfs maze string
			assertEquals(ms.getBfsShortestMaze(), ms.getDfsShortestMaze());

			// get expected path length from map
			int expectedPathLength = expectedMap.get(r);
			// compare path length to expected length
			assertTrue(ms.getBfsPath().size() == expectedPathLength);
			assertTrue(ms.getDfsPath().size() == expectedPathLength);

			// write maze output
			ms.writeMaze();
		}
	}

	/**
	 * solve mazes from input files, compare the output string from maze's solution
	 * and saved output files
	 * 
	 * @param expectedMap expected path length for each size of maze, key-size,
	 *                    value-expected length
	 * @param folder      input file folder name
	 */
	private void outputTest(Map<Integer, Integer> expectedMap, String folder) {
		for (Integer key : expectedMap.keySet()) {

			int r = key;

			// get maze from input files
			MazeSolver ms = new MazeSolver(r, folder, "maze" + r);
			ms.solveMaze();
			String solvedString = ms.getOutputStr();

			// get output maze from output files
			MazeSolver msOutput = new MazeSolver(r, folder, "maze" + r + "-output");
			String outputString = msOutput.readMaze();

			// compare the output string from mazeSolver and output.txt
			assertEquals(outputString, solvedString);
		}

	}

}
