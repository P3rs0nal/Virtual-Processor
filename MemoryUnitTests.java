import org.junit.Assert;
import org.junit.Test;

public class MemoryUnitTests {
	String[] t = new String[1024];
	Word expected = new Word(1789568341);
	
	@Test
	public void loadTesting() throws Exception {
		for(int i = 0; i < 10; i++) {
			t[i] = "01101010101010101010010101010101";
		}
		MainMemory.load(t);
		for(int i = 0; i < 10; i++) {
	        Assert.assertEquals(MainMemory.DRAM[i].toString(), expected.toString());
		}
	}
	
	@Test
	public void writeTesting() throws Exception {
		Word address = new Word(29);
		Word value = new Word(824);
		for(int i = 0; i < 10; i++) {
			t[i] = "01101010101010101010010101010101";
		}
		MainMemory.load(t);
		MainMemory.write(address, value);
        Assert.assertEquals(MainMemory.DRAM[29].toString(), value.toString());
	}
	
	@Test
	public void readTesting() throws Exception {
		Word address = new Word(29);
		Word value = new Word(824);
		for(int i = 0; i < 10; i++) {
			t[i] = "01101010101010101010010101010101";
		}
		MainMemory.load(t);
		MainMemory.write(address, value);
        Assert.assertEquals(MainMemory.read(address).toString(), value.toString());
	}
	
	@Test
	public void incrementTesting() throws Exception {
		Word toIncrement = new Word(29);
		Word expected = new Word(30);
		toIncrement.increment();
        Assert.assertEquals(toIncrement.toString(), expected.toString());
	}
	
	@Test
	public void min() throws Exception {
		Word toIncrement = new Word(0);
		Word expected = new Word(1);
		toIncrement.increment();
        Assert.assertEquals(toIncrement.toString(), expected.toString());
	}
	
	@Test
	public void max() throws Exception {
		Word toIncrement = new Word(Integer.MAX_VALUE);
		Word expected = new Word(2147483647+1);
		toIncrement.increment();
        Assert.assertEquals(toIncrement.toString(), expected.toString());
	}
	
	@Test
	public void negative() throws Exception {
		Word toIncrement = new Word(-Integer.MAX_VALUE);
		Word expected = new Word(-2147483646);
		toIncrement.increment();
        Assert.assertEquals(toIncrement.toString(), expected.toString());
	}
	
	@Test
	public void extensiveTest() throws Exception {
		String val = "00000000000000000000000000000000";
		Word two = new Word(2);
		Word five = new Word(5);
		Word end = new Word(1023);
		Word overBounds = new Word(1025);
		for(int i = 0; i < 1024; i++) {
			//randomize the input string
			if(i%2 == 1) {
				char[] chars = val.toCharArray();
				chars[i%32] = '1';
				val = String.valueOf(chars);
			}
			else {
				char[] chars = val.toCharArray();
				chars[i%32] = '0';
				val = String.valueOf(chars);
			}
			if(i%50==1) {
				val = "00000000000000000000000000000000";
			}
			t[i] = val;
		}
		t[1023] = "00000000011011101101101001000000";
		MainMemory.load(t);
        Assert.assertEquals(MainMemory.read(two).toString(), new Word().toString());
        Assert.assertEquals(MainMemory.read(five).toString(), new Word(335544320).toString());
        Assert.assertEquals(MainMemory.read(end).toString(), new Word(7264832).toString());
        Assert.assertEquals(MainMemory.read(overBounds).toString(), new Word().toString());
	}
}
