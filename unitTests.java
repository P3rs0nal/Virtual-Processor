import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class unitTests{
	
    @Test
    public void bitsTest() throws Exception {
        Bit one = new Bit(true);
        Bit zero = new Bit();
        
        one.set(false);
        Assert.assertEquals(one.getValue(), false);
        
        one.set(true);
        Assert.assertEquals(one.getValue(), true);
        
        one.toggle();
        Assert.assertEquals(one.getValue(), false);
        
        one.toggle();
        Assert.assertEquals(one.getValue(), true);
        
        zero.set();
        Assert.assertEquals(zero.getValue(), true);
        
        zero.clear();
        Assert.assertEquals(zero.getValue(), false);
        
        Assert.assertEquals(one.and(zero).getValue(), false);
        Assert.assertEquals(one.or(zero).getValue(), true);
        Assert.assertEquals(one.xor(zero).getValue(), true);
        Assert.assertEquals(one.not().getValue(), false);
        Assert.assertEquals(zero.not().getValue(), true);
        Assert.assertEquals(one.toString(), "Value of bit: true\n");
        Assert.assertEquals(zero.toString(), "Value of bit: false\n");
        
        one.set(false);
        Assert.assertEquals(one.getValue(), false);
        Assert.assertEquals(one.xor(zero).getValue(), false);
        one.set(true);
        zero.set(true);
        Assert.assertEquals(one.xor(zero).getValue(), false);
        Assert.assertEquals(one.and(zero).getValue(), true);
    }
    
    @Test
    public void basicWordsTest() throws Exception {
    	Word zero = new Word(); 
        Word seven = new Word(); //0111
        Word six = new Word(); //0110
        Word thirtySix = new Word(); //0010 0100
        
        zero.set(0);
        seven.set(7);
        six.set(6);
        thirtySix.set(36);
        
        Assert.assertEquals(zero.toString(), "0000 0000 0000 0000 0000 0000 0000 0000");
        
        // 7 = 0111, f,t,t,t
        Assert.assertEquals(seven.getBit(31).getValue(), true);
        Assert.assertEquals(seven.getBit(30).getValue(), true);
        Assert.assertEquals(seven.getBit(29).getValue(), true);
        Assert.assertEquals(seven.getBit(28).getValue(), false);
        
        //6 = 0110, f,t,t,f
        Assert.assertEquals(six.getBit(31).getValue(), false);
        Assert.assertEquals(six.getBit(30).getValue(), true);
        Assert.assertEquals(six.getBit(29).getValue(), true);
        Assert.assertEquals(six.getBit(28).getValue(), false);
        
        //36 = 0010 0100, f,f,t,f f,t,f,f
        Assert.assertEquals(thirtySix.getBit(31).getValue(), false);
        Assert.assertEquals(thirtySix.getBit(30).getValue(), false);
        Assert.assertEquals(thirtySix.getBit(29).getValue(), true);
        Assert.assertEquals(thirtySix.getBit(28).getValue(), false);
        
        Assert.assertEquals(thirtySix.getBit(27).getValue(), false);
        Assert.assertEquals(thirtySix.getBit(26).getValue(), true);
        Assert.assertEquals(thirtySix.getBit(25).getValue(), false);
        Assert.assertEquals(thirtySix.getBit(24).getValue(), false);
    }
    
    @Test
    public void negativeWordsTest() throws Exception {
    	Word zero = new Word(); 
        Word seven = new Word(); //0111
        Word six = new Word(); //0110
        Word thirtySix = new Word(); //0010 0100
        
        zero.set(-0);
        seven.set(-7);
        six.set(-6);
        thirtySix.set(-36);
        
        // 0 cannot be "negative" 
        Assert.assertEquals(zero.toString(), "0000 0000 0000 0000 0000 0000 0000 0000");
        
        // -7 = 1001, t,f,f,t and index 0 = 1
        Assert.assertEquals(seven.getBit(31).getValue(), true);
        Assert.assertEquals(seven.getBit(30).getValue(), false);
        Assert.assertEquals(seven.getBit(29).getValue(), false);
        Assert.assertEquals(seven.getBit(28).getValue(), true);
        Assert.assertEquals(seven.getBit(0).getValue(), true);
        
        // -6 = 1010, f,t,f,t and index 0 = 1
        Assert.assertEquals(six.getBit(31).getValue(), false);
        Assert.assertEquals(six.getBit(30).getValue(), true);
        Assert.assertEquals(six.getBit(29).getValue(), false);
        Assert.assertEquals(six.getBit(28).getValue(), true);
        Assert.assertEquals(six.getBit(0).getValue(), true);
        
        // -36 = 1101 1100, t,t,f,t t,t,f,f and index 0 = 1
        Assert.assertEquals(thirtySix.getBit(31).getValue(), false);
        Assert.assertEquals(thirtySix.getBit(30).getValue(), false);
        Assert.assertEquals(thirtySix.getBit(29).getValue(), true);
        Assert.assertEquals(thirtySix.getBit(28).getValue(), true);
        
        Assert.assertEquals(thirtySix.getBit(27).getValue(), true);
        Assert.assertEquals(thirtySix.getBit(26).getValue(), false);
        Assert.assertEquals(thirtySix.getBit(25).getValue(), true);
        Assert.assertEquals(thirtySix.getBit(24).getValue(), true);
        Assert.assertEquals(thirtySix.getBit(0).getValue(), true);
    }
    
    @Test
    public void operationsWordsTest() throws Exception {
    	Word zero = new Word(); 
        Word seven = new Word(); //0111
        Word six = new Word(); //0110
        Word thirtySix = new Word(); //0010 0100
        
        zero.set(0);
        seven.set(7);
        six.set(6);
        thirtySix.set(36);
        
        Assert.assertEquals(zero.not().toString(), "1111 1111 1111 1111 1111 1111 1111 1111");
        
        // 7 = 0111, !7 = 1000
        Assert.assertEquals(seven.not().getBit(31).getValue(), false);
        Assert.assertEquals(seven.not().getBit(30).getValue(), false);
        Assert.assertEquals(seven.not().getBit(29).getValue(), false);
        Assert.assertEquals(seven.not().getBit(28).getValue(), true);
        
        // 6 = 0110, !6 = 1001
        Assert.assertEquals(six.not().getBit(31).getValue(), true);
        Assert.assertEquals(six.not().getBit(30).getValue(), false);
        Assert.assertEquals(six.not().getBit(29).getValue(), false);
        Assert.assertEquals(six.not().getBit(28).getValue(), true);
        
        // 36 = 0010 0100, !36 = 1101 1011
        Assert.assertEquals(thirtySix.not().getBit(31).getValue(), true);
        Assert.assertEquals(thirtySix.not().getBit(30).getValue(), true);
        Assert.assertEquals(thirtySix.not().getBit(29).getValue(), false);
        Assert.assertEquals(thirtySix.not().getBit(28).getValue(), true);
        
        Assert.assertEquals(thirtySix.not().getBit(27).getValue(), true);
        Assert.assertEquals(thirtySix.not().getBit(26).getValue(), false);
        Assert.assertEquals(thirtySix.not().getBit(25).getValue(), true);
        Assert.assertEquals(thirtySix.not().getBit(24).getValue(), true);
        
        // 7 xor 6 => 0111 xor 0110 = 0001
        Assert.assertEquals(seven.xor(six).getBit(31).getValue(), true);
        Assert.assertEquals(six.xor(seven).getBit(30).getValue(), false);
        Assert.assertEquals(seven.xor(six).getBit(29).getValue(), false);
        Assert.assertEquals(seven.xor(six).getBit(28).getValue(), false);
        
        // 7 xor 36 => 0000 0111 xor 0010 0100 = 0010 0011
        Assert.assertEquals(seven.xor(thirtySix).getBit(31).getValue(), true);
        Assert.assertEquals(seven.xor(thirtySix).getBit(30).getValue(), true);
        Assert.assertEquals(seven.xor(thirtySix).getBit(29).getValue(), false);
        Assert.assertEquals(seven.xor(thirtySix).getBit(28).getValue(), false);
        
        Assert.assertEquals(seven.xor(thirtySix).getBit(27).getValue(), false);
        Assert.assertEquals(seven.xor(thirtySix).getBit(26).getValue(), true);
        Assert.assertEquals(seven.xor(thirtySix).getBit(25).getValue(), false);
        Assert.assertEquals(seven.xor(thirtySix).getBit(24).getValue(), false);

        // 7 or 6 => 0111 or 0110 = 0111
        Assert.assertEquals(seven.or(six).getBit(31).getValue(), true);
        Assert.assertEquals(seven.or(six).getBit(30).getValue(), true);
        Assert.assertEquals(seven.or(six).getBit(29).getValue(), true);
        Assert.assertEquals(seven.or(six).getBit(28).getValue(), false);

        // 7 and 6 => 0111 and 0110 = 0110
        Assert.assertEquals(seven.and(six).getBit(31).getValue(), false);
        Assert.assertEquals(seven.and(six).getBit(30).getValue(), true);
        Assert.assertEquals(seven.and(six).getBit(29).getValue(), true);
        Assert.assertEquals(seven.and(six).getBit(28).getValue(), false);
    }
    
    @Test
    public void shiftingWordsTest() throws Exception {
    	Word zero = new Word(); 
        Word seven = new Word(); //0111
        Word six = new Word(); //0110
        Word thirtySix = new Word(); //0010 0100
        
        zero.set(0);
        seven.set(7);
        six.set(6);
        thirtySix.set(36);
        
        Assert.assertEquals(zero.rightShift(20).toString(), "0000 0000 0000 0000 0000 0000 0000 0000");
        Assert.assertEquals(zero.leftShift(20).toString(), "0000 0000 0000 0000 0000 0000 0000 0000");
        
        Assert.assertEquals(seven.rightShift(2).getBit(31).getValue(), true);
        Assert.assertEquals(seven.rightShift(2).getBit(30).getValue(), false);
        Assert.assertEquals(seven.rightShift(2).getBit(29).getValue(), false);
        Assert.assertEquals(seven.rightShift(2).getBit(28).getValue(), false);
              
        Assert.assertEquals(seven.leftShift(2).getBit(31).getValue(), false);
        Assert.assertEquals(seven.leftShift(2).getBit(30).getValue(), false);
        Assert.assertEquals(seven.leftShift(2).getBit(29).getValue(), true);
        Assert.assertEquals(seven.leftShift(2).getBit(28).getValue(), true);
        
        Assert.assertEquals(seven.leftShift(2).getBit(27).getValue(), true);
        Assert.assertEquals(seven.leftShift(2).getBit(26).getValue(), false);
        Assert.assertEquals(seven.leftShift(2).getBit(25).getValue(), false);
        Assert.assertEquals(seven.leftShift(2).getBit(24).getValue(), false);
        }
    
   	@Test
    public void signedAndUnsignedWordsTest() throws Exception {
        Word seven = new Word();
        Word six = new Word();
        Word thirtySix = new Word();
        
        seven.set(7);
        six.set(-6);
        thirtySix.set(36);
        
        Assert.assertEquals(seven.getUnsigned(), 7);
        Assert.assertEquals(seven.getSigned(), 7);
        
        Assert.assertNotEquals(six.getUnsigned(), -6);
        Assert.assertEquals(six.getSigned(), -6);
        
        Assert.assertEquals(thirtySix.getUnsigned(), 36);
        Assert.assertEquals(thirtySix.getSigned(), 36);
        
        }
   	
	@Test
    public void s() throws Exception {
        Word seven = new Word();     
//        seven.set(7);
//        
        Assert.assertEquals(seven.getUnsigned(), 0);
        Assert.assertEquals(seven.getSigned(), 0);
        
        }
   	
   	@Test
    public void copyWordsTest() throws Exception {
        Word seven = new Word();
        Word thirtySix = new Word();
        Word fifty = new Word(50);
        
        seven.set(7);
        thirtySix.set(36);
        
        seven.copy(thirtySix);
        Assert.assertEquals(seven.getUnsigned(), 36);
        
        seven.copy(fifty);
        Assert.assertEquals(seven.getUnsigned(), 50);
        
        thirtySix.set(-20);
        fifty.copy(thirtySix);
        Assert.assertEquals(fifty.getSigned(), -20);
        
        thirtySix.copy(fifty);
        Assert.assertEquals(thirtySix.getSigned(), -20);
        
        seven.copy(fifty);   
        Assert.assertEquals(seven.getSigned(), -20);
        }
   	
	@Test
    public void compoundWordTests() throws Exception {
        Word first = new Word(21873); // 0101 0101 0111 0001
        Word second = new Word(-123948); // 1... 1110 0001 1011 1101 0100
        Word and = new Word();
        Word or = new Word();
        Word xor = new Word();
        
        // 0000 0101 0101 0111 0001
        // 1110 0001 1011 1101 0100
        // and
        // 0000 0001 0001 0101 0000 = 4432
        and.copy(first.and(second));
        Assert.assertEquals(and.getUnsigned(), 4432);
        
        // 0000 0101 0101 0111 0001
        // 1110 0001 1011 1101 0100
        // or
        // 1110 0101 1111 1111 0101 = -106507
        or.copy(first.or(second));
        Assert.assertEquals(or.getSigned(), -106507);
        
        // 0000 0101 0101 0111 0001
        // 1110 0001 1011 1101 0100
        // xor
        // 1110 0100 1110 1010 0101 = -110941
        
        xor.copy(first.xor(second));
        Assert.assertEquals(xor.getSigned(), -110939);
        Assert.assertEquals(xor.rightShift(40).getUnsigned(), 0);
        Assert.assertEquals(xor.leftShift(40).getUnsigned(), 0);
        Assert.assertEquals(or.rightShift(16).getUnsigned(), 65534);
        Assert.assertEquals(or.rightShift(-16).getUnsigned(), or.leftShift(16).getUnsigned());
        
        }
}