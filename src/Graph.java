import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

	private ArrayList<ArrayList<Integer>> cells;

	private int start;
	private int dest;

	private int[] dist;
	private int[] pathRecord;
	private boolean[] visit;

	public Graph(ArrayList<ArrayList<Integer>> cells) {
		start = 0;
		dest = cells.size() - 1;
		dist = new int[cells.size()];
		pathRecord = new int[cells.size()];
		visit = new boolean[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			dist[i] = -1;
			pathRecord[i] = -1;
			visit[i] = false;
		}
		this.cells = cells;
	}

	public void printBFSPath() {

		BFS();

		LinkedList<Integer> path = new LinkedList<Integer>();

		int temp = dest;
		path.add(temp);
		while (pathRecord[temp] != -1) {
			path.add(pathRecord[temp]);
			temp = pathRecord[temp];
		}

		System.out.print("Path :");
		for (int i = path.size() - 1; i >= 0; i--) {
			System.out.print(path.get(i) + " ");
		}
		System.out.println();
		System.out.println("Length of path: " + dist[dest]);
		int visitCount = 0;
		for (int i = 0; i < visit.length; i++) {
			if (visit[i]) {
				visitCount++;
			}
		}
		System.out.println("Visited cells: " + visitCount);
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

}
