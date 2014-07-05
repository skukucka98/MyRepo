
public class Cpu {
	private double regA;
	private double regB;
	private double regC;
	private double regD;
	private double accumulator;
	
	public Cpu () {
		regA = 1.0;
		regB = 3.0;
		regC = 5.0;
		regD = 7.0;
	    accumulator = 9.0;
	}
	
	public double getRegA() {
		return regA;
	}
	
	public double getRegB() {
		return regB;
	}
	
	public double getRegC() {
		return regC;
	}
	
	public double getRegD() {
		return regD;
	}
	
	public double getAccu() {
		return accumulator;
	}
	
	public void setRegA(double a) {
		regA = a;
	}
	
	public void setRegB(double b) {
		regB = b;
	}
	
	public void setRegC(double c) {
		regC = c;
	}
	
	public void setRegD(double d) {
		regD = d;
	}
	
	public void setAccu(double accu) {
		accumulator = accu;
	}
	
	public void addToAccu(double i) {
		setAccu(accumulator + i);
	}
	
	public void resetRegValues() {
		setRegA(1.0);
		setRegB(3.0);
		setRegC(5.0);
		setRegD(7.0);
		setAccu(9.0);
	}
}
