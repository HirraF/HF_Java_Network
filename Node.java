
public class Node {
	public String Name;

//default Constructor- empty node	
	public Node() {
		this.Name="";
		}
// Constructor- node with given name	
	public Node (String namegiven){
		this.Name= namegiven;
		}

// method to return name of node	
	public String NodeName(Node nodex) {
		return nodex.Name;
		}
// overwrite equals method. ensure one of each node only.	
	public boolean equals (Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Node )) return false;
		Node other = (Node ) obj;
		if (!Name.equals(other.Name)) return false;
		return true;
		}
		
// overwrite hashcode	
	public int hashCode() {
		return Name.hashCode();
	}
	}

