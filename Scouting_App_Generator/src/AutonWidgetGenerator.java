import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AutonWidgetGenerator {

	
	static FileWriter aXMLWriter;
	static FileWriter aJavaWriter;
	
	
	public static void autonCounterGenerator(String displayText, int startNum, String counterText){
		try {
			aXMLWriter = new FileWriter(Init.aXML);
			aJavaWriter = new FileWriter(Init.aJava); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			RandomAccessFile raf = new RandomAccessFile(Init.aXML, "rw");	
			
			try {
				raf.seek(raf.length());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void autonRadioButtonGenerator(String displayText, int numButtons, String[] buttonLabels){
		try {
			aXMLWriter = new FileWriter(Init.aXML);
			aJavaWriter = new FileWriter(Init.aJava); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
