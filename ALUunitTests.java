import org.junit.Assert;
import org.junit.Test;

public class ALUunitTests{
	
    @Test
    public void ALUand() throws Exception {
    	Word seven = new Word(7); // 0000 0111
        Word thirtySix = new Word(36); // 0010 0100
        Bit[] operation = {new Bit(true), new Bit(), new Bit(), new Bit(false)}; // and
        // 0000 0111 AND 0010 0100 => 0000 0100 = 4
        Word expected = new Word(4);
        ALU test = new ALU(seven, thirtySix);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUor() throws Exception {
    	Word seven = new Word(7); // 0000 0111
        Word thirtySix = new Word(36); // 0010 0100
        Bit[] operation = {new Bit(true), new Bit(), new Bit(), new Bit(true)}; // or
        // 0000 0111 OR 0010 0100 => 0010 0111 = 39
        Word expected = new Word(39);
        ALU test = new ALU(seven, thirtySix);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUxor() throws Exception {
    	Word seven = new Word(7); // 0000 0111
        Word thirtySix = new Word(36); // 0010 0100
        Bit[] operation = {new Bit(true), new Bit(), new Bit(true), new Bit()}; // xor
        // 0000 0111 XOR 0010 0100 => 0010 0011 = 35
        Word expected = new Word(35);
        ALU test = new ALU(seven, thirtySix);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUnot() throws Exception {
    	Word seven = new Word(7); // 0000 0111
        Word thirtySix = new Word(36); // 0010 0100
        Bit[] operation = {new Bit(true), new Bit(), new Bit(true), new Bit(true)}; // not op1 (seven)
        Word expected = new Word(7);
        expected = expected.not();
        ALU test = new ALU(seven, thirtySix);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUleftShift() throws Exception {
    	Word seven = new Word(7); // 0111
        Word two = new Word(2); // 0010
        Bit[] operation = {new Bit(true), new Bit(true), new Bit(), new Bit()}; // leftShift op1 (by op2)
        // 0000 0111 LS 2 => 0001 1100 = 28
        Word expected = new Word(28);
        ALU test = new ALU(seven, two);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUrightShift() throws Exception {
    	Word seven = new Word(7); // 0111
        Word two = new Word(2); // 0010
        Bit[] operation = {new Bit(true), new Bit(true), new Bit(), new Bit(true)}; // rightShift op1 (by op2)
        // 0000 0111 RS 2 => 0000 0001 = 1
        Word expected = new Word(1);
        ALU test = new ALU(seven, two);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUbasicAdd() throws Exception {
    	Word seven = new Word(7); // 0111
        Word two = new Word(2); // 0010
        Bit[] operation = {new Bit(true), new Bit(true), new Bit(true), new Bit()}; // add
        // 0111 ADD 0010 => 1001 = 9
        Word expected = new Word(9);
        ALU test = new ALU(seven, two);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUbasicSubtract() throws Exception {
    	Word seven = new Word(7); // 0111
        Word two = new Word(2); // 0010
        Bit[] operation = {new Bit(true), new Bit(true), new Bit(true), new Bit(true)}; // subtract
        // 0111 SUB 0010 => 0111 ADD (NOT) (0010) => 0111 ADD 1101 => 0100 + 0001 (carry) => 0101 = 5
        Word expected = new Word(5);
        ALU test = new ALU(seven, two);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUbasicMultiply() throws Exception {
    	Word seven = new Word(7); // 0111
        Word two = new Word(2); // 0010
        Bit[] operation = {new Bit(), new Bit(true), new Bit(true), new Bit(true)}; // multiply
        /*
          	 0111
         x	 0010
         --------
             0000
            01110
           000000
         +0000000
         --------
          0001110
         =>  1110 = 14
         */
        Word expected = new Word(14);
        ALU test = new ALU(seven, two);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUbasicNegMultiply() throws Exception {
    	Word negSeven = new Word(-7); 
        Word fifteen = new Word(15);
        Bit[] operation = {new Bit(), new Bit(true), new Bit(true), new Bit(true)}; //multiply
        Word expected = new Word(-105);
        
        ALU test = new ALU(negSeven, fifteen);
        test.doOperation(operation);
        Assert.assertEquals(test.result.toString(), expected.toString());
    }
    
    @Test
    public void ALUMaxMultiplyandAdd() throws Exception {
    	Word maxOne = new Word(2147483647); 
        Word maxTwo = new Word(2147483647);
        Bit[] multiply = {new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        Bit[] add = {new Bit(true), new Bit(true), new Bit(true), new Bit()};
        Word multAns = new Word(1);
        Word addAns = new Word(-2);
        
        ALU addALU = new ALU(maxOne, maxTwo);
        ALU multALU = new ALU(maxOne, maxTwo);
        
        addALU.doOperation(add);
        multALU.doOperation(multiply);
        
        Assert.assertEquals(addALU.result.toString(), addAns.toString());
        Assert.assertEquals(multALU.result.toString(), multAns.toString());

    }
    
    @Test
    public void ALUcompositionTest() throws Exception {
    	Word fifteen = new Word(15); // 1111
        Word four = new Word(4); // 0100
        Word negFive = new Word(-5);// 11..1101
        Bit[] multiply = {new Bit(), new Bit(true), new Bit(true), new Bit(true)};
        Bit[] add = {new Bit(true), new Bit(true), new Bit(true), new Bit()};
        /*
         15*4 + (-5) = 60 + (-5) = 55 => 0011 0111
          1111
         x0100
         -----
           0011 1100 => 60
         + 11...1101  => -5
         -----------
         0..0011 0111 => 55
         */
        Word multAns = new Word(60);
        ALU mult = new ALU(fifteen, four);
        mult.doOperation(multiply);
        Assert.assertEquals(mult.result.toString(), multAns.toString());
       
        Word additionAns = new Word(55);
        Word sixty = mult.result;
        ALU addition = new ALU(sixty, negFive);
        addition.doOperation(add);
        Assert.assertEquals(addition.result.toString(), additionAns.toString());
    }
    
    @Test
    public void ALUextensiveTest() throws Exception {
    	Word a = new Word(893459); //0000 0000 0000 1101 1010 0010 0001 0011 
        Word b = new Word(-893460); //1111 1111 1111 1110 1010 0010 1000 1100
        
        Bit[] sub = {new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
        Bit[] add = {new Bit(true), new Bit(true), new Bit(true), new Bit()};
       
        // sub = 1786919
        // add = -1
        
        Word subAns = new Word(1786919);
        Word addAns = new Word(-1);
        
        ALU ALUsub = new ALU(a, b);
        ALU ALUadd = new ALU(a, b);
        
        ALUsub.doOperation(sub);
        ALUadd.doOperation(add);
        
        Assert.assertEquals(ALUsub.result.toString(), subAns.toString());
        Assert.assertEquals(ALUadd.result.toString(), addAns.toString());
    }
    
}