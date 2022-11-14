/*
 * Name(s): Kevin Boc and Yupeng Ni
 * Date: 11/12/2022
 * The Graph class creates a graph representation of the generated maze, where adjacency linked lists 
 * show the connections each of each vertex. There are two search methods, depth first search and 
 * breadth first search.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Graph {

	private int r;
	private MazeGenerator maze;
	
	private ArrayList<LinkedList<Integer>> cells;

	private int start;
	private int dest;

	private int dicoverTimeCount;
	private int[] dist;
	private int[] discoverTime;
	private int[] parent;
	private boolean[] visit;
	private LinkedList<Integer> path;
	
	private int visitCount; //Kevin's implementation
	private int[][] pathing; //Kevin's implementation
	private int sides; //Kevin's implementation

	public Graph(int r) {
		this.r = r;
		this.maze = new MazeGenerator(r);
		cells = maze.getCells();
		this.cells = cells;
		
		initialization();
		
		maze.displayMaze();
	}
	
	private void initialization() {
		visitCount = 0; //Kevin's implementation
		sides = (int) Math.sqrt(cells.size());
		
		dicoverTimeCount = 0;
		start = 0;
		dest = cells.size() - 1;
		dist = new int[cells.size()];
		discoverTime = new int[cells.size()];
		
		
		pathing = new int[sides][sides]; //Kevin's implementation
		for(int x = 0; x < pathing.length; x++) { //Kevin's implementation
			for(int y = 0; y < pathing[0].length; y++) {
				pathing[x][y] = -1;
			}
		}
		
		parent = new int[cells.size()];
		visit = new boolean[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			dist[i] = -1;
			discoverTime[i] = -1;
			parent[i] = -1;
			visit[i] = false;
		}
		
		path = new LinkedList<Integer>();
		
	}

	public void BFSPath() {

		//restore the variables
		initialization();
		BFS();

		int temp = dest;
		path.add(temp);
		while (parent[temp] != -1) {
			path.add(parent[temp]);
			temp = parent[temp];
		}
		
		printGraphResult();

	}

	public void BFS() {

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
					discoverTime[adj] = ++dicoverTimeCount;
					parent[adj] = q;
					queue.add(adj);
					
					if (adj == dest)
						return;
				}
			}
		}
	}
	
	public void printGraphResult() {
		initialization();
		System.out.println("BFS:");
		maze.displayMazeVisited(discoverTime);
		System.out.println();
		maze.displayShortestPath(path);
		System.out.println("Length of path: " + (dist[dest] + 1));
		displayPath();
		System.out.println("Visited cells: " + visitCount);
	}
	
	private void displayPath() {
		System.out.print("Path :");
		for (int i = path.size() - 1; i >= 0; i--) {
			int num = path.get(i);
			int[] xy = maze.getXYbyNum(num, r);
			System.out.print("(" + xy[0] + ", " + xy[1] + ") ");
		}
		System.out.println();
	}

	public void DFS() {
		
		Stack<Integer> stack = new Stack<>();
		stack.push(start);
		boolean found = false;
		DFSHelper(stack, found);
		System.out.println("DFS Path: \n" + pathString(pathing));
		System.out.println("DFS Path Coordinates: \n" + pathCoords(pathing));
		System.out.println("Visited Vertices: " + visitCount);
 	}
	
	public void DFSHelper(Stack<Integer> stack, boolean found) {
		if(!found) {
			int visited = stack.pop();
			int x = visited / sides;
			int y = visited % sides;
			pathing[x][y] = visitCount;
			visit[visited] = true;
			visitCount++;
			LinkedList<Integer> adj = cells.get(visited);
			if(visited == dest) {
				found = true;
			}
			else {
				for(int j = 0; j < adj.size(); j++) {
					int curr = adj.get(j);
					if(!visit[curr]) {
						stack.push(curr);
					}
				}
				DFSHelper(stack, found);
			}
		}
	}
	
	public static void toString(int[][] arr) { //Kevin's implementations
		for(int x = 0; x < arr.length; x ++) {
			String s = "";
			for(int y = 0; y < arr[0].length; y ++) {
				if(arr[x][y] != -1) {
					s += arr[x][y] + " ";
				}
				else {
					s += "  ";
				}
				System.out.println(s);
			}
		}
	}
	
	public static String pathString(int[][] arr) { //Kevin's implementation
		String s = "";
		for(int[] row: arr) {
			for(int val: row) {
				if(val != -1) {
					s += val + " ";
				}
				else {
					s += "  ";
				}
			}
			s += "\n";
		}
		return s;
	}
	
	public static String pathCoords(int[][] arr) { //Kevin's implementation (Needs to fix to show only shortest path)
		String s = "";
		for(int x = 0; x < arr.length; x ++) {
			for(int y = 0; y < arr[0].length; y ++) {
				if(arr[x][y] != -1) {
					s += "(" + x + ", " + y + ") ";
				}
			}
		}
		return s;
		
	}
	
	public static void main(String[] args) {
		
		Graph g = new Graph(4);
		//g.DFS();
		g.BFSPath();
	}
}
