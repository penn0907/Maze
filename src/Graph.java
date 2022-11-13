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

	private ArrayList<LinkedList<Integer>> cells;

	private int start;
	private int dest;

	private int[] dist;
	private int[] pathRecord;
	private boolean[] visit;
	private LinkedList<Integer> path;
	
	private int visitCount; //Kevin's implementation
	private int[][] pathing; //Kevin's implementation
	private int sides; //Kevin's implementation

	public Graph(ArrayList<LinkedList<Integer>> cells) {
		visitCount = 0; //Kevin's implementation
		sides = (int) Math.sqrt(cells.size());
		
		start = 0;
		dest = cells.size() - 1;
		dist = new int[cells.size()];
		
		
		pathing = new int[sides][sides]; //Kevin's implementation
		for(int x = 0; x < pathing.length; x++) { //Kevin's implementation
			for(int y = 0; y < pathing[0].length; y++) {
				pathing[x][y] = -1;
			}
		}
		
		pathRecord = new int[cells.size()];
		visit = new boolean[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			dist[i] = -1;
			pathRecord[i] = -1;
			visit[i] = false;
		}
		
		path = new LinkedList<Integer>();
		this.cells = cells;
	}

	public void BFSPath() {

		BFS();

		int temp = dest;
		path.add(temp);
		while (pathRecord[temp] != -1) {
			path.add(pathRecord[temp]);
			temp = pathRecord[temp];
		}

	}

	public void BFS() {

		LinkedList<Integer> queue = new LinkedList<Integer>();

		visit[start] = true;
		dist[start] = 0;
		queue.add(start);

		while (!queue.isEmpty()) {
			int q = queue.remove();
			for (int i = 0; i < cells.get(q).size(); i++) {
				int adj = cells.get(q).get(i);
				if (visit[adj] == false) {
					visit[adj] = true;
					dist[adj] = dist[q] + 1;
					pathRecord[adj] = q;
					queue.add(adj);

					if (adj == dest)
						break;
				}
			}
		}
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
	
	public int[] getDist() {
		return dist;
	}

	public void setDist(int[] dist) {
		this.dist = dist;
	}

	public int[] getPathRecord() {
		return pathRecord;
	}

	public void setPathRecord(int[] pathRecord) {
		this.pathRecord = pathRecord;
	}

	public boolean[] getVisit() {
		return visit;
	}

	public void setVisit(boolean[] visit) {
		this.visit = visit;
	}

	public LinkedList<Integer> getPath() {
		return path;
	}

	public void setPath(LinkedList<Integer> path) {
		this.path = path;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}
	
	public int getVisitCount() { //Kevin's implementations
		return visitCount;
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
		int r = 3;
		MazeGenerator maze = new MazeGenerator(r);

		maze.displayCells();
		maze.displayMaze();
		maze.setAdj();
		
		Graph g = new Graph(maze.getCells());
		g.DFS();
	}
}
