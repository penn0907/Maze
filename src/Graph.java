import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

	private ArrayList<LinkedList<Integer>> cells;

	private int start;
	private int dest;

	private int[] dist;
	private int[] pathRecord;
	private boolean[] visit;
	private LinkedList<Integer> path;

	public Graph(ArrayList<LinkedList<Integer>> cells) {
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
	
	

}
