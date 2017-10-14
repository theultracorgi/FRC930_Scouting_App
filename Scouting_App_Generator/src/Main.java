import java.io.IOException;

public class Main {
	static AutonWidgetGenerator awg = new AutonWidgetGenerator();
	public static void main(String[] args) {
		String[] buff = new String[10];
		Setup.autonInit();
		Setup.teleopInit();
		awg.autonCounterGenerator("sup", 1, "ma dude");
		awg.autonRadioButtonGenerator("s", 6);
		
		
		Setup.closeWriters();
		
	}

}
