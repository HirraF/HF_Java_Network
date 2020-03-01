import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Network {
	public HashSet<Node> Nodelist;
	public HashSet<Edge> Edgelist;
	
// default constructor which makes empty network 
	public Network() {		
		this.Nodelist= new HashSet<Node>();
		this.Edgelist= new HashSet<Edge>();
	}

// method to read file to make network 
//splits whitespace delimted lines into nodes. connects nodes on a line to a edge
	public Network CreateNetwork(String filename) throws Exception {
		Path file = Paths.get(filename);
		try (BufferedReader reader = Files.newBufferedReader(file)){ 
		String line = null;
		while ((line=reader.readLine())!= null ) {
			String [] elements= line.split("\\s+");
			// check if file looks properly formatted 
			if (elements.length != 2) {
				throw new IOException("\n File incorrectly formatted. Correct format is one edge per line with Nodes separated by whitespace");
			}
			String x1= elements[0];
			String x2=elements[1];
			Node nodea = new Node (x1);
			Node nodeb = new Node(x2);
			Edge edge1= new Edge(nodea, nodeb);
			this.Nodelist.add(nodea);
			this.Nodelist.add(nodeb);
			this.Edgelist.add(edge1);
		}		
		reader.close();
		System.out.println("Number of Nodes: "+ this.Nodelist.size());
		System.out.println("Number of Edges: "+ this.Edgelist.size());
		}		
		catch(NoSuchFileException e){
			System.out.println("\n File not found, Try again");	
			System.err.format("\n IOException: %s \n", e);
		}
		catch(IOException x) {
			System.err.format("\n IOException: %s \n", x);
		}		
		return this;
	}
	
// adding new interaction into the network using string args 
	public Network AppendNetwork (String name1, String name2) {
		Node node1= new Node(name1);
		Node node2= new Node(name2);
		this.Nodelist.add(node1);
		this.Nodelist.add(node2);
		Edge edgex = new Edge (node1, node2);
		//message to user if they try to add duplicate edge
		if(this.Edgelist.contains(edgex)) {
			System.out.println("\n Edge already exists");
		}
		else {
			this.Edgelist.add(edgex);
			System.out.format("\n Created edge between %s and %s \n", node1.Name, node2.Name);
		}		
		return this;
	}
	
// NETWORK ANALYSIS METHODS 
	
// building HashMap of degree of each node <node name, degree>:
// loop calculate how many edges a node is involved in (its degree)
	public HashMap<String, Integer> DegreeMap (){
		HashMap<String,Integer> NodeDegrees = new HashMap <String, Integer>();
		int counter;
		for (Node node : this.Nodelist) {
			counter=0;
			for (Edge edge : this.Edgelist) {
				if (edge.Nodesl.contains(node.Name)) {
					counter=counter+1;
				}
			}
			NodeDegrees.put(node.Name, counter);
		}
	return NodeDegrees;
	}
	
//return degree of a given node.
	public int DegreeNode(String nodename) {
		int degree=0;
		Node nodex = new Node (nodename);
		// does node exist in network, if so get its degree from DegreeMap.
		if (this.Nodelist.contains(nodex)){	
			degree = this.DegreeMap().get(nodename);
			System.out.format("\n Degree of node %s: "+ degree +"\n", nodename);
		}
		else {
			System.out.println(" \n Node does not exist");
		}
		return degree;
	}
// Write a method to calculate the average degree of all nodes in the network.	
	public double AvgDegNode() {
		double avgdegnode = 0;
		try {
		int sumdeg=0;
		for (Map.Entry<String,Integer> entry: this.DegreeMap().entrySet()) {
			sumdeg=sumdeg+entry.getValue();
		}
		avgdegnode = (sumdeg/this.Nodelist.size());
		System.out.format("\n The average node degree is: %.2f \n",avgdegnode);
		}
		catch(ArithmeticException e){
			System.err.format("\n Exception %s",e);
			if(this.Nodelist.isEmpty()) {
				System.out.println("\n No network loaded. Try again");
			}
		}
	return avgdegnode;
	}

//method to find the hubs of the network, which are the nodes of highest degree. 
//The method should print the value of the highest degree and the names of all nodes having that degree.
	public int Hub() {
		int max = 0;
		try {
		ArrayList<String> hublist= new ArrayList<String>();
		max= Collections.max(this.DegreeMap().values()); 	// get max degree
		//search DegreeMap HashMap of nodes and their degrees for nodes with the max degree
		for(Map.Entry <String, Integer> element : this.DegreeMap().entrySet()) {
			if (element.getValue() == max) {
				hublist.add(element.getKey()); 								
			}
		}
		System.out.println(" \n Max degree of a node is :" + max);
		System.out.println("Nodes identified as Hubs:" + hublist);
		}
		catch (Exception e){
			System.err.format("\n Exception %s",e);
			if(this.Nodelist.isEmpty()) {
				System.out.println("\n No network loaded. Try again");
			}
		}
		return max;
	}
//Write a method enabling the user to save the full degree distribution of the network in the form of a table. 
//The first column of the table should contain the degree and the second column the number of nodes having that degree.
	
// created hashmap: degdist{ key= degree, value = number of nodes with that degree}
	
	public HashMap<Integer, Integer> DegreeDistribution(String fileout){
		Path file = Paths.get(fileout);
		HashMap<Integer, Integer> degdist = new HashMap<Integer, Integer>();
		// get the degrees present in our network. 
		for(Integer i: this.DegreeMap().values()) {
			if(!degdist.containsKey(i)) {
				degdist.put(i, null);			
			}
		}
		// for each degree count how many nodes have this degree
		for(Integer i: degdist.keySet()) {
			int freq=0;
			for(Map.Entry <String, Integer> element : this.DegreeMap().entrySet()) {
				if(element.getValue()== i) {
					freq=freq+1;
					}
			}
			degdist.put(i, freq);		
		}
		//write to file.
		try(BufferedWriter writer = Files.newBufferedWriter(file)){
			writer.write("Degree (k) : Frequency N(k) \n");
			for(Map.Entry <Integer, Integer> elm : degdist.entrySet()) {
				writer.write(elm.getKey()+ "	"+ elm.getValue()+ "\n");
			}
		writer.close();	
		}
		catch (IOException x) {
				System.err.format("\n IOException: %s \n", x);
		}

		return degdist;
	}
}
