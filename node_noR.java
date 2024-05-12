
public class node_noR extends Node{
	
	Word imm = new Word();
	Word opcode = new Word();
	Word rVal = new Word(0);
	
	public node_noR() {
		
	}
	
	public void setImm(int val) {
		imm.set(val);
		imm = imm.leftShift(5);
	}
	
	public void setOP(int val) {
		opcode.set(val);
		opcode = opcode.leftShift(2);
	}
	
	public void construct() {
		this.r = this.r.or(imm);
		this.r = this.r.or(opcode);
		this.r = this.r.or(rVal);
	}

}
