import java.io.IOException;
import java.io.OutputStream;
import javafx.scene.control.TextArea;

//creating a new class to redirect Standard output onto my GUI
//create new class of OutputStream type

public class OutputGUI extends OutputStream{
	TextArea outputarea;

	//override write method to allow writing to FX TextArea
		@Override
		public void write(int b) throws IOException {
			outputarea.appendText(String.valueOf((char) b));

		}
	// define a constructor 	
		public OutputGUI (TextArea output) {
			this.outputarea = output;
		}
}
