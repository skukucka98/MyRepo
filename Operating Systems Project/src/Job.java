import java.util.Vector;


public class Job {
	private String jobNumber;
	private int linesOfInstructions;
	private int priority;
	private Vector<Instruction> instructions;
	
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