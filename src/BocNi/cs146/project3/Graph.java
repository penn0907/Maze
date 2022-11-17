package BocNi.cs146.project3;

/*
 * Name(s): Kevin Boc and Yupeng Ni
 * Date: 11/12/2022
 */
import java.util.*;

//The Graph class creates a graph representation of the generated maze, where adjacency linked lists 
//show the connections each of each vertex. There are two search methods, depth first search and 
//breadth first search.
public class Graph {

	// Instance Variables
	private int r;
	private int start;
	private int dest;
	private int visitCount;
	private Integer discoverTimeCount;

	private MazeGenerator maze;

	private int[] dist;
	private Integer[] discoverTime;
	private int[] parent;
	private boolean[] visit;

	private LinkedList<Integer> path;

	private ArrayList<LinkedList<Integer>> cells;

	/**
	 * Constructs a graph model of the maze that has r^2 cells
	 * 
	 * @param r the side lengths of the maze (r by r maze)
	 */
	public Graph(int r) {
		this.r = r;
		this.maze = new MazeGenerator(r);
		this.cells = maze.getCells();

		initialization();
	}

	/**
	 * Constructs a graph model of the maze given a string of the maze
	 * 
	 * @param r       the side lengths of the maze (r by r maze)
	 * @param mazeStr string representation of the maze
	 */
	public Graph(int r, String mazeStr) {
		this.r = r;
		this.maze = new MazeGenerator(r, mazeStr);
		this.cells = maze.getCells();

		initialization();

	}

	/**
	 * Initializes all the instance variables of its respective graph object
	 * 
	 */
	private void initialization() {
		visitCount = 0;
		discoverTimeCount = 0;
		start = 0;
		dest = cells.size() - 1;

		dist = new int[cells.size()];
		discoverTime = new Integer[cells.size()];

		parent = new int[cells.size()];
		visit = new boolean[cells.size()];

		path = new LinkedList<Integer>();

		for (int i = 0; i < cells.size(); i++) {
			dist[i] = -1;
			discoverTime[i] = -1;
			parent[i] = -1;
			visit[i] = false;
		}
	}

	/**
	 * Function that calls the breadth-first search and printing result methods. It
	 * also calculates the shortest path given the path BFS took.
	 * 
	 */
	public String BFSPath() {
		// Restore the variables
		initialization();
		BFS();

		int temp = dest;
		path.add(temp);

		while (parent[temp] != -1) {
			path.add(parent[temp]);
			temp = parent[temp];
		}

		return getOutputString("BFS");
	}

	/**
	 * Function that searches the graph representation of the maze for its
	 * destination using breadth-first-search. It measures the count of visited
	 * vertices and its order. It also calculates the length of the shortest path.
	 * 
	 */
	private void BFS() {
		LinkedList<Integer> queue = new LinkedList<Integer>();

		visit[start] = true;
		visitCount++;
		dist[start] = 0;
		discoverTime[start] = 0;

		queue.add(start);

		while (!queue.isEmpty()) {
			int q = queue.remove();
			for (int i = 0; i < cells.get(q).size(); i++) {
				int adj = cells.get(q).get(i);
				if (visit[adj] == false) {
					visit[adj] = true;
					visitCount++;
					dist[adj] = dist[q] + 1;
					discoverTime[adj] = ++discoverTimeCount;
					parent[adj] = q;
					queue.add(adj);

					if (adj == dest)
						return;
				}
			}
		}
	}

	/**
	 * Function that calls the depth-first search and printing result methods. The
	 * implementation of DFS is the use of a data structure, Stack. It also
	 * calculates the shortest path and its length given the path DFS took.
	 * 
	 */
	public String DFSPath() {
		initialization();

		Stack<Integer> stack = new Stack<>();

		stack.push(start);

		boolean found = false;

		DFS(stack, found);

		int temp = dest;
		path.add(temp); // path size is the length of the shortest path

		while (parent[temp] != -1) {
			path.add(parent[temp]);
			temp = parent[temp];
		}

		return getOutputString("DFS");
	}

	/**
	 * Function that searches the graph representation of the maze for its
	 * destination using depth-first-search. It measures the count of visited
	 * vertices and its order.
	 * 
	 * @param stack the stack of vertices/cells
	 * @param found the boolean variable of reaching the destination or not
	 * 
	 */
	private void DFS(Stack<Integer> stack, boolean found) {
		if (!found) {
			int visited = stack.pop();

			discoverTime[visited] = discoverTimeCount;
			discoverTimeCount++;
			visit[visited] = true;
			visitCount++;
			dist[visited]++;

			LinkedList<Integer> adj = cells.get(visited);

			if (visited == dest) {
				found = true;
			} else {

				for (int j = 0; j < adj.size(); j++) {
					int curr = adj.get(j);
					if (!visit[curr]) {
						stack.push(curr);
						parent[curr] = visited;
					}
				}
				DFS(stack, found);
			}
		}
	}

	/**
	 * print maze
	 * 
	 * @return string of maze
	 */
	public String getMazeString() {
		StringBuffer sb = new StringBuffer();
		sb.append(maze.displayMaze(0, null));
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * Prints out and displays the shortest path in coordinates.
	 * 
	 */
	public String displayPath() {
		StringBuffer sb = new StringBuffer();
		for (int i = path.size() - 1; i >= 0; i--) {
			int num = path.get(i);
			int[] xy = maze.getXYbyNum(num, r);
			sb.append("(" + xy[0] + ", " + xy[1] + ") ");
		}
		return sb.toString();
	}

	/**
	 * assemble string of the results of DFS and BFS (Path Order, Shortest Path using #, Shortest
	 * Path Length, and Number of Visited Vertices/Cells)
	 * 
	 * @param methodName the method used to search the maze
	 * 
	 */
	public String getOutputString(String methodName) {
		StringBuffer sb = new StringBuffer();
		sb.append(methodName).append(":\n");
		List<Integer> list = Arrays.asList(discoverTime);
		sb.append(maze.displayMaze(1, list));
		sb.append("\n");
		sb.append(maze.displayMaze(2, path));
		sb.append("Path: ");
		sb.append(displayPath());
		sb.append("\n");
		sb.append("Length of path: " + path.size());
		sb.append("\n");
		sb.append("Visited cells: " + visitCount);
		sb.append("\n");
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * get the maze string with shortest path
	 * 
	 * @return maze String
	 */
	public String getShortestMazeStr() {
		return maze.displayMaze(2,path);
	}

	/**
	 * Returns the path as a linked list of integers
	 * 
	 * @return the path as a linked list of integers
	 */
	public LinkedList<Integer> getPath() {
		return path;
	}

	/**
	 * Get the visited cells count
	 * 
	 * @return visitedCount
	 */
	public int getVisitCount() {
		return visitCount;
	}

}
