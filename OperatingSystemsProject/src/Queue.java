import java.util.Vector;

public class Queue {
	private Vector<Job> queue;
	
	public Queue(Vector<Job> q) {
		queue = q;
	}
	
	public Queue(){
		queue = new Vector<>();
	}
	
	public Vector<Job> getQueue() {
		return queue;
	}
	
	public void addToQueue(Job j, int waitTime) {
		queue.add(j);
		j.setWaitTime(waitTime);
	}
	
	public void removeFromQueue(Job j) {
		queue.remove(j);
	}
	
	public void decWaitTime() {
		for (Job j : queue) {
			j.decWaitTime();
		}
	}
}