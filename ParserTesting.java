import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class ParserTesting {
	
	@Test
	public void CopyAndMultiply() throws Exception {
        //store 100 => r1
		//store 5 => r2
		//store 5 * 100 => r4 (500)
		
		String inp = "COPY 100 R1; COPY 5 R2; MATH MULTIPLY R1 R2 R4";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].toString(), new Word(100).toString());
        Assert.assertEquals(pro.registers[2].getSigned(), 5);
        Assert.assertEquals(pro.registers[4].getSigned(), 500);
        //MainMemory.clear();
	}
	
	@Test
	public void CopyAndAdd() throws Exception {
        //store 14 => r7
		//store 5 => r3
		//store 14 + 5 => r24 (19)
		
		String inp = "COPY 14 R7; COPY 5 R3; MATH ADD R7 R3 R24";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[7].getSigned(), 14);
        Assert.assertEquals(pro.registers[3].getSigned(), 5);
        Assert.assertEquals(pro.registers[24].getSigned(), 19);
        //MainMemory.clear();
	}
	
	@Test
	public void load() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; COPY 7 R3; LOAD R1 R2 R9";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[3].getSigned(), 7);
        Assert.assertEquals(pro.registers[9].getSigned(), 0);
        //MainMemory.clear();
	}
	
	@Test
	public void store() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; STORE R1 R2 R3";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[3].getSigned(), 0);
        //MainMemory.clear();
	}
	
	@Test
	public void branch() throws Exception {
		
		String inp = "COPY 20 R1; COPY 20 R2; BRANCH EQUAL R1 R2 R3 5";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 20);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[3].getSigned(), 0);
        //MainMemory.clear();
	}
	
	@Test
	public void call() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; CALL LESS R1 R2 R3";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[3].getSigned(), 0);
        //MainMemory.clear();
	}
	
	@Test
	public void push() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; PUSH R1 R2 R3";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[3].getSigned(), 0);
        //MainMemory.clear();
	}
	
	@Test
	public void pop() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; POP R1 R2 R3";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[3].getSigned(), 0);
        //MainMemory.clear();
	}
	@Test
	public void halt() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; HALT; POP R1 R2 R3";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[3], null);
        //MainMemory.clear();
	}
	
	@Test
	public void Return() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; RETURN R1 R2";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 0);
        Assert.assertEquals(pro.registers[30], null);
        //MainMemory.clear();
	}
	
	@Test
	public void shiftRight() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; MATH SHIFT RIGHT R1 R2";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[30], null);
        //MainMemory.clear();
	}
	
	@Test
	public void shiftLeft() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; MATH SHIFT LEFT R1 R2";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[30], null);
        //MainMemory.clear();
	}
	
	@Test
	public void compoundTest() throws Exception {
		
		String inp = "COPY 10 R1; COPY 20 R2; MATH ADD R1 R2 R3";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[1].getSigned(), 10);
        Assert.assertEquals(pro.registers[2].getSigned(), 20);
        Assert.assertEquals(pro.registers[3].getSigned(), 30);
        //MainMemory.clear();
	}
	
	@Test
	public void compoundBranchTest() throws Exception {
		
		String inp = "COPY 3 R1; COPY 6 R6; BRANCH GREATER R1 R6 2; COPY 8 R23; COPY 10 R13; MATH ADD R1 R6 R15";
		Lexer lex = new Lexer(inp);
		lex.Lex();
		
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		
		MainMemory.load(toLoad);
        Processor pro = new Processor();
        pro.run();
        
        Assert.assertEquals(pro.registers[15].getSigned(), 3);
        Assert.assertEquals(pro.registers[1].getSigned(), 3);
        Assert.assertEquals(pro.registers[6].getSigned(), 0);
        Assert.assertEquals(pro.registers[23].getSigned(), 8);
        Assert.assertEquals(pro.registers[13].getSigned(), 10);
        Assert.assertEquals(pro.registers[6].getSigned(), 0);
        //MainMemory.clear();
	}
}
