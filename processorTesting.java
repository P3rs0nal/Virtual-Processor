import org.junit.Assert;
import org.junit.Test;

public class processorTesting {
	@Test
	public void destOnlyTest() throws Exception {
		String word = "00000000000000010110100000000001"; 
		// IV-000000000000000101 FUNCTION-1010 RD-00000 OPCODE-00001
        // store 0 xor 0 (default ops) in 00000(reg0) 
        String[] mem = {word};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.registers[0].getSigned(), 0);
	}
	
	@Test
	public void threeRegTest() throws Exception {
        String word = "10001110000010010111100000100011"; 
        // IV-10001110 RS2-00001 RS1-00101 FUNCTION-1110 RD-00001 OPCODE-00011
        // store 00101(5) + 00001(1) in 00001(reg1)
        String[] mem = {word};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.registers[1].getSigned(), 6);
	}
	
	@Test
	public void twoRegTest() throws Exception {
        String word = "10001110000010011110010001000010"; 
        // IV-1000111000001 RS1-00111 FUNCTION-1001 RD-00001 OPCODE-00010
        // store 00111(7) OR emptyWord(op1 default) in 00010(reg2)
        String[] mem = {word};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        Assert.assertEquals(pro.registers[2].getSigned(), 7);
	}
	
	@Test
	public void noRegTest() throws Exception {
        String word = "10001110000010011110010001000000"; 
        // IV-100011100000100001 OPCODE-00000
        // store IV and halt
        String[] mem = {word};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        for(int i = 1; i <32; i++)
        	Assert.assertEquals(pro.registers[i], null);
	}
	
	@Test
	public void allFourTypeTests() throws Exception {
        String destOnly = "00000000000000010110100000000001"; 
		// IV-000000000000000101 FUNCTION-1010 RD-00000 OPCODE-00001
        // store 0 xor 0 (default ops) in 00000(reg0) 
        
        String threeReg = "10001110000010010111100000100011"; 
        // IV-10001110 RS2-00001 RS1-00101 FUNCTION-1110 RD-00001 OPCODE-00011
        // store 00101(5) + 00001(1) in 00001(reg1)
        
        String twoReg = "10001110000010011110010001000010"; 
        // IV-1000111000001 RS1-00111 FUNCTION-1001 RD-00001 OPCODE-00010
        // store 00111(7) OR emptyWord(op1 default) in 00010(reg2)
        
        String noReg = "10001110000010011110010001000000"; 
        // IV-100011100000100001 OPCODE-00000
        // store IV and halt
        
        String[] mem = {destOnly, threeReg, twoReg, noReg};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();

        for(int i = 0; i <32; i++) {
        	if(i == 0)
        		Assert.assertEquals(pro.registers[i].getSigned(), 0);
        	else if(i == 1)
        		Assert.assertEquals(pro.registers[i].getSigned(), 6);
        	else if(i == 2)
        		Assert.assertEquals(pro.registers[i].getSigned(), 7);
        	else
        		Assert.assertEquals(pro.registers[i], null);
        	}
	}
	
	@Test
	public void moreCompositeTesting() throws Exception {
        String destOnly = "00000000000000010110100000000001"; 
		// IV-000000000000000101 FUNCTION-1010 RD-11110 OPCODE-00001
        // store 0 xor 0 (default ops) in 11110 (reg30)
        
        String threeReg = "10001110101110111001110010000011"; 
        // IV-10001110 RS2-10111 RS1-01110 FUNCTION-0111 RD-00100 OPCODE-00011
        // store 10111(23) * 01110(14) in 00100(reg4) (result: 322)
        
        String twoReg = "10001110000010011110110111100010"; 
        // IV-1000111000001 RS1-00111 FUNCTION-1011 RD-01111 OPCODE-00010
        // store 00111(7) NOT (ignore other OP) in 01111(reg15)
        
        String destOnlyAfter = "00000000000000010110000100100001"; 
		// IV-000000000000000101 FUNCTION-1000 RD-01001 OPCODE-00001
        // store 00111(7) and 01110(14) (leftover operands from previous memory) in 01001 (reg9) (result: 6)
        
        String noReg = "10001110000010011110010001000000"; 
        // IV-100011100000100001 OPCODE-00000
        // store IV and halt
        
        String[] mem = {destOnly, threeReg, twoReg, destOnlyAfter, noReg};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        for(int i = 0; i <32; i++) {
        	if(i == 0)
        		Assert.assertEquals(pro.registers[i].getSigned(), 0);
        	else if(i == 4)
        		Assert.assertEquals(pro.registers[i].getSigned(), 322);
        	else if(i ==9)
        		Assert.assertEquals(pro.registers[i].getSigned(), 6);
        	else if(i == 15)
        		Assert.assertEquals(pro.registers[i].getSigned(), new Word(7).not().getSigned());
        	else
        		Assert.assertEquals(pro.registers[i], null);
        	}
	}
	
	@Test
	public void emptyTest() throws Exception {
        String word = "00000000000000000000000000000000"; 
        // IV-000000000000000000 OPCODE-00000
        // store IV and halt
        String[] mem = {word};
        MainMemory.load(mem);
        Processor pro = new Processor();
        pro.run();
        for(int i = 1; i <32; i++)
        	Assert.assertEquals(pro.registers[i], null);
	}
}
