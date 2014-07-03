import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;


public class HardDrive {
	private Vector<Job> hardDrive;
	
	public HardDrive(File file) throws IOException {
		hardDrive = new Vector<Job>();
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		Job job = new Job();
		Vector<Instruction> instructions = new Vector<Instruction>();
		boolean firstLine = true;
		while ((line = in.readLine()) != null) {
			String[] tokens = line.split(", ");
			if (tokens[0].charAt(0) == 'J') {
				if (firstLine) {
					job = new Job(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
					firstLine = false;
				} else {
					Vector<Instruction> temp = new Vector<Instruction>();
					for (Instruction i : instructions) {
						temp.add(i);
					}
					Job j = new Job (job.getJobNumber(), job.getLinesOfInstructions(), job.getPriority(), temp);
					hardDrive.add(j);
					job = new Job(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
					instructions.clear();
				}
			} else {
				Instruction i = new Instruction(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]));
				instructions.add(i);
			}
		}
		Job j = new Job (job.getJobNumber(), job.getLinesOfInstructions(), job.getPriority(), instructions);
		hardDrive.add(j);
		in.close();
	}
	
	public Vector<Job> getHardDrive() {
		return hardDrive;
	}
	
	
	//sorts jobs in a vector based on priority and lines of instruction.
	public void sort () {
		for (int i = 0; i < hardDrive.size() - 1; i++) {
			for (int j = i + 1; j < hardDrive.size(); j++) {
				if (hardDrive.get(i).getPriority() > hardDrive.get(j).getPriority()) {
					Collections.swap(hardDrive, i, j);
				}
				if (hardDrive.get(i).getPriority() == hardDrive.get(j).getPriority()) {
					if (hardDrive.get(i).getLinesOfInstructions() > hardDrive.get(j).getLinesOfInstructions()) {
						Collections.swap(hardDrive, i, j);
					}
					else if (hardDrive.get(i).getLinesOfInstructions() == hardDrive.get(j).getLinesOfInstructions()) {
						Collections.swap(hardDrive, i+1, j);
					}
				}
			}
		}
	}
}
