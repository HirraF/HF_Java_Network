import java.util.ArrayList;
public class Edge {
	public String node1;
	public String node2;
	public ArrayList<String> Nodesl;
	public String connection;

// constructor accepting 2 nodes. creates edge between 	
	public Edge(Node nodeA, Node nodeB) {
		this.node1= nodeA.Name;
		this.node2=nodeB.Name;
		this.Nodesl= new ArrayList<String>();
		this.Nodesl.add(node1);
		this.Nodesl.add(node2);
		connection= node1 +"	"+ node2;		
		}
		
// overwrite equals - ensure one of each edge 	
	public boolean equals (Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Edge)) return false;
		Edge other = (Edge) obj;
		//check self-int 
		if (node1.equals(node2) && !other.node1.equals(other.node2)) return false;
		if (node1.equals(node2) && other.node1.equals(other.node2)) {
			if (node1.equals(other.node1)) return true;
			if (!node1.equals(other.node1)) return false;
		}
		if (!Nodesl.contains(other.node1) && !Nodesl.contains(other.node2)) return false;
		if (Nodesl.contains(other.node1) && Nodesl.contains(other.node2)) return true;
		return true;
		} 
		
		
// overwrite hashcode	
	public int hashCode() {
		int hash=1;
		hash=(node1.hashCode()+node2.hashCode())*31;
		return hash;
		}
	}



