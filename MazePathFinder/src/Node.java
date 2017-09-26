
/**
 * We can add more information to this class, if we need to find shortest path, etc.
 * @author Parag Mali
 */
public class Node{
	
	NodeType nodeType;
	
	public Node(NodeType nodeType) {
		
		this.nodeType = nodeType;
	}


	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [nodeType=");
		builder.append(nodeType);
		builder.append("]");
		return builder.toString();
	}	
	
}