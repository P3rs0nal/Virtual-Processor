import org.junit.Assert;
import org.junit.Test;

public class processorTestingPart2 {
	
	/*
	 * NOTE: To test boolean operations individually, a modified version
	 * of the BOP function was created for testing purposes only, which
	 * is identical to the functional BOP, only with an extra parameter
	 * allowing for changes to the input function to simulate each boolean
	 * operation as if passed through
	 */
	
	@Test
	public void BOPeq() throws Exception {
		Bit[] op = new Bit[4];
		for(int i = 0; i < 4; i++) {
			op[i] = new Bit();
		}
	    Processor pro = new Processor();
	    boolean eq = pro.BOP(new Word(0), op);
	    boolean not = pro.BOP(new Word(1), op);
	    Assert.assertEquals(eq, true);
	    Assert.assertEquals(not, false);
	}
	
	@Test
	public void BOPnotEq() throws Exception {
		Bit[] op = new Bit[4];
		for(int i = 0; i < 4; i++) {
			op[i] = new Bit();
		}
		op[3] = new Bit(true);
		Processor pro = new Processor();
		boolean eq = pro.BOP(new Word(0), op);
	    boolean not = pro.BOP(new Word(1), op);
	    Assert.assertEquals(eq, false);
	    Assert.assertEquals(not, true);
	}
	
	@Test
	public void BOPlessThan() throws Exception {
		Bit[] op = new Bit[4];
		for(int i = 0; i < 4; i++) {
			op[i] = new Bit();
		}
		op[2] = new Bit(true);
		Processor pro = new Processor();
		boolean less = pro.BOP(new Word(-1), op);
	    boolean not = pro.BOP(new Word(15), op);
	    Assert.assertEquals(less, true);
	    Assert.assertEquals(not, false);
	}
	
	@Test
	public void BOPgreaterEq() throws Exception {
		Bit[] op = new Bit[4];
		for(int i = 0; i < 4; i++) {
			op[i] = new Bit();
		}
		op[2] = new Bit(true);
		op[3] = new Bit(true);
		Processor pro = new Processor();
		boolean greater = pro.BOP(new Word(15), op);
		boolean greatereq = pro.BOP(new Word(0), op);
	    boolean not = pro.BOP(new Word(-8), op);
	    Assert.assertEquals(greater, true);
	    Assert.assertEquals(greatereq, true);
	    Assert.assertEquals(not, false);
	}
	
	@Test
	public void BOPgreater() throws Exception {
		Bit[] op = new Bit[4];
		for(int i = 0; i < 4; i++) {
			op[i] = new Bit();
		}
		op[1] = new Bit(true);
		Processor pro = new Processor();
		boolean greater = pro.BOP(new Word(123), op);
	    boolean not = pro.BOP(new Word(-2), op);
	    Assert.assertEquals(greater, true);
	    Assert.assertEquals(not, false);
	}
	
	@Test
	public void BOPlessThanEq() throws Exception {
		Bit[] op = new Bit[4];
		for(int i = 0; i < 4; i++) {
			op[i] = new Bit();
		}
		op[1] = new Bit(true);
		op[3] = new Bit(true);
		Processor pro = new Processor();
		boolean less = pro.BOP(new Word(-285), op);
		boolean lessEq = pro.BOP(new Word(0), op);
	    boolean not = pro.BOP(new Word(9), op);
	    Assert.assertEquals(less, true);
	    Assert.assertEquals(lessEq, true);
	    Assert.assertEquals(not, false);
	}
	
	@Test
	public void BranchNoR() throws Exception {
		String noR = "00000000000000010110100000000100"; 
		// IV-000000000000000101101000000 OPCODE-00100
        
		//pc = immv
        String[] mem = {noR};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.registers[0].getSigned(), 0);
        Assert.assertEquals(pro.PC.getSigned(), 2880);
	}
	
	@Test
	public void BranchDest() throws Exception {
		String dest = "00000000000000010110100000000101";
		// IV-000000000000000101 FUNCTION-1010 RD-00000 OPCODE-00101
        
		//pc = immv + rd
        String[] mem = {dest};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.registers[0].getSigned(), 0);
        Assert.assertEquals(pro.PC.getSigned(), 6);
	}
	
	@Test
	public void BranchTwoR() throws Exception {		
		String twoR = "00000000001110010100110000100110";
		// IV-0000000000111 RS1 = 00101 FUNCTION-0011 RD-00001 OPCODE-00110
        
		//pc = pc + immv IF rs1 >= rd
        String[] mem = {twoR};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.PC.getSigned(), 8);
	}
	
	@Test
	public void BranchThreeR() throws Exception {
		String threeR = "00011100000110010101010000100111";
		// IV-00011100 RS1 = 00011 RS2 = 00101 FUNCTION-0101 RD-00001 OPCODE-00111
        
		//pc = pc + immv IF rs1 <= rs2
        String[] mem = {threeR};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.PC.getSigned(), 1);
	}
	
	@Test
	public void CallNoR() throws Exception {
		String noR = "00000000000000010110100000001000"; 
		// IV-000000000000000101101000000 OPCODE-01000
        
		//PC = IM, PC = 2880
        String[] mem = {noR};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.registers[0].getSigned(), 0);
        Assert.assertEquals(pro.PC.getSigned(), 2880);
	}
	
	@Test
	public void CalldestOnly() throws Exception {
		String dest = "00000000000000010110100000001001";
		// IV-000000000000000101 FUNCTION-1010 RD-00000 OPCODE-01001
	        
	    String[] mem = {dest};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(pro.registers[0].getSigned(), 0);
	    Assert.assertEquals(pro.PC.getSigned(), 5);
	}
	
	@Test
	public void CallTwoR() throws Exception {		
		String twoR = "00000000001110010100110000101010";
		// IV-0000000000111 RS1 = 00101 FUNCTION-0011 RD-00001 OPCODE-01010
	        
		//pc = pc + immv IF rs1 >= rd
	    String[] mem = {twoR};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(pro.PC.getSigned(), 7);
	}
	
	@Test
	public void CallThreeR() throws Exception {
		String threeR = "00011100000110010101010000101011";
		// IV-00011100 RS1 = 00011 RS2 = 00101 FUNCTION-0101 RD-00001 OPCODE-01011
	        
		//pc = pc + immv IF rs1 <= rs2
	    String[] mem = {threeR};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(pro.PC.getSigned(), 29);
		}
	
	@Test
	public void PushnoR() throws Exception {
		String noR = "00000000000000010110100000001100"; 
		// IV-000000000000000101101000000 OPCODE-01100
        
        String[] mem = {noR};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        //UNUSED
        Assert.assertEquals(pro.registers[0].getSigned(), 0);
	}
	
	@Test
	public void PushdestOnly() throws Exception {
		String dest = "00000000000000010101110000001101";
		// IV-000000000000000101 FUNCTION-0111 RD-00000 OPCODE-01101
	    
		// mem[--SP] = rd OP imm
		// mem[1022] = 0 x 5 = 0
	    String[] mem = {dest};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(pro.registers[0].getSigned(), 0);
	    Assert.assertEquals(MainMemory.DRAM[1022].toString(), new Word(0).toString());
	}
	
	@Test
	public void PushTwoR() throws Exception {		
		String twoR = "00000000001110010111100000101110";
		// IV-0000000000111 RS1 = 00101 FUNCTION-1110 RD-00001 OPCODE-01110
	        
		// mem[--SP] = rd OP rs1
		// mem[1022] = 1 + 5 = 6
	    String[] mem = {twoR};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(MainMemory.DRAM[1022].toString(), new Word(6).toString());
	}
	
	@Test
	public void PushThreeR() throws Exception {
		String threeR = "00011100111110010111110011101111";
		// IV-00011100 RS1 = 11111 RS2 = 00101 FUNCTION-1111 RD-00111 OPCODE-01111
	        
		//mem[--sp] = rs1 OP rs2
		//mem[1022] = 31 - 5 = 26
	    String[] mem = {threeR};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(MainMemory.DRAM[1022].getSigned(), new Word(26).getSigned());
		}
	
	@Test
	public void LoadnoR() throws Exception {
		String noR = "00000000000000010110100000010000"; 
		// IV-000000000000000101101000000 OPCODE-10000
        
        String[] mem = {noR};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.PC.getSigned(), 5);
	}
	
	@Test
	public void LoaddestOnly() throws Exception {
		String dest = "00000000000000010101110000010001";
		// IV-000000000000000101 FUNCTION-0111 RD-00000 OPCODE-10001
	    
		// rd = mem[rd+imm]
		// rd = mem[0+5] = 0
	    String[] mem = {dest};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(pro.registers[0].getSigned(), 0);
	    Assert.assertEquals(pro.rd.toString(), new Word(0).toString());
	}
	
	@Test
	public void LoadTwoR() throws Exception {		
		String twoR = "00000000001110010111100000101110";
		// IV-0000000000111 RS1 = 00101 FUNCTION-1110 RD-00001 OPCODE-01110
		        
		// rd = mem[rs1+imm]
		// rd = mem[5+7] = 0
	    String[] mem = {twoR};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(pro.rd.toString(), new Word(1).toString());
	}
	
	@Test
	public void LoadThreeR() throws Exception {
		String threeR = "00011100111110010111110011110011";
		// IV-00011100 RS1 = 11111 RS2 = 00101 FUNCTION-1111 RD-00111 OPCODE-10011
		        
		// rd = mem[rs1+rs2]
		// rd = mem[31+5] = 0
		String[] mem = {threeR};
		MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(pro.rd.toString(), new Word(0).toString());
	}
	
	@Test
	public void StorenoR() throws Exception {
		String noR = "00000000000000010110100000010100"; 
		// IV-000000000000000101101000000 OPCODE-10100
        
        String[] mem = {noR};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        // unused
	    Assert.assertEquals(pro.registers[0].getSigned(), 0);
	}
	
	@Test
	public void StoredestOnly() throws Exception {
		String dest = "00000000000000010101110000110101";
		// IV-000000000000000101 FUNCTION-0111 RD-00001 OPCODE-10101
	    
		// mem[rd] = imm
		// mem[1] = 5
	    String[] mem = {dest};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(MainMemory.DRAM[1].toString(), new Word(5).toString());
	}
	
	@Test
	public void StoreTwoR() throws Exception {		
		String twoR = "00000000001110010111100000110110";
		// IV-0000000000111 RS1 = 00101 FUNCTION-1110 RD-00001 OPCODE-10110
		        
		// mem[rd + imm] = rs1
		// mem[1+7] = 5
	    String[] mem = {twoR};
	    MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(MainMemory.DRAM[8].getSigned(), new Word(5).getSigned());
	}
	
	@Test
	public void StoreThreeR() throws Exception {
		String threeR = "00011100111110010111110011110111";
		// IV-00011100 RS1 = 11111 RS2 = 00101 FUNCTION-1111 RD-00111 OPCODE-10111
		        
		// mem[rd + rs1] = rs2
		// mem[7+31] = 5
		String[] mem = {threeR};
		MainMemory.load(mem);
	    Processor pro = new Processor();
	    pro.run();
	    Assert.assertEquals(MainMemory.DRAM[38].getSigned(), new Word(5).getSigned());
	}
	
}
