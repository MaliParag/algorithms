/**
 * Class representing state information of each node 
 * @author Parag Mali
 *
 */
public enum NodeType {
	
	WALL, // it is a wall
	PATH, // it is part of the current path
	PATH_NOT_AVAILABLE, // no path is available from this node
	PATH_MAY_BE_AVAILABLE // path maybe available from this node
}
