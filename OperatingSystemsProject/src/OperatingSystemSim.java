
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

public class OperatingSystemSim {
	public static void main (String[] args) throws NumberFormatException, IOException {
		Scanner scan = new Scanner(System.in);
		File file = new File("E:\\Workspace\\Operating Systems Project\\src\\Files\\ugradPart1.txt");
		HardDrive hardDrive = new HardDrive(file);
		//I'm using a RAM space of 100 but you can change this int to any other number if you would like.
		int ramSpace = 100;
		int ramUsage = 0;
		Vector<Job> ram = new Vector<Job>();
		//Will implement readyQueue as LinkedList to avoid complications.
		LinkedList<Job> readyQueue = new LinkedList<Job>();
		Queue ioQueue = new Queue();
		Queue waitQueue = new Queue();
		
		int ch = 0;
		while (ch != 1 && ch != 2 && ch != 3) {
			System.out.println("What kind of Algorithm would you like to use? (1 for FIFO, 2 for SJF, and 3 for Priority)");
			ch = scan.nextInt();
			if (ch != 1 && ch != 2 && ch != 3) {
				System.out.println("Incorrect submission");
			}
		}
		scan.close();
		System.out.println();
		if (ch == 2) {
			hardDrive.SjfSort();	
		} else if (ch == 3) {
			hardDrive.prioritySort();
		}

		for (Job job : hardDrive.getHardDrive()) {
			//When the next job in the queue can not fit in the allotted RAM space then we print out this cycle's information.
			if (((ramUsage + job.getLinesOfInstructions()) > ramSpace) || job.equals(hardDrive.getHardDrive().lastElement())) {
				for (Job j : ram) {
					readyQueue.add(j);
				}
				Cpu cpu = new Cpu();
				while (!readyQueue.isEmpty() || !ioQueue.getQueue().isEmpty() || !waitQueue.getQueue().isEmpty()) {
					//Process each job in the first spot of the readyQueue
					processing: {
						//this is to ensure that the while loop will not break when jobs are still available.
						if (readyQueue.isEmpty() && (!ioQueue.getQueue().isEmpty() || !waitQueue.getQueue().isEmpty())) {
							for (Iterator<Job> iter = ioQueue.getQueue().iterator(); iter.hasNext();) {
								Job qJob = iter.next();
								qJob.decWaitTime();
								if (qJob.getWaitTime() == 0) {
									readyQueue.add(qJob);
									cpu.setRegA(qJob.getValRegA());
									cpu.setRegB(qJob.getValRegB());
									cpu.setRegC(qJob.getValRegC());
									cpu.setRegD(qJob.getValRegD());
									cpu.setAccu(qJob.getValAccu());
									iter.remove();
								}
							}
							for (Iterator<Job> iter = waitQueue.getQueue().iterator(); iter.hasNext();) {
								Job qJob = iter.next();
								qJob.decWaitTime();
								if (qJob.getWaitTime() == 0) {
									readyQueue.add(qJob);
									cpu.setRegA(qJob.getValRegA());
									cpu.setRegB(qJob.getValRegB());
									cpu.setRegC(qJob.getValRegC());
									cpu.setRegD(qJob.getValRegD());
									cpu.setAccu(qJob.getValAccu());
									iter.remove();
								}
							}
						} 
						for (Job j : readyQueue) {
							while (!j.getInstructions().isEmpty()) {
								Instruction i = j.getInstructions().get(0);
								//if this is the last instruction, then process it and print the results.
								if (j.getInstructions().size() == 1) {
									i.process(j, i, cpu);
									System.out.println("\nRegister Values of CPU after " + j.getJobNumber());
									System.out.println("---------------------------------");
									System.out.println("Register A: " + cpu.getRegA());
									System.out.println("Register B: " + cpu.getRegB());
									System.out.println("Register C: " + cpu.getRegC());
									System.out.println("Register D: " + cpu.getRegD());
									System.out.println("Accumulator: " + cpu.getAccu());
									readyQueue.remove(readyQueue.getFirst());
									break processing;
								}
								
								i.process(j, i, cpu);
								
								//Check for context switching during processing of a job.
								for (Job qJob : ioQueue.getQueue()) {
									if (qJob.getWaitTime() <= 0) {
										j.storePCB(cpu, cpu.getRegA(), cpu.getRegB(), cpu.getRegC(), cpu.getRegD(), cpu.getAccu());
										readyQueue.addFirst(qJob);
										cpu.setRegA(qJob.getValRegA());
										cpu.setRegB(qJob.getValRegB());
										cpu.setRegC(qJob.getValRegC());
										cpu.setRegD(qJob.getValRegD());
										cpu.setAccu(qJob.getValAccu());
										ioQueue.removeFromQueue(qJob);
										break processing;
									}
									qJob.decWaitTime();
								}
								for (Job qJob : waitQueue.getQueue()) {
									if (qJob.getWaitTime() <= 0) {
										j.storePCB(cpu, cpu.getRegA(), cpu.getRegB(), cpu.getRegC(), cpu.getRegD(), cpu.getAccu());
										readyQueue.addFirst(qJob);
										cpu.setRegA(qJob.getValRegA());
										cpu.setRegB(qJob.getValRegB());
										cpu.setRegC(qJob.getValRegC());
										cpu.setRegD(qJob.getValRegD());
										cpu.setAccu(qJob.getValAccu());
										waitQueue.removeFromQueue(qJob);
										break processing;
									}
									qJob.decWaitTime();
								}
								
								//When the job goes to the io or wait queue, store the register and accumulator values.
								if (i.getOperation().equals("_rd") || i.getOperation().equals("_wr")) {
									j.storePCB(cpu, cpu.getRegA(), cpu.getRegB(), cpu.getRegC(), cpu.getRegD(), cpu.getAccu());
									readyQueue.remove(j);
									ioQueue.addToQueue(j, i.getResult());
									cpu.resetRegValues();
									break processing;
								} else if (i.getOperation().equals("_wt")) {
									j.storePCB(cpu, cpu.getRegA(), cpu.getRegB(), cpu.getRegC(), cpu.getRegD(), cpu.getAccu());
									readyQueue.remove(j);
									waitQueue.addToQueue(j, i.getResult());
									cpu.resetRegValues();
									break processing;
								}
							}
						}						
					}
				}
				ram.clear();
				ramUsage = 0;
			}
			ram.add(job);
			ramUsage += job.getLinesOfInstructions();	
		}
		
		//take care of any jobs left over in the ram
		if (!ram.isEmpty()) {
			for (Job j : ram) {
				readyQueue.add(j);
			}
			Cpu cpu = new Cpu();
			while (!readyQueue.isEmpty() || !ioQueue.getQueue().isEmpty() || !waitQueue.getQueue().isEmpty()) {
				//Process each job in the first spot of the readyQueue
				processing: {
					//this is to ensure that the while loop will not break when jobs are still available.
					if (readyQueue.isEmpty() && (!ioQueue.getQueue().isEmpty() || !waitQueue.getQueue().isEmpty())) {
						for (Iterator<Job> iter = ioQueue.getQueue().iterator(); iter.hasNext();) {
							Job qJob = iter.next();
							qJob.decWaitTime();
							if (qJob.getWaitTime() == 0) {
								readyQueue.add(qJob);
								cpu.setRegA(qJob.getValRegA());
								cpu.setRegB(qJob.getValRegB());
								cpu.setRegC(qJob.getValRegC());
								cpu.setRegD(qJob.getValRegD());
								cpu.setAccu(qJob.getValAccu());
								iter.remove();
							}
						}
						for (Iterator<Job> iter = waitQueue.getQueue().iterator(); iter.hasNext();) {
							Job qJob = iter.next();
							qJob.decWaitTime();
							if (qJob.getWaitTime() == 0) {
								readyQueue.add(qJob);
								cpu.setRegA(qJob.getValRegA());
								cpu.setRegB(qJob.getValRegB());
								cpu.setRegC(qJob.getValRegC());
								cpu.setRegD(qJob.getValRegD());
								cpu.setAccu(qJob.getValAccu());
								iter.remove();
							}
						}
					} 
					for (Job j : readyQueue) {
						while (!j.getInstructions().isEmpty()) {
							Instruction i = j.getInstructions().get(0);
							//if this is the last instruction, then process it and print the results.
							if (j.getInstructions().size() == 1) {
								i.process(j, i, cpu);
								System.out.println("\nRegister Values of CPU after " + j.getJobNumber());
								System.out.println("---------------------------------");
								System.out.println("Register A: " + cpu.getRegA());
								System.out.println("Register B: " + cpu.getRegB());
								System.out.println("Register C: " + cpu.getRegC());
								System.out.println("Register D: " + cpu.getRegD());
								System.out.println("Accumulator: " + cpu.getAccu());
								readyQueue.remove(readyQueue.getFirst());
								break processing;
							}
							
							i.process(j, i, cpu);
							
							//Check for context switching during processing of a job.
							for (Job qJob : ioQueue.getQueue()) {
								if (qJob.getWaitTime() <= 0) {
									j.storePCB(cpu, cpu.getRegA(), cpu.getRegB(), cpu.getRegC(), cpu.getRegD(), cpu.getAccu());
									readyQueue.addFirst(qJob);
									cpu.setRegA(qJob.getValRegA());
									cpu.setRegB(qJob.getValRegB());
									cpu.setRegC(qJob.getValRegC());
									cpu.setRegD(qJob.getValRegD());
									cpu.setAccu(qJob.getValAccu());
									ioQueue.removeFromQueue(qJob);
									break processing;
								}
								qJob.decWaitTime();
							}
							for (Job qJob : waitQueue.getQueue()) {
								if (qJob.getWaitTime() <= 0) {
									j.storePCB(cpu, cpu.getRegA(), cpu.getRegB(), cpu.getRegC(), cpu.getRegD(), cpu.getAccu());
									readyQueue.addFirst(qJob);
									cpu.setRegA(qJob.getValRegA());
									cpu.setRegB(qJob.getValRegB());
									cpu.setRegC(qJob.getValRegC());
									cpu.setRegD(qJob.getValRegD());
									cpu.setAccu(qJob.getValAccu());
									waitQueue.removeFromQueue(qJob);
									break processing;
								}
								qJob.decWaitTime();
							}
							
							//When the job goes to the io or wait queue, store the register and accumulator values.
							if (i.getOperation().equals("_rd") || i.getOperation().equals("_wr")) {
								j.storePCB(cpu, cpu.getRegA(), cpu.getRegB(), cpu.getRegC(), cpu.getRegD(), cpu.getAccu());
								readyQueue.remove(j);
								ioQueue.addToQueue(j, i.getResult());
								cpu.resetRegValues();
								break processing;
							} else if (i.getOperation().equals("_wt")) {
								j.storePCB(cpu, cpu.getRegA(), cpu.getRegB(), cpu.getRegC(), cpu.getRegD(), cpu.getAccu());
								readyQueue.remove(j);
								waitQueue.addToQueue(j, i.getResult());
								cpu.resetRegValues();
								break processing;
							}
						}
					}
				}
			}
		}
	}
}
