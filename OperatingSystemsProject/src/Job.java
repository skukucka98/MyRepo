import java.util.Vector;


public class Job {
	private String jobNumber;
	private int linesOfInstructions;
	private int priority;
	private Vector<Instruction> instructions;
	private int waitTime;
	private double valRegA;
	private double valRegB;
	private double valRegC;
	private double valRegD;
	private double valAccu;
	
	public Job (String jn, int loi, int p, Vector<Instruction> i) {
		jobNumber = jn;
		linesOfInstructions = loi;
		priority = p;
		instructions = i;
	}
	
	public Job (String jn, int loi, int p) {
		jobNumber = jn;
		linesOfInstructions = loi;
		priority = p;
	}
	
	public Job () {}
	
	public String getJobNumber() {
		return jobNumber;
	}
	
	public int getLinesOfInstructions() {
		return linesOfInstructions;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public Vector<Instruction> getInstructions() {
		return instructions;
	}
	
	public int getWaitTime() {
		return waitTime;
	}
	
	public void setWaitTime(int i) {
		waitTime = i;
	}
	
	public void decWaitTime() {
		waitTime = waitTime - 1;
	}
	
	public void removeInstruction(Instruction i) {
		instructions.remove(i);
	}
	
	public void storePCB(Cpu c, double regA, double regB, double regC, double regD, double accu) {
		valRegA = regA;
		valRegB = regB;
		valRegC = regC;
		valRegD = regD;
		valAccu = accu;
	}
	
	public double getValRegA() {
		return valRegA;
	}
	
	public double getValRegB() {
		return valRegB;
	}
	
	public double getValRegC() {
		return valRegC;
	}
	
	public double getValRegD() {
		return valRegD;
	}
	
	public double getValAccu() {
		return valAccu;
	}
	
	public boolean equals(Job otherJob) {
		return jobNumber.equals(otherJob.getJobNumber());
	}
	
	public String toString() {
		StringBuilder instructionString = new StringBuilder();
		for (Instruction i : instructions) {
			instructionString.append(i.toString() + "\n"); 
		}
		String is = instructionString.toString();
		return getJobNumber() + "\nLines of Instructions: " + getLinesOfInstructions() +
			   "\nPriority Level: " + getPriority() + 
			   "\n\tInstructions\n" + is;
			   
	}
}