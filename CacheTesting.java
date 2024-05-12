import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class CacheTesting {
	@Test
	public void ArraySums() throws Exception {
		Processor.currentClockCycle = 0;
		InstructionCache c = new InstructionCache();
		L2Cache l2 = new L2Cache();
		Processor pro = new Processor();
		for(int i = 0; i < 20; i++) {
			pro.registers[i] = new Word(200+i);
		}
		String inp = "";
		int r = 0;
		for(int i = 200; i < 220; i++) {
			inp+="STORE " + "R" + r++ + " " +(i*2 - i+3) + ";";
		}
		String adding = "";
		r = 200;
		for(int i = 0; i < 20; i++) {
			adding +="LOAD R0 R2 " + r + ";";
			adding +="MATH ADD R2 R1;";
			r++;
		}
		inp += adding;
		Lexer lex = new Lexer(inp);
		lex.Lex();
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		MainMemory.load(toLoad);
        pro.run();
    }
	
	@Test
	public void ArraySumsBackwards() throws Exception {
        Processor.currentClockCycle = 0;
		InstructionCache c = new InstructionCache();
		L2Cache l2 = new L2Cache();
		Processor pro = new Processor();
		for(int i = 20; i > 0; i--) {
			pro.registers[i] = new Word(200+i);
		}
		String inp = "";
		int r = 0;
		for(int i = 200; i < 220; i++) {
			inp+="STORE " + "R" + r++ + " " +(i*2 - i+3) + ";";
		}
		String adding = "";
		r = 200;
		for(int i = 0; i < 20; i++) {
			adding +="LOAD R0 R2 " + r + ";";
			adding +="MATH ADD R2 R1;";
			r++;
		}
		inp += adding;
		Lexer lex = new Lexer(inp);
		lex.Lex();
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		MainMemory.load(toLoad);
        pro.run();
    }
	
	@Test
	public void LinkedList() throws Exception {
		Processor.currentClockCycle = 0;
		//r1 -> r3
		//r2 -> random
		InstructionCache c = new InstructionCache();
		L2Cache l2 = new L2Cache();
		Processor pro = new Processor();
		for(int i = 0; i < 20; i++) {
			pro.registers[1] = new Word(200+i);
			pro.registers[2] = new Word((200 + (int) Math.random()*601));
			pro.registers[3] = new Word(201+i);
		}
		String inp = "";
		int r = 0;
		for(int i = 200; i < 220; i++) {
			inp+="STORE " + "R" + r++ + " " +(i*2 - i+3) + ";";
		}
		String adding = "";
		r = 200;
		for(int i = 20; i > 0; i--) {
			adding +="LOAD R0 R2 " + r + ";";
			adding +="MATH ADD R2 R1;";
			r++;
		}
		inp += adding;
		Lexer lex = new Lexer(inp);
		lex.Lex();
		Parser parser = new Parser(lex.getTokens());
		ArrayList<String> memoryCommands = parser.parse();
		String[] toLoad = new String[memoryCommands.size()];
		for(int i = 0; i < memoryCommands.size(); i++) {
			toLoad[i] = memoryCommands.get(i);
		}
		MainMemory.load(toLoad);
        pro.run();
    }
}
