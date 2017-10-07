import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Init {
	
	static File aXML;
	static File aJava;
	
	static File tXML;
	static File tJava;
	
	static String aXMLDir = "C:/autontest/";
	static String aJavaDir = "C:/autontest/";
	
	static String tXMLDir = "C:/teleoptest/";
	static String tJavaDir = "C:/teleoptest/";

	public static void autonInit(){
		
		aXML = new File(aXMLDir + "Auton.xml");
		aJava = new File(aJavaDir + "Auton.java");
		
		if(aXML.exists()){
			try {
				RandomAccessFile clearAXML = new RandomAccessFile(aXML,"rw");
				try {
					clearAXML.setLength(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				aXML.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(aJava.exists()){
			try {
				RandomAccessFile clearAJava = new RandomAccessFile(aJava,"rw");
				try {
					clearAJava.setLength(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				aJava.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
	}
	
	public static void teleopInit(){
		
		tXML = new File(tXMLDir + "Teleop.xml");
		tJava = new File(tJavaDir + "Teleop.java");
		
		if(tXML.exists()){
			try {
				RandomAccessFile clearTXML = new RandomAccessFile(tXML,"rw");
				try {
					clearTXML.setLength(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				tXML.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(tJava.exists()){
			try {
				RandomAccessFile clearTJava = new RandomAccessFile(tJava,"rw");
				try {
					clearTJava.setLength(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				tJava.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
	}
}
