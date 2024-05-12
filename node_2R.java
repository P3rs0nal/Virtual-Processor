
public class node_2R extends Node{
	
	Word function = new Word();
	Word imm = new Word();
	Word rs1 = new Word();
	Word rd = new Word(); 
	Word opcode = new Word();
	Word rval = new Word(2);
	
	public node_2R() {
		
	}
	
	public void setFunction(int val) {
		function.set(val);
		function = function.leftShift(10);
	}
	
	public void setImm(int val) {
		imm.set(val);
		imm = imm.leftShift(19);
	}
	
	public void setRS1(int val) {
		rs1.set(val);
		rs1 = rs1.leftShift(14);
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
		this.r = this.r.or(rs1);
		this.r = this.r.or(rd);
		this.r = this.r.or(opcode);
		this.r = this.r.or(rval);
	}

}
