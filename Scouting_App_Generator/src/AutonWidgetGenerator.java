import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AutonWidgetGenerator {
	
	static File aXML;
	static File aJava;
	
	static FileWriter aXMLWriter;
	static FileWriter aJavaWriter;

	public static void init(){
		
		aXML = new File("C:/autontest/Auton.xml");
		aJava = new File("C:/autontest/Auton.xml");
		
		try {
			aXMLWriter = new FileWriter(aXML,true);
			aJavaWriter = new FileWriter(aJava,true);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		if(aXML.exists()){
			try {
				RandomAccessFile clearXML = new RandomAccessFile(aXML,"rw");
				try {
					clearXML.setLength(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(aJava.exists()){
			try {
				RandomAccessFile clearJava = new RandomAccessFile(aJava,"rw");
				try {
					clearJava.setLength(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
