import java.io.FileNotFoundException;

/**
 * Main class to take maze details as input and prints out the path if available
 * @author Parag Mali
 *
 */
public class Solution {

	public static void main(String[] args) throws FileNotFoundException {
	
		MazePathFinder mazePathFinder = new MazePathFinder(args[0]);
		
		mazePathFinder.findPath();
		mazePathFinder.printPath();
		
	}


}
