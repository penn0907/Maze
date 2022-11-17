package BocNi.cs146.project3;

/*
 * Name(s): Kevin Boc and Yupeng Ni
 * Date: 11/12/2022
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

//The Maze Solver class generates the random mazes given input sizes and writes them to files.
//It also calls BFS and DFS to solve the mazes and store the solutions in the files.
public class MazeSolver {

	//Instance Variables
	private Graph graph;

	private int r;
	private String folder;
	private String fileName;

	private String outputStr;

	private LinkedList<Integer> bfsPath;
	private String bfsShortestMaze;
	private int bfsVisitedCells;

	private LinkedList<Integer> dfsPath;
	private String dfsShortestMaze;
	private int dfsVisitedCells;

	/**
	 * Constructor for read existed output file and set the output string
	 * 
	 * @param folder
	 * @param fileName
	 */
	public MazeSolver(String folder, String fileName) {
		this.folder = folder;
		this.fileName = fileName;
		outputStr = readMaze();
	}

	/**
	 * Constructor for solve the maze from the input file
	 * 
	 * @param r
	 * @param folder
	 * @param fileName
	 */
	public MazeSolver(int r, String folder, String fileName) {
		if(r <= 0 ) {
			throw new IllegalArgumentException("Invalid size input");
		}
		this.folder = folder;
		this.fileName = fileName;
		this.r = r;
	}
	
	/**
	 * generate and write a maze into file with size r
	 */
	public void generateMaze() {
		StringBuffer sb = new StringBuffer();
		graph = new Graph(r);
		sb.append(r).append(" ").append(r).append("\n");
		sb.append(graph.getMazeString());
		writeMaze(folder + "/" + fileName + ".txt", sb.toString());
	}

	/**
	 * solve the maze from txt file and write the output
	 */
	public void solveMaze() {
		this.graph = new Graph(r, readMaze());
		StringBuffer sb = new StringBuffer();
		sb.append(graph.getMazeString());

		sb.append(graph.BFSPath());
		bfsPath = graph.getPath();
		bfsShortestMaze = graph.getShortestMazeStr();
		bfsVisitedCells = graph.getVisitCount();

		sb.append(graph.DFSPath());
		dfsPath = graph.getPath();
		dfsShortestMaze = graph.getShortestMazeStr();
		dfsVisitedCells = graph.getVisitCount();

		outputStr = sb.toString();
		
	}
	
	/**
	 * write maze output into file
	 */
	public void writeMaze() {
		String str = r + " " + r + "\n" + outputStr;
		writeMaze(folder + "/" + fileName + "-output.txt", str);
	}

	/**
	 * helper method to write maze output into file
	 * @param path	file path
	 * @param content
	 */
	private void writeMaze(String path, String content) {
		FileOutputStream fileOutputStream = null;
		File file = new File(path);
		try {
			file.createNewFile();
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(content.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * read maze from file
	 * @return maze String
	 */
	public String readMaze() {
		String maze = "";
		try {
			File file = new File(folder + "/" + fileName + ".txt");
			if(!file.exists()) {
				
				return null;
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				maze += line + "\n";
				line = br.readLine();
			}
			br.close();
			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return maze;
	}

	/**
	 * Returns the Linked List representation of the BFS path
	 * 
	 * @return the BFS path
	 */
	public LinkedList<Integer> getBfsPath() {
		return bfsPath;
	}

	/**
	 * Returns the Linked List representation of the DFS path 
	 * 
	 * @return the DFS path
	 */
	public LinkedList<Integer> getDfsPath() {
		return dfsPath;
	}

	/**
	 * Returns the String representation of the BFS path 
	 * 
	 * @return the BFS path
	 */
	public String getBfsShortestMaze() {
		return bfsShortestMaze;
	}

	/**
	 * Returns the String representation of the DFS path 
	 * 
	 * @return the DFS path
	 */
	public String getDfsShortestMaze() {
		return dfsShortestMaze;
	}

	/**
	 * Returns the visited cells of the the BFS algo
	 * 
	 * @return the visited cells of the the BFS algo
	 */
	public int getBfsVisitedCells() {
		return bfsVisitedCells;
	}

	/**
	 * Returns the visited cells of the the DFS algo
	 * 
	 * @return the visited cells of the the DFS algo
	 */
	public int getDfsVisitedCells() {
		return dfsVisitedCells;
	}

	/**
	 * Returns the output string of the maze not solved
	 * 
	 * @return the output string of the maze not solved
	 */
	public String getOutputStr() {
		return outputStr;
	}

}
