
public class node_Dest extends Node{
	
	Word function = new Word();
	Word imm = new Word();
	Word rd = new Word(); 
	Word opcode = new Word();
	Word rval = new Word(1);
	
	public node_Dest() {
		
	}
	
	public void setFunction(int val) {
		function.set(val);
		function = function.leftShift(10);
	}
	
	public void setImm(int val) {
		imm.set(val);
		imm = imm.leftShift(14);
	}
	
	public void setRD(int val) {
		rd.set(val);
		rd = rd.leftShift(5);
	}
	
	public void setOP(int val) {
		opcode.set(val);
		opcode = opcode.leftShift(2);
	}
	
	public void construct() {
		this.r = this.r.or(function);
		this.r = this.r.or(imm);
		this.r = this.r.or(rd);
		this.r = this.r.or(opcode);
		this.r = this.r.or(rval);
	}

}
