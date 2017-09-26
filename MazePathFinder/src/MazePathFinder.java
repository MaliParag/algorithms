/**
 * MazePathFinder
 * 
 * Finds path in the given multilevel maze
 * 
 * @author Parag Mali, Sahil Ajmera
 * 
 * Assumptions :
 * 
 * 1. Maze is rectangular and three dimensional
 * 2. To generalize the solution we have assumed that we can not exit from the ceiling. 
 *    We can only exit from the boundary of the maze. 
 *    This program finds path to the ceiling, so both cases are covered.
 * 3. There is at least one entry point
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * To find path from multilevel three dimensional maze
 *
 */

public class MazePathFinder {
	
	private char[][][] maze;
	private Node[][][] nodesMetadata;
	private int levels;
	private int rows;
	private int columns;
	
	//keep track of terminal nodes
	List<Position> terminalPositions;
		
	public MazePathFinder(char[][][] maze) {
		
		this.maze = maze;
		levels = maze.length;
		rows = maze[0].length;
		columns = maze[0][0].length;
		initialize();
		
	}

	//initializes list to store terminal positions and finds node's metadata
	private void initialize() {

		terminalPositions = new ArrayList<Position>();
		nodesMetadata = generateNodesMetadata();
		
	}
	
	
	/*
	 * It extracts following information about each node
	 * 
	 * Where can we move from this node
	 * 
	 */
	private Node[][][] generateNodesMetadata() {
		
		Node[][][] metadata = new Node[levels][rows][columns];
		
		//for each level
		for (int i = 0; i < levels; i++) {
			//for each row
			for (int j = 0; j < rows; j++) {
				//for each node in a row
				for (int k = 0; k < columns; k++) {
						
					//terminal nodes
					if (j == 0 || k == 0 || j == rows - 1 || k == columns - 1) {
						
						//terminal nodes from where we can enter or exit
						if(maze[i][j][k] != Constants.WALL) {
							
							terminalPositions.add(new Position(i, j, k));
						}
						
					}
					
					//checks if node is wall
					if (maze[i][j][k] == Constants.WALL) {
						
						metadata[i][j][k] = new Node(NodeType.WALL);
					
					} else if (isOpenSpaceAbove(i, j, k) || 
							   isOpenSpaceBelow(i, j, k) ||
							   isOpenSpaceFront(i, j, k) ||
							   isOpenSpaceInBack(i, j, k) ||
							   isOpenSpaceOnLeft(i, j, k) ||
							   isOpenSpaceOnRight(i, j, k)) {
								
								metadata[i][j][k] = new Node(NodeType.PATH_MAY_BE_AVAILABLE);
							
					} else {
								
						metadata[i][j][k] = new Node(NodeType.PATH_NOT_AVAILABLE);
					}
					
				}
			}
		}
		
		return metadata;
		
	}
	
	/**
	 * Checks if path is available in front current node
	 * @param i level
	 * @param j row 
	 * @param k column
	 * @return true or false
	 */
	private Boolean isOpenSpaceFront(Integer i, Integer j, Integer k) {
		
		Boolean answer = false;
		
		if(j - 1 >= 0 && maze[i][j - 1][k] == Constants.OPEN_SPACE) {
			
			answer = true;
		}
		
		return answer;
	}
	
	/**
	 * Checks if path is available back of current node
	 * @param i level
	 * @param j row 
	 * @param k column
	 * @return true or false
	 */
	private Boolean isOpenSpaceInBack(Integer i, Integer j, Integer k) {
		
		Boolean answer = false;
		
		if(j + 1 < rows && maze[i][j + 1][k] == Constants.OPEN_SPACE) {
			
			answer = true;
		}
		
		return answer;
	}

	/**
	 * Checks if path is available on left side of current node
	 * @param i level
	 * @param j row 
	 * @param k column
	 * @return true or false
	 */
	private Boolean isOpenSpaceOnLeft(Integer i, Integer j, Integer k) {
		
		Boolean answer = false;
		
		if(k - 1 >= 0 && maze[i][j][k - 1] == Constants.OPEN_SPACE) {
			
			answer = true;
		}
		
		return answer;
	}

	/**
	 * Checks if path is available on right side of current node
	 * @param i level
	 * @param j row 
	 * @param k column
	 * @return true or false
	 */
	private Boolean isOpenSpaceOnRight(Integer i, Integer j, Integer k) {
		
		Boolean answer = false;
		
		if(k + 1 < columns && maze[i][j][k + 1] == Constants.OPEN_SPACE) {
			
			answer = true;
		}
		
		return answer;
	}

	/**
	 * Checks if path is available above of current node
	 * @param i level
	 * @param j row 
	 * @param k column
	 * @return true or false
	 */
	private Boolean isOpenSpaceAbove(Integer i, Integer j, Integer k) {
		
		Boolean answer = false;
		
		if(i + 1 < levels && maze[i + 1][j][k] == Constants.OPEN_SPACE) {
			
			answer = true;
		}
		
		return answer;
	}

	/**
	 * Checks if path is available below current node
	 * @param i level
	 * @param j row 
	 * @param k column
	 * @return true or false
	 */
	Boolean isOpenSpaceBelow(Integer i, Integer j, Integer k) {
		
		Boolean answer = false;
		
		if(i - 1 >= 0 && maze[i - 1][j][k] == Constants.OPEN_SPACE) {
			
			answer = true;
		}
		
		return answer;
	}

	/**
	 * Reads maze information from input file
	 * @param file input file
	 * @throws FileNotFoundException throws exception if file is not found
	 */
	public MazePathFinder(File file) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(file);
		int i = 0;
		int j = 0;
		int k = 0;
		
		//Keep track of number of rows and columns
		while(scanner.hasNext()) {
			
			String str = scanner.nextLine().trim();
			columns = str.length();
			
			if (str.equals("next level")) {
				
				j = j + 1;
				rows = i;
				i = 0;
				
			} else {
				
				i = i + 1;
				
			}
		}
		
		rows = i;
		//Tracks number of levels
		levels = j + 1; 
		scanner.close();
		scanner = new Scanner(file);
		i = 0;
		j = 0;
		k = 0;
		
		maze = new char[levels][rows][columns];
		
		//Create three dimensional maze array
		while(scanner.hasNext()) {
			
			String str = scanner.nextLine();
			
			if (str.equals("next level")) {
				
				//go to next level
				i = i + 1;
				j = 0;
				continue;
			}
			
			for (int l = 0; l < str.length(); l++) {
				
				maze[i][j][l] = str.charAt(l);
			}
			
			j = j + 1;
			
		}
		//initialize nodes in maze with state information
		initialize();
	}
	
	/**
	 * converts input string path into File object to process further
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public MazePathFinder(String filePath) throws FileNotFoundException {
		
		this(new File(filePath));
	}

	/**
	 * Finds out path in the given maze
	 * 
	 */
	public void findPath(){

		Boolean pathFound = false;
		
		for (Position position : terminalPositions) {
			
			//reintialize metadata
			initialize();
			
			//get a start position
			pathFound = findPath(position);
			
			if (pathFound) {
				
				break;
			}
		}
		
		if (!pathFound) {
			
			System.out.println("\n\nNo path found!");
			
		}
		
	}
	
	/**
	 * Helper function to find out path from given maze and stores state information
	 * @param position Entry point
	 * @return True if path is present else false
	 */
	private Boolean findPath(Position position) {
		
		Position startPosition = position;
		nodesMetadata[position.getX()][position.getY()][position.getZ()].setNodeType(NodeType.PATH); 

		Position nextPosition = getNextPossiblePosition(position, NodeType.PATH_MAY_BE_AVAILABLE);
		Boolean pathFound = false;
		
		while (null != nextPosition)
		{
			if (null != nextPosition && !startPosition.equals(nextPosition) && terminalPositions.contains(nextPosition)) {
				
				position = nextPosition;
				nodesMetadata[position.getX()][position.getY()][position.getZ()].setNodeType(NodeType.PATH);
				pathFound = true;
				break;
				
			}
			
			//move ahead until you can go
			while(null != nextPosition) {
				
				position = nextPosition;
				nodesMetadata[position.getX()][position.getY()][position.getZ()].setNodeType(NodeType.PATH); 
				
				nextPosition = getNextPossiblePosition(position, NodeType.PATH_MAY_BE_AVAILABLE);
				
				if (null != nextPosition && terminalPositions.contains(nextPosition)) {
					
					nodesMetadata[nextPosition.getX()][nextPosition.getY()][nextPosition.getZ()].setNodeType(NodeType.PATH);
					System.out.println("\n\nPath found!");
					pathFound = true;
					break;
				}
			}
			
			if(pathFound) {
				
				break;
			}
			
			//no path from current node. Mark it as path not available from this node
			nodesMetadata[position.getX()][position.getY()][position.getZ()].setNodeType(NodeType.PATH_NOT_AVAILABLE);
			
			//go back to a node from where we can try alternate path
			nextPosition = getNextPossiblePosition(position, NodeType.PATH);
		}
		
		return pathFound;
	}

	/**
	 * Find outs next possible position to follow from current position
	 * @param position current position
	 * @param nodeType Enum storing information about state of a particular node
	 * @return
	 */
	private Position getNextPossiblePosition(Position position, NodeType nodeType) {
		
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		Position nextPosition = null;
		
		//Checks all possible positions to jump
		if(isPathAvailableOnLeft(x, y, z, nodeType)) {
			
			nextPosition = new Position(x, y, z - 1);
		
		} else if (isPathAvailableOnRight(x, y, z, nodeType)) {
			
			nextPosition = new Position(x, y, z + 1);
			
		} else if (isPathAvailableFront(x, y, z, nodeType)) {
			
			nextPosition = new Position(x, y + 1, z);
			
		} else if (isPathAvailableBack(x, y, z, nodeType)) {
			
			nextPosition = new Position(x, y - 1, z);
			
		} else if (isPathAvailableAbove(x, y, z, nodeType)) {
			
			nextPosition = new Position(x + 1, y, z);
			
		} else if (isPathAvailableBelow(x, y, z, nodeType)) {
			
			nextPosition = new Position(x - 1, y, z);
		}
		
		System.out.println("Next possible postion : " + nextPosition);
		return nextPosition;
		
	}
	
	/**
	 * Checks if path is available on left side
	 * @param x	level
	 * @param y	row
	 * @param z column
	 * @param nodeType Enum object storing state information of particular node
	 * @return
	 */
	private Boolean isPathAvailableOnLeft(int x, int y, int z, NodeType nodeType) {
		
		Boolean answer = false;
		
		if (z - 1 >= 0 && nodesMetadata[x][y][z - 1].getNodeType() == nodeType) {
			
			answer = true;
		}
		
		return answer;
	}
	
	/**
	 * Checks if path is available on right side
	 * @param x	level
	 * @param y	row
	 * @param z column
	 * @param nodeType Enum object storing state information of particular node
	 * @return
	 */
	private Boolean isPathAvailableOnRight(int x, int y, int z, NodeType nodeType) {
		
		Boolean answer = false;
		
		if (z + 1 < columns && nodesMetadata[x][y][z + 1].getNodeType() == nodeType) {
			
			answer = true;
		}
		
		return answer;
	}
	
	/**
	 * Checks if path is available on front side
	 * @param x	level
	 * @param y	row
	 * @param z column
	 * @param nodeType Enum object storing state information of particular node
	 * @return
	 */
	private Boolean isPathAvailableFront(int x, int y, int z, NodeType nodeType) {
		
		Boolean answer = false;
		
		if (y + 1 < rows && nodesMetadata[x][y + 1][z].getNodeType() == nodeType) {
			
			answer = true;
		}
		
		return answer;
	}
	
	/**
	 * Checks if path is available on back side
	 * @param x	level
	 * @param y	row
	 * @param z column
	 * @param nodeType Enum object storing state information of particular node
	 * @return
	 */
	private Boolean isPathAvailableBack(int x, int y, int z, NodeType nodeType) {
		
		Boolean answer = false;
		
		if (y - 1 >= 0 && nodesMetadata[x][y - 1][z].getNodeType() == nodeType) {
			
			answer = true;
		}
		
		return answer;
	}
	
	/**
	 * Checks if path is available on above side
	 * @param x	level
	 * @param y	row
	 * @param z column
	 * @param nodeType Enum object storing state information of particular node
	 * @return
	 */
	private Boolean isPathAvailableAbove(int x, int y, int z, NodeType nodeType) {
		
		Boolean answer = false;
		
		if (x + 1 < levels && nodesMetadata[x + 1][y][z].getNodeType() == nodeType) {
			
			answer = true;
		}
		
		return answer;
	}

	/**
	 * Checks if path is available on below side
	 * @param x	level
	 * @param y	row
	 * @param z column
	 * @param nodeType Enum object storing state information of particular node
	 * @return
	 */
	private Boolean isPathAvailableBelow(int x, int y, int z, NodeType nodeType) {
		
		Boolean answer = false;
		
		if (x - 1 >= 0 && nodesMetadata[x - 1][y][z].getNodeType() == nodeType) {
			
			answer = true;
		}
		
		return answer;
	}
	
	/**
	 * Prints out complete path with '~' character
	 */
	public void printPath() {
		
		System.out.println("\n\n");
		//for each level
		for (int i = 0; i < levels; i++) {
			//for each row
			for (int j = 0; j < rows; j++) {
				//for each node in a row
				for (int k = 0; k < columns; k++) {
		
					if (nodesMetadata[i][j][k].getNodeType() == NodeType.PATH) {
						
						System.out.print(" ~");
						
					} else {
						
						System.out.print(" " + maze[i][j][k]);
					}			
				}
				
				System.out.println("\n");
			}
			
			System.out.println("\n\n");
		}
		
	}
	
}



