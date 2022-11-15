/*
 * Name(s): Kevin Boc and Yupeng Ni
 * Date: 11/12/2022
 * The Graph class creates a graph representation of the generated maze, where adjacency linked lists 
 * show the connections each of each vertex. There are two search methods, depth first search and 
 * breadth first search.
 */

import java.util.*;

public class Graph {

	private int r;
	private int start; 
	private int dest;
	private int visitCount;
	private int discoverTimeCount;
	
	private MazeGenerator maze;
	
	private int[] dist;
	private int[] discoverTime;
	private int[] parent;
	private boolean[] visit;

	private LinkedList<Integer> path;

	private ArrayList<LinkedList<Integer>> cells;
	
	public Graph(int r) {
		this.r = r;
		this.maze = new MazeGenerator(r);
		this.cells = maze.getCells();

		initialization();
		
		maze.displayMaze();
	}
	
	private void initialization() {
		visitCount = 0; 
		discoverTimeCount = 0;
		start = 0;
		dest = cells.size() - 1;
	
		dist = new int[cells.size()];
		discoverTime = new int[cells.size()];
		
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

	public void BFSPath() {
		//Restore the variables
		initialization();
		BFS();

		int temp = dest;
		path.add(temp);
		
		while (parent[temp] != -1) {
			path.add(parent[temp]);
			temp = parent[temp];
		}
		
		printBFSResult();
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
					discoverTime[adj] = ++discoverTimeCount;
					parent[adj] = q;
					queue.add(adj);
					
					if (adj == dest)
						return;
				}
			}
		}
	}
	
	public void DFSPath() {
		initialization();
		
		Stack<Integer> stack = new Stack<>();
		
		stack.push(start);
		
		boolean found = false;
		
		DFS(stack, found);
		
		int temp = dest;
		path.add(temp);
		
		while (parent[temp] != -1) {
			path.add(parent[temp]);
			temp = parent[temp];
		}
		
		System.out.println(Arrays.toString(dist));
		
		printDFSResult();
 	}
	
	public void DFS(Stack<Integer> stack, boolean found) {
		if(!found) {
			int visited = stack.pop();
			
			discoverTime[visited] = discoverTimeCount;
			discoverTimeCount++;
			visit[visited] = true;
			visitCount++;
			dist[visited]++; 
			
			LinkedList<Integer> adj = cells.get(visited);
			
			if(visited == dest) {
				found = true;
			}
			else {

				for(int j = 0; j < adj.size(); j++) {
					int curr = adj.get(j);
					if(!visit[curr]) {
						stack.push(curr);
						parent[curr] = visited;
					}
				}
				DFS(stack, found);
			}
		}
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
	
	public void printBFSResult() {
		System.out.println("BFS:");
		maze.displayMazeVisited(discoverTime);
		System.out.println();
		maze.displayShortestPath(path);
		System.out.println("Length of path: " + (dist[dest] + 1));
		displayPath();
		System.out.println("Visited cells: " + visitCount);
	}
	
	public void printDFSResult() {
		System.out.println("DFS:");
		maze.displayMazeVisited(discoverTime);
		System.out.println();
		maze.displayShortestPath(path);
		System.out.println("Length of path: " + path.size());
		displayPath();
		System.out.println("Visited cells: " + visitCount);
	}
	
	public static void main(String[] args) {
		
		Graph a1 = new Graph(5);
		Graph a2 = a1;
		a1.DFSPath();
		a2.BFSPath();
	}
}
