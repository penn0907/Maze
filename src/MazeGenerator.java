import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class MazeGenerator {
	// coordinates row x, column y
	private final int r;
	// stores the cells
	private final int[][] maze;
	private ArrayList<ArrayList<Integer>> cells;

	public void setAdj() {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < r; j++) {
				int num = getNumByXY(j, i, r);
				if ((maze[i][j] & 1) != 0) {
					cells.get(num).add(getNumByXY(j, i - 1, r));
				}
				if ((maze[i][j] & 2) != 0) {
					cells.get(num).add(getNumByXY(j, i + 1, r));
				}
				if ((maze[i][j] & 4) != 0) {
					cells.get(num).add(getNumByXY(j + 1, i, r));
				}
				if ((maze[i][j] & 8) != 0) {
					cells.get(num).add(getNumByXY(j - 1, i, r));
				}
			}
		}
	}

	// Constructor
	public MazeGenerator(int r) {
		this.r = r;
		maze = new int[this.r][this.r];
		cells = new ArrayList<ArrayList<Integer>>(r * r);
		for (int i = 0; i < r * r; i++) {
			cells.add(new ArrayList<Integer>());
		}
		generateMaze(0, 0);

	}

	// prints the maze with the cells and walls removed
	public void displayMaze() {
		for (int i = 0; i < r; i++) {
			// draw the north edge
			for (int j = 0; j < r; j++) {
				System.out.print((maze[i][j] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < r; j++) {
				System.out.print((maze[i][j] & 8) == 0 ? "|   " : "    ");
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < r; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}

	// recursive perfect maze generator, using a modified DFS
	// (cx,cy) coordinates of current cell, and (nx,ny) coordinates of neighbor cell
	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			// find neighbor cell
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			// if neighbor exists and not visited
			if (between(nx, r) && between(ny, r) && (maze[nx][ny] == 0)) {
				// remove walls
				// update current cell using or (|) bit operations
				// example if a cell has north (1) and south (2) neighbor openings, maze holds 3
				// example if a cell has east (4) and west (8) neighbor openings, maze holds 12

				maze[cx][cy] |= dir.bit;
				// update neighbor cell
				maze[nx][ny] |= dir.opposite.bit;
				// recursive call to neighbor cell
				generateMaze(nx, ny);
			}
		}
	}

	// prints the value of maze array
	public void displayCells() {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < r; j++)
				System.out.print(" " + maze[i][j]);
			System.out.println();
		}
	}

	// checks if 0<=v<upper
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}

	// enum type for all directions
	private enum DIR {
		// direction(bit, column move, row move)
		// bit 1 is North, 2 is South, 4 is East and 8 is West
		// example North N(1,0,-1).
		N(1, -1, 0), S(2, 1, 0), E(4, 0, 1), W(8, 0, -1);

		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;

		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}

		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}

	};

	public static int getNumByXY(int x, int y, int n) {

		return y * n + x;
	}

	public static int[] getXYbyNum(int num, int n) {

		int[] result = { num % n, num / n };

		return result;
	}

	public ArrayList<ArrayList<Integer>> getCells() {
		return cells;
	}

	public void setCells(ArrayList<ArrayList<Integer>> cells) {
		this.cells = cells;
	}

	public static void main(String[] args) {

		MazeGenerator maze = new MazeGenerator(3);

		maze.displayCells();
		maze.displayMaze();
		maze.setAdj();
		ArrayList<ArrayList<Integer>> c = maze.getCells();
		for (int i = 0; i < c.size(); i++) {
			System.out.println(i + ": " + c.get(i).toString());
		}
		
		Graph g = new Graph(maze.getCells());
		g.printBFSPath();
	}

}