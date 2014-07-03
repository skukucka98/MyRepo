
public class Instruction {
	private int instructionNumber;
	private String operation;
	private String var1;
	private String var2;
	private int result;
	private int cycle = 0;
	
	public Instruction(int i, String o, String v1, String v2, int r) {
		instructionNumber = i;
		operation = o;
		var1 = v1;
		var2 = v2;
		result = r;
	}
	
	public int getInstructionNumber() {
		return instructionNumber;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public String getVar1() {
		return var1;
	}
	
	public String getVar2() {
		return var2;
	}
	
	public int getResult() {
		return result;
	}
	
	public int getCycle() {
		return cycle;
	}
	
	public int identifyVar(String var, Cpu c) {
		switch(var) {
			case "A": return c.getRegA();
			case "B": return c.getRegB();
			case "C": return c.getRegC();
			case "D": return c.getRegD();
			default: return 0;
		}
	}
	
	public void process(Instruction i, Cpu c) {
		switch (operation) {
			case "add":
				c.addToAccu(i.identifyVar(i.getVar1(), c) + i.identifyVar(i.getVar2(), c));
				cycle++;
				break;
			case "sub":
				c.addToAccu(i.identifyVar(i.getVar1(), c) - i.identifyVar(i.getVar2(), c));
				cycle++;
				break;
			case "mul":
				c.addToAccu(i.identifyVar(i.getVar1(), c) * i.identifyVar(i.getVar2(), c));
				cycle++;
				break;
			case "div":
				c.addToAccu(i.identifyVar(i.getVar1(), c) / i.identifyVar(i.getVar2(), c));
				cycle++;
				break;
			case "_rd":
				cycle++;
				break;
			case "_wr":
				cycle++;
				break;
			case "_wt":
				cycle++;
				break;
			case "sto":
				cycle++;
				c.setAccu(getResult());
			case "rcl":
				if (i.getVar1() == "A") {
					c.setRegA(c.getAccu());
				} else if (i.getVar1() == "B") {
					c.setRegB(c.getAccu());
				} else if (i.getVar1() == "C") {
					c.setRegC(c.getAccu());
				} else if (i.getVar1() == "D") {
					c.setRegD(c.getAccu());
				}
		}
	}
	
	public String toString() {
		return getInstructionNumber() + " " + getOperation() + " " + getVar1() + 
				" " + getVar2() + " " + getResult(); 
	}
}
