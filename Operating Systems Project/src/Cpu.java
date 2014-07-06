
public class Cpu {
	private int regA;
	private int regB;
	private int regC;
	private int regD;
	private int accumulator;
	
	public Cpu (int a, int b, int c, int d, int acc) {
		regA = a;
		regB = b;
		regC = c;
		regD = d;
	    accumulator = acc;
	}
	
	public int getRegA() {
		return regA;
	}
	
	public int getRegB() {
		return regB;
	}
	
	public int getRegC() {
		return regC;
	}
	
	public int getRegD() {
		return regD;
	}
	
	public int getAccu() {
		return accumulator;
	}
	
	public void setRegA(int a) {
		regA = a;
	}
	
	public void setRegB(int b) {
		regB = b;
	}
	
	public void setRegC(int c) {
		regC = c;
	}
	
	public void setRegD(int d) {
		regD = d;
	}
	
	public void setAccu(int accu) {
		accumulator = accu;
	}
	
	public void addToAccu(int i) {
		setAccu(accumulator + i);
	}
}
