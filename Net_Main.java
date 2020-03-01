import javafx.application.Application;
import java.io.PrintStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Net_Main extends Application{
	public void start(Stage primaryStage) throws Exception{
		 Network mynetwork= new Network(); //create a blank network
		 TextArea output = new TextArea();
		 output.setWrapText(true);
		 
		 // build network items
		 // set their aesthetics as well 
		 Label buildtitle = new Label("Build Your Network");
		 buildtitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 18));
		 buildtitle.setUnderline(true);
		 Label lbl1 = new Label ("File to read: ");
		 TextField txt1 = new TextField();
		 Tooltip t1 =new Tooltip("Give file path where edge list is stored. Example: /User/myfolder/mynetwork.txt");
		 txt1.setTooltip(t1);
		 Button btn1= new Button ("Create Network");
		 
		 //adding interactions to the network items
		 Label newintlbl = new Label ("Add a new interaction to your Network");
		 newintlbl.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		 Label node1name = new Label("Node:");
		 Label node2name = new Label("Node:");
		 TextField newn1 = new TextField();
		 TextField newn2 = new TextField();
		 Tooltip t2 =new Tooltip("Remember node names are case sensitive");
		 newn1.setTooltip(t2);
		 newn2.setTooltip(t2);
		 Button btn2 = new Button ("Add Interaction ");
		 
		 //analysing the network items 
		 Label analysistitle = new Label("Analyse Your Network");
		 analysistitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 18));
		 analysistitle.setUnderline(true);
		 Label degtitle = new Label ("Get the degree of a node");
		 Label deginfo = new Label("Type in the name of nodes you wish to create and edge between. These nodes can be new to the network");
		 deginfo.setFont(Font.font("Arial", FontPosture.ITALIC,12));
		 degtitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		 Label lblgetdeg = new Label("Node Name:");
		 TextField thenode= new TextField();
		 thenode.setTooltip(t2);
		 Button btngetdeg = new Button ("Get Degree");
		 
		 //average degree items
		 Label titleavgdeg = new Label("Print the average degree of all Nodes");
		 titleavgdeg.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		 Button btnAvgDeg = new Button ("Get Average Degree");
		 
		 //Get hubs items
		 Label titlehub = new Label("Print the degree and identity of Hubs");
		 titlehub.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		 Button btnHub = new Button ("Get Hubs");
		 
		 //print deg distribution items
		 Label titleoutput = new Label("Print the Degree Distribution to an output file");
		 titleoutput.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		 titleoutput.setUnderline(true);
		 Label outputlbl = new Label ("Give output file destination:");
		 outputlbl.setWrapText(true);
		 Tooltip tt =new Tooltip("Give file path followed by chosen_filename.txt. to save in a specific location Example: /User/myfolder/file3.txt");
		 TextField outputfile = new TextField();
		 outputfile.setTooltip(tt);
		 Button btnDegdist= new Button ("Print to File");
		 
		 Label helplbl= new Label("You can hover over any input field to get more info.");
		 helplbl.setFont(Font.font("Arial", FontPosture.ITALIC, 13));
				 
		 
	
		 //custom printstream which redirects std.out to the GUI text area
		 PrintStream ps = new PrintStream(new OutputGUI(output));
		 //standardOut=System.out;
		 System.setOut(ps);
		 System.setErr(ps);
		 
		 //button for creating network. Read infile name and create
		  btn1.setOnAction(new EventHandler<ActionEvent>(){
			  public void handle (ActionEvent event){
				  try {
					mynetwork.CreateNetwork(txt1.getText());
				} catch (Exception e) {
					System.err.print(e.getMessage());
				} 
				  } });
		  
		  //add action to  new interaction button
		  btn2.setOnAction(new EventHandler <ActionEvent>(){
			  public void handle (ActionEvent event1) {
				  if(!newn1.getText().isBlank()&& !newn2.getText().isBlank()) {
					  mynetwork.AppendNetwork(newn1.getText().strip(), newn2.getText().strip());
					  System.out.println("Number of nodes: "+ mynetwork.Nodelist.size());
					  System.out.println("Number of Edges: "+mynetwork.Edgelist.size());					  
				  } 
				  } });

		  //Add action get degrees button
		  btngetdeg.setOnAction(new EventHandler <ActionEvent>() {
			  public void handle (ActionEvent event){
				  if(!mynetwork.Nodelist.isEmpty()) {
					  mynetwork.DegreeNode(thenode.getText().strip());	  
				 }
				  else {
					  System.out.println("\n No network loaded, create a network and try again");
				  }	 
			  } });
		  
		  //Add action get average degree button
		  btnAvgDeg.setOnAction(new EventHandler <ActionEvent>(){
			  public void handle (ActionEvent event){
					  mynetwork.AvgDegNode();
			  }		  
		  });
		  // add action to Hubs button
		  btnHub.setOnAction(new EventHandler <ActionEvent>(){
			  public void handle (ActionEvent event){
					  mynetwork.Hub();	
			  }	   });
		 
		  // adding functionality to allow output to file 
		  btnDegdist.setOnAction(new EventHandler <ActionEvent>() {
			  public void handle (ActionEvent event){
				  mynetwork.DegreeDistribution(outputfile.getText());
				  if(!outputfile.getText().isBlank()) {
					  System.out.format("\n Printed to file: %s \n", outputfile.getText());
				  }
			  }
		  });
		  
// adding and placing children in a gridpane 
// setting their alignments within grid pane and how they will sit relative to each other 
		  GridPane root = new GridPane();
		  root.setStyle("-fx-background-color: #eef2f5 ;");
		  root.getColumnConstraints().add(new ColumnConstraints(100));

		  //root.setGridLinesVisible(true);
		  root.setVgap(4); 
		  root.setHgap(2);
		  root.setPadding(new Insets(20,20,20,20));
		  
		  
		  root.add(buildtitle,0,0,2,1);
		  GridPane.setHalignment(buildtitle, HPos.LEFT);
		  root.add(lbl1,0,1,1,1);
		  root.add(txt1,1,1);
		  root.add(btn1,0,2,2,1);
		  GridPane.setHalignment(btn1, HPos.RIGHT);
		  
		  root.add(newintlbl,0,3,2,1);
		  root.add(deginfo,0,4,2,1);
		  GridPane.setMargin(deginfo, new Insets(0, 0, 10, 0));
		  deginfo.setWrapText(true);
		  GridPane.setHalignment(newintlbl, HPos.LEFT);
		  GridPane.setMargin(newintlbl, new Insets(10, 0, 0, 0));
		  root.add(node1name, 0, 5,1,1);
		  root.add(newn1,1,5,1,1);
		  root.add(node2name,0,6,1,1);
		  root.add(newn2,1,6,1,1);
		  root.add(btn2,0,7,2,1);
		  GridPane.setHalignment(btn2, HPos.RIGHT);
		  
		  root.add(analysistitle,0,8,2,1);
		  GridPane.setHalignment(analysistitle, HPos.LEFT);
		  GridPane.setMargin(analysistitle, new Insets(40, 0, 5, 0));
		  root.add(degtitle,0,9,2,1);
		  GridPane.setHalignment(degtitle, HPos.LEFT);
		  root.add(lblgetdeg,0,10,1,1);
		  root.add(thenode,1,10,1,1);
		  root.add(btngetdeg,0,11,2,1);
		  GridPane.setHalignment(btngetdeg, HPos.RIGHT);
		  
		  root.add(titleavgdeg, 0,12,2,1);
		  GridPane.setHalignment(titleavgdeg, HPos.LEFT);
		  GridPane.setMargin(titleavgdeg, new Insets(10, 0,0, 0));
		  root.add(btnAvgDeg,0,13,2,1);
		  GridPane.setHalignment(btnAvgDeg, HPos.LEFT);
		  
		  root.add(titlehub,0, 14,2,1);
		  GridPane.setHalignment(titlehub, HPos.LEFT);
		  GridPane.setMargin(titlehub, new Insets(18,0,0,0));
		  root.add(btnHub,0,15,2,1);
		  GridPane.setHalignment(btnHub, HPos.LEFT);
		  GridPane.setMargin(btnHub, new Insets(0,0,10,0));
		  
		  root.add(titleoutput,0,16,2,1);
		  GridPane.setHalignment(titleoutput, HPos.LEFT);
		  GridPane.setMargin(titleoutput, new Insets(40,0,0,0));
		  root.add(outputlbl,0,17,1,1);
		  GridPane.setMargin(outputlbl, new Insets(0,0,0,0));
		  root.add(outputfile,1,17,6,1);
		  GridPane.setMargin(outputfile, new Insets(0,0,0,0));
		  root.add(btnDegdist,0,18,2,1);
		  GridPane.setMargin(btnDegdist, new Insets(0,0,0,0));
		  GridPane.setHalignment(btnDegdist, HPos.RIGHT);

		  root.add(output,2,0,10,19);
		  GridPane.setHalignment(output, HPos.CENTER);
		  GridPane.setMargin(output, new Insets(0,0,0,20));
		  
		  root.add(helplbl, 0, 19,2,1);
		  
		  Scene scene = new Scene(root, 900, 750);
		  primaryStage.setTitle("Network Analysis ");
		  primaryStage.setScene(scene);
		  primaryStage.show();
		
	 }
	

public static void main(String[] args) {
		launch(args);
	}
}
