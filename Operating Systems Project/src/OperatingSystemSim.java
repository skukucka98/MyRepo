
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class OperatingSystemSim {
	public static void main (String[] args) throws NumberFormatException, IOException {
		File file = new File("C:\\Users\\Steven\\workspace\\Operating Systems Project\\src\\Files\\ugradPart1.txt");
		HardDrive hardDrive = new HardDrive(file);
		//I'm using a RAM space of 100 but you can change this int to any other number if you would like.
		int ramSpace = 100;
		int ramUsage = 0;
		int cycle = 1;
		Vector<Job> ram = new Vector<Job>();
		
		hardDrive.sort();		

		for (Job job : hardDrive.getHardDrive()) {
			//When the next job in the queue can not fit in the allotted RAM space then we print out this cycle's information.
			if (((ramUsage + job.getLinesOfInstructions()) > ramSpace)) {
				System.out.println("-------------------------------------------------------------------------------");
				System.out.println("Ram usage after cycle " + cycle + ": " + ramUsage + "/" + ramSpace);
				System.out.println("Jobs in cycle " + cycle + "\n");
				for (Job currentJob : ram) {
					System.out.println(currentJob.toString());
				}
				cycle++;
				ram.clear();
				ramUsage = 0;
			}
			ram.add(job);
			ramUsage += job.getLinesOfInstructions();	
			if (job.equals(hardDrive.getHardDrive().lastElement())) {
				System.out.println("-------------------------------------------------------------------------------");
				System.out.println("Ram usage after cycle " + cycle + ": " + ramUsage + "/" + ramSpace);
				System.out.println("Jobs in cycle " + cycle + "\n");
				for (Job currentJob : ram) {
					System.out.println(currentJob.toString());
				}
			}
		}
	}
}
