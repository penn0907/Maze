package BocNi.cs146.project3;

/*
 * Name(s): Kevin Boc and Yupeng Ni
 * Date: 11/12/2022
 */
import java.util.*;

/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class MazeGenerator {
	// Coordinates row x, column y
	private final int r;

	// Stores the cells
	private final int[][] maze;
	private ArrayList<LinkedList<Integer>> cells;

	// Constructor
	public MazeGenerator(int r) {
		this.r = r;
		maze = new int[this.r][this.r];
		cells = new ArrayList<LinkedList<Integer>>(r * r);
		for (int i = 0; i < r * r; i++) {
			cells.add(new LinkedList<Integer>());
		}
		generateMaze(0, 0);
		setAdj();
	}

	/**
	 * Constructor use a maze string
	 * 
	 * @param maze string maze
	 * @param r    size
	 */
	public MazeGenerator(int r, String maze) {
		this.r = r;
		this.maze = new int[this.r][this.r];
		cells = new ArrayList<LinkedList<Integer>>(r * r);
		for (int i = 0; i < r * r; i++) {
			cells.add(new LinkedList<Integer>());
		}

		// convert string to maze
		convertStringToMaze(maze);
		setAdj();
	}

	/**
	 * convert string maze into this
	 * 
	 * @param maze
	 */
	private void convertStringToMaze(String mazeStr) {
		String[] lines = mazeStr.split("\n");
		int lineCount = 1;
		for (int i = 0; i < r; i++) {
			int charCount = 1;
			for (int j = 0; j < r; j++) {
				if (lines[lineCount - 1].charAt(charCount) == ' ') {
					maze[i][j] += 1;
				}
				if (lines[lineCount + 1].charAt(charCount) == ' ') {
					maze[i][j] += 2;
				}
				if (lines[lineCount].charAt(charCount - 1) == ' ') {
					maze[i][j] += 8;
				}
				if (lines[lineCount].charAt(charCount + 1) == ' ') {
					maze[i][j] += 4;
				}
				charCount += 2;
			}
			lineCount += 2;
		}

	}

	/**
	 * Sets up adjacency list cells
	 */
	public void setAdj() {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < r; j++) {
				int num = getNumByXY(j, i, r);
				if ((maze[i][j] & 1) != 0) {
					int neighbor = getNumByXY(j, i - 1, r);
					if (between(neighbor, r * r))
						cells.get(num).add(neighbor);
				}
				if ((maze[i][j] & 2) != 0) {
					int neighbor = getNumByXY(j, i + 1, r);
					if (between(neighbor, r * r))
						cells.get(num).add(neighbor);
				}
				if ((maze[i][j] & 4) != 0) {
					int neighbor = getNumByXY(j + 1, i, r);
					if (between(neighbor, r * r))
						cells.get(num).add(neighbor);
				}
				if ((maze[i][j] & 8) != 0) {
					int neighbor = getNumByXY(j - 1, i, r);
					if (between(neighbor, r * r))
						cells.get(num).add(neighbor);
				}
			}
		}
	}

	/**
	 * display maze
	 * @param type 	0:default	1:display visited path	2:display shortest path
	 * @param list	visited path list or shortest path list
	 * @return	maze String
	 */
	public String displayMaze(int type, List<Integer> list) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < r; i++) {
			// Draws the north edge
			for (int j = 0; j < r; j++) {
				if (i == 0 && j == 0)
					sb.append("+ ");
				else
					sb.append((maze[i][j] & 1) == 0 ? "+-" : "+ ");
			}
			sb.append("+").append("\n");
			// Draws the west edge
			for (int j = 0; j < r; j++) {
				int num = getNumByXY(j, i, r);
				String temp = getCellFiller(type, list, num);
				sb.append((maze[i][j] & 8) == 0 ? "|" + temp + "" : " " + temp + "");
			}
			sb.append("|").append("\n");
		}
		// Draws the bottom line
		for (int j = 0; j < r - 1; j++) {
			sb.append("+-");
		}
		sb.append("+ +").append("\n");
		return sb.toString();
	}

	/**
	 * get the symbol to fill the cell
	 * @param type 	0:default	1:display visited path	2:display shortest path
	 * @param list	visited path list or shortest path list
	 * @param num	cell number
	 * @return cell symbol
	 */
	private String getCellFiller(int type, List<Integer> list, int num) {
		String temp = " ";
		
		if (type == 1) {
			temp = list.get(num) >= 0 ? list.get(num) % 10 + "" : " ";
			
		} else if (type == 2) {
			temp = list.contains(num) ? "#" + "" : " ";
		}

		return temp;
	}

	// Recursive perfect maze generator, using a modified DFS
	// (cx,cy) coordinates of current cell, and (nx,ny) coordinates of neighbor cell
	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			// Finds neighbor cell
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			// If neighbor exists and not visited
			if (between(nx, r) && between(ny, r) && (maze[nx][ny] == 0)) {
				// Removes walls
				// Updates current cell using or (|) bit operations
				// Example: if a cell has north (1) and south (2) neighbor openings, maze holds
				// 3
				// Example: if a cell has east (4) and west (8) neighbor openings, maze holds 12

				maze[cx][cy] |= dir.bit;
				// Updates neighbor cell
				maze[nx][ny] |= dir.opposite.bit;
				// Recursive call to neighbor cell
				generateMaze(nx, ny);
			}
		}
	}

	// Checks if 0<=v<upper
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}

	// enum type for all directions
	private enum DIR {
		// direction(bit, column move, row move)
		// bit 1 is North, 2 is South, 4 is East and 8 is West
		// Example: North N(1,0,-1).
		N(1, -1, 0), S(2, 1, 0), E(4, 0, 1), W(8, 0, -1);

		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;

		// Uses the static initializer to resolve forward references
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

	/**
	 * Converts x-y coordinate to 1 digit
	 * 
	 * @param x
	 * @param y
	 * @param n
	 * @return
	 */
	public int getNumByXY(int x, int y, int n) {

		return y * n + x;
	}

	/**
	 * Converts 1 digit to x-y coordinate
	 * 
	 * @param num
	 * @param n
	 * @return
	 */
	public int[] getXYbyNum(int num, int n) {

		return new int[] { num % n, num / n };
	}

	/**
	 * Returns the cells of the maze
	 * 
	 * @return the cells of the maze
	 */
	public ArrayList<LinkedList<Integer>> getCells() {
		return cells;
	}

}