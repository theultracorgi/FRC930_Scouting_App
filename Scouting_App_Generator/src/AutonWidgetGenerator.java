import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AutonWidgetGenerator {


	
	
	public static void autonCounterGenerator(String displayText, int startNum, String counterText){
		
		try {
			Setup.aJavaWriter.append("sup");
			Setup.aJavaWriter.append("suppies");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}

	
	public static void autonRadioButtonGenerator(String displayText, int numButtons){
		
		try {
			RandomAccessFile raf = new RandomAccessFile(Setup.aJava, "rw");
			raf.seek(raf.length());
			Setup.aJavaWriter.append("sund");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
