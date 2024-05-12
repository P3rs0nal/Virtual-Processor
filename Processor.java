
public class Processor {
	Word PC = new Word(0);
	Word SP = new Word(1023);
	Word currentInstruction = new Word();
	Word fiveBitMask = new Word();
	Word functionMask = new Word();
	Word rs1 = new Word();
	Word rs2 = new Word();
	Word function = new Word();
	Word rd = new Word();
	Word opCode = new Word();
	Word immediateVal = new Word();
	Bit halted = new Bit();
	ALU alu = new ALU();
	Word[] registers = new Word[32];
	public static int currentClockCycle = 0;
	public Processor() {
		registers[0] = new Word();
	}
	
	public void run() throws Exception {
		while(!halted.getValue()) {
			//create and reset the masks
			fiveBitMask.clearBits();
			functionMask.clearBits();
			for(int i = 27; i <=31; i++)
				fiveBitMask.setBit(i, new Bit(true));
			for(int i = 18; i <= 21; i++)
				functionMask.setBit(i, new Bit(true));
			fetch();
			decode();
			execute();
			store();
		}
		System.out.println("Halted at: " + currentClockCycle + " clock cycles");
	}
	
	public void fetch() {
		try {
			currentInstruction = InstructionCache.read(PC);
			currentClockCycle+=300;
		}
		catch(Exception E) {
			currentInstruction = null;
		}
		if(currentInstruction == null) {
			currentInstruction = new Word();
		}
		PC.increment();
	}
	
	public void decode() {
		immediateVal = new Word();
		rs1 = new Word();
		rs2 = new Word();
		rd = new Word();
		function = new Word();
		opCode = new Word();
		try {
			Bit last = currentInstruction.getBit(31);
			Bit secondToLast = currentInstruction.getBit(30);
			if(last.and(secondToLast).getValue()) { //3R 11
				opCode = fiveBitMask.and(currentInstruction);
				fiveBitMask = fiveBitMask.leftShift(5);
				rd = fiveBitMask.and(currentInstruction).rightShift(5);
				function = functionMask.and(currentInstruction).rightShift(10);
				fiveBitMask = fiveBitMask.leftShift(9);
				rs2 = fiveBitMask.and(currentInstruction).rightShift(14);
				fiveBitMask = fiveBitMask.leftShift(5);
				rs1 = fiveBitMask.and(currentInstruction).rightShift(19);
				for(int i = 0; i <= 8; i++) {
					immediateVal.setBit(i, currentInstruction.getBit(i));
				}
				immediateVal = immediateVal.rightShift(24);
				rs1 = registers[rs1.getSigned()];
				rs2 = registers[rs2.getSigned()];
			}
			else if(secondToLast.getValue()) { //2R 10
				opCode = fiveBitMask.and(currentInstruction);
				fiveBitMask = fiveBitMask.leftShift(5);
				rd = fiveBitMask.and(currentInstruction).rightShift(5);
				function = functionMask.and(currentInstruction).rightShift(10);
				fiveBitMask = fiveBitMask.leftShift(9);
				rs1 = fiveBitMask.and(currentInstruction).rightShift(14);
				for(int i = 0; i <= 13; i++) {
					immediateVal.setBit(i, currentInstruction.getBit(i));
				}
				immediateVal = immediateVal.rightShift(19);
				rs1 = registers[rs1.getSigned()];
			}
			else if(last.getValue()) { //1R 01
				opCode = fiveBitMask.and(currentInstruction);
				fiveBitMask = fiveBitMask.leftShift(5);
				rd = fiveBitMask.and(currentInstruction).rightShift(5);
				function = functionMask.and(currentInstruction).rightShift(10);
				for(int i = 0; i <= 18; i++) {
					immediateVal.setBit(i, currentInstruction.getBit(i));
				}
				immediateVal = immediateVal.rightShift(14);
			}
			else { //0R 00
				opCode = fiveBitMask.and(currentInstruction);
				for(int i = 0; i <= 27; i++) {
					immediateVal.setBit(i, currentInstruction.getBit(i));
				}
				immediateVal = immediateVal.rightShift(5);
			}
		}
		catch(Exception e) {
		}
	}
	
	public void execute() throws Exception {
		alu.result = new Word();
		try {
			// case opCode: 00000
			if(!(opCode.getBit(27).getValue() || opCode.getBit(28).getValue() || opCode.getBit(29).getValue() || opCode.getBit(30).getValue() || opCode.getBit(31).getValue()))
				halted.toggle();
			// 000 math
			else if(!(opCode.getBit(29).getValue() || opCode.getBit(28).getValue() || opCode.getBit(27).getValue())) {
				if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) {
					alu.result = immediateVal;
				}
				else {
					alu.op1 = rs1;
					alu.op2 = rs2;
					Bit[] opFunction = new Bit[4];
					for(int i = 0; i < 4; i++) {
						if(function.getBit(28+i).getValue())
							opFunction[i] = new Bit(true);
						else
							opFunction[i] = new Bit();
					}
					alu.doOperation(opFunction);
				}
			}
			//001 branch
			else if(opCode.getBit(29).getValue() && !(opCode.getBit(28).getValue() || opCode.getBit(27).getValue())) {
				branch();
			}
			//010 call
			else if(opCode.getBit(28).getValue() && !(opCode.getBit(29).getValue() || opCode.getBit(27).getValue())) {
				call();
			}
			//011 push
			else if(opCode.getBit(29).getValue() && opCode.getBit(28).getValue() || !opCode.getBit(27).getValue()) {
				push();
			}
			//100 load
			else if(opCode.getBit(27).getValue() && !(opCode.getBit(28).getValue() || opCode.getBit(29).getValue())) {
				load();
			}
			//101 store
			else if(opCode.getBit(29).getValue() && !(opCode.getBit(28).getValue()) && opCode.getBit(27).getValue()) {
				Store();
			}
			// 110 pop/interrupt
			else if(!opCode.getBit(29).getValue() && opCode.getBit(28).getValue() && opCode.getBit(27).getValue()) {
				pop();
				//DO NOT IMPL YET
			}
		}
		catch(Exception e) {
		}
	}
	
	public void store() {
		if(!halted.getValue()) {
			int dest = 0;
			if(rd.getBit(27).getValue())
				dest += 16;
			if(rd.getBit(28).getValue())
				dest += 8;
			if(rd.getBit(29).getValue())
				dest += 4;
			if(rd.getBit(30).getValue())
				dest += 2;
			if(rd.getBit(31).getValue())
				dest += 1;
			if(dest != 0)
				registers[dest] = alu.result;
		}
	}
	
	public boolean BOP(Word result) {
		Word zero = new Word(0);
		// 0000 eq
		if (!function.getBit(28).getValue() && !function.getBit(29).getValue() && !function.getBit(30).getValue() && !function.getBit(31).getValue()) {
			for(int bit = 0; bit < 32; bit++) {
				if(result.getBit(bit).getValue() != zero.getBit(bit).getValue())
					return false;
			}
			return true;
		}
		// 0001 not eq
		else if (!function.getBit(28).getValue() && !function.getBit(29).getValue() && !function.getBit(30).getValue() && function.getBit(31).getValue()) {
			for(int bit = 0; bit < 32; bit++) {
				if(result.getBit(bit).getValue() != zero.getBit(bit).getValue())
					return true;
			}
			return false;
		}
		// 0010 <
		else if (!function.getBit(28).getValue() && !function.getBit(29).getValue() && function.getBit(30).getValue() && !function.getBit(31).getValue()) {
			if(result.getBit(0).getValue())
				return true;
		}
		// 0011 >=
		else if (!function.getBit(28).getValue() && !function.getBit(29).getValue() && function.getBit(30).getValue() && function.getBit(31).getValue()) {
			if(!result.getBit(0).getValue())
				return true;
		}
		// 0100 >
		else if (!function.getBit(28).getValue() && function.getBit(29).getValue() && !function.getBit(30).getValue() && !function.getBit(31).getValue()) {
			if(!result.getBit(0).getValue())
				return true;
		}
		// 0101 <=
		else if (!function.getBit(28).getValue() && function.getBit(29).getValue() && !function.getBit(30).getValue() && function.getBit(31).getValue()) {
			if(result.getBit(0).getValue())
					return true;
			for(int bit = 0; bit < 32; bit++) {
				if(result.getBit(bit).getValue() != zero.getBit(bit).getValue())
					return false;
			}
			return true;
		}
		return false;
	}
	
	public void branch() throws Exception {
		if(currentInstruction.getBit(31).getValue() && !currentInstruction.getBit(30).getValue()) { //1R 01
			PC.set(PC.getSigned()+immediateVal.getSigned());
		}
		else if(!currentInstruction.getBit(31).getValue() && !currentInstruction.getBit(30).getValue()){ //0R 00
			PC.set(immediateVal.getSigned());
		}
		else { // 2R or 3R
			if(currentInstruction.getBit(30).getValue())//2R 10
				rs2 = rd;
			alu.op1 = rs1;
			alu.op2 = rs2;
			Bit[] subtract = new Bit[4];
			for(int i = 0; i < 4; i++) {
				subtract[i] = new Bit(true);
			}
			alu.doOperation(subtract);
			boolean res = BOP(alu.result);
			if(currentInstruction.getBit(30).and(currentInstruction.getBit(31)).getValue()) {
				//3R 11
				if(res)
					PC.set(PC.getSigned()+immediateVal.getSigned());
			}
			else if(currentInstruction.getBit(30).getValue()) { //2R 10
				if(res)
					PC.set(PC.getSigned()+immediateVal.getSigned());
			}
		}
	}
	
	public void call() throws Exception {
		if(currentInstruction.getBit(31).getValue()) { //1R 01
			InstructionCache.write(SP, PC);
			PC.set(rd.getSigned()+immediateVal.getSigned());
		}
		else if(!currentInstruction.getBit(31).and(currentInstruction.getBit(30)).getValue()){ //0R 00
			InstructionCache.write(SP, PC);
			PC.set(immediateVal.getSigned());
		}
		else { // 2R or 3R
			if(currentInstruction.getBit(30).getValue())//2R 10
				rs2 = rd;
			alu.op1 = rs1;
			alu.op2 = rs2;
			Bit[] subtract = new Bit[4];
			for(int i = 0; i < 4; i++) {
				subtract[i] = new Bit(true);
			}
			alu.doOperation(subtract);
			boolean res = BOP(alu.result);
			if(currentInstruction.getBit(30).and(currentInstruction.getBit(31)).getValue()) { //3R 11
				if(res) {
					InstructionCache.write(SP, PC);
					PC.set(rd.getSigned()+immediateVal.getSigned());
				}
			}
			else if(currentInstruction.getBit(30).getValue()) { //2R 10
				if(res) {
					InstructionCache.write(SP, PC);
					PC.set(PC.getSigned()+immediateVal.getSigned());
				}
			}
		}
	}
	
	public void push() throws Exception {
		if(currentInstruction.getBit(30).getValue() && !currentInstruction.getBit(31).getValue())//2R 10
			rs2 = rd;
		if(currentInstruction.getBit(31).getValue() && !currentInstruction.getBit(30).getValue()){//1R 01
			rs2 = rd;
			rs1 = immediateVal;
		}
		alu.op1 = rs1;
		alu.op2 = rs2;
		Bit[] opFunction = new Bit[4];
		for(int i = 0; i < 4; i++) {
			if(function.getBit(28+i).getValue())
				opFunction[i] = new Bit(true);
			else
				opFunction[i] = new Bit();
		}
		alu.doOperation(opFunction);
		if(currentInstruction.getBit(30).and(currentInstruction.getBit(31)).getValue()) { //3R 11
			SP.decrement();
			InstructionCache.write(SP,alu.result);
			currentClockCycle+=300;
		}
		else if(currentInstruction.getBit(30).getValue()) { //2R 10
			SP.decrement();
			InstructionCache.write(SP,alu.result);
			currentClockCycle+=300;
		}
		else if(currentInstruction.getBit(31).getValue()) { //1R 01
			SP.decrement();
			InstructionCache.write(SP,alu.result);
			currentClockCycle+=300;
		}
	}
	
	public void load() throws Exception {
		if(currentInstruction.getBit(30).and(currentInstruction.getBit(31)).getValue()) { //3R 11
			rd = InstructionCache.read(new Word(rs1.getSigned() + rs2.getSigned()));
			currentClockCycle+=300;
		}
		else if(currentInstruction.getBit(30).getValue()) { //2R 10
			rd = InstructionCache.read(new Word(rs1.getSigned() + immediateVal.getSigned()));
			currentClockCycle+=300;
		}
		else if(currentInstruction.getBit(31).getValue()) { //1R 01
			rd = InstructionCache.read(new Word(rd.getSigned() + immediateVal.getSigned()));
			currentClockCycle+=300;
		}
		else if(!currentInstruction.getBit(30).and(currentInstruction.getBit(31)).getValue()) {
			PC = InstructionCache.read(SP);
			SP.increment();
			currentClockCycle+=300;
		}
		if (rd == null)
			rd = new Word(0);
		if(PC == null)
			PC = new Word(0);
	}
	
	public void Store() {
		if(currentInstruction.getBit(30).and(currentInstruction.getBit(31)).getValue()) { //3R 11
			InstructionCache.write(new Word(rd.getSigned() + rs1.getSigned()), rs2);
			currentClockCycle+=300;
		}
		else if(currentInstruction.getBit(30).getValue()) { //2R 10
			InstructionCache.write(new Word(rd.getSigned() + immediateVal.getSigned()), rs1);
			currentClockCycle+=300;
		}
		else if(currentInstruction.getBit(31).getValue()) { //1R 01
			InstructionCache.write(rd, immediateVal);
			currentClockCycle+=300;
		}
	}
	
	public void pop() {
		if(currentInstruction.getBit(30).and(currentInstruction.getBit(31)).getValue()) { //3R 11
			InstructionCache.write(new Word(SP.getSigned() - (rs1.getSigned() + rs2.getSigned())), rd);
			currentClockCycle+=300;
		}
		else if(currentInstruction.getBit(30).getValue()) { //2R 10
			InstructionCache.write(new Word(SP.getSigned() - (rs1.getSigned() + immediateVal.getSigned())), rs1);
			currentClockCycle+=300;
		}
		else if(currentInstruction.getBit(31).getValue()) { //1R 01
			rd = InstructionCache.read(SP);
			SP.increment();
			currentClockCycle+=300;
		}
		else if(!currentInstruction.getBit(30).and(currentInstruction.getBit(31)).getValue()) {
			//INTERRUPT, DO NOT IMPLEMENT YET
		}
	}
	
	public int toInt(Word input) {
		int res = 0;
		if(input.getBit(27).getValue())
			res += 16;
		if(input.getBit(28).getValue())
			res += 8;
		if(input.getBit(29).getValue())
			res += 4;
		if(input.getBit(30).getValue())
			res += 2;
		if(input.getBit(31).getValue())
			res += 1;
		return res;
	}
	
	/*
	NOTE:
	This function is for testing purposes only.
	It takes a bit array and modifies the current
	function instructions to simulate each operation.
	This function is not used anywhere besides testing.
	*/
	public boolean BOP(Word result, Bit[] op) {
		Word zero = new Word(0);
		for(int i = 28; i < 32; i++) {
			function.setBit(i,op[i-28]);
		}
		// 0000 eq
		if (!function.getBit(28).getValue() && !function.getBit(29).getValue() && !function.getBit(30).getValue() && !function.getBit(31).getValue()) {
			for(int bit = 0; bit < 32; bit++) {
				if(result.getBit(bit).getValue() != zero.getBit(bit).getValue())
					return false;
			}
			return true;
		}
		// 0001 not eq
		else if (!function.getBit(28).getValue() && !function.getBit(29).getValue() && !function.getBit(30).getValue() && function.getBit(31).getValue()) {
			for(int bit = 0; bit < 32; bit++) {
				if(result.getBit(bit).getValue() != zero.getBit(bit).getValue())
					return true;
			}
			return false;
		}
		// 0010 <
		else if (!function.getBit(28).getValue() && !function.getBit(29).getValue() && function.getBit(30).getValue() && !function.getBit(31).getValue()) {
			if(result.getBit(0).getValue())
				return true;
		}
		// 0011 >=
		else if (!function.getBit(28).getValue() && !function.getBit(29).getValue() && function.getBit(30).getValue() && function.getBit(31).getValue()) {
			if(!result.getBit(0).getValue())
				return true;
		}
		// 0100 >
		else if (!function.getBit(28).getValue() && function.getBit(29).getValue() && !function.getBit(30).getValue() && !function.getBit(31).getValue()) {
			if(!result.getBit(0).getValue())
				return true;
		}
		// 0101 <=
		else if (!function.getBit(28).getValue() && function.getBit(29).getValue() && !function.getBit(30).getValue() && function.getBit(31).getValue()) {
			if(result.getBit(0).getValue())
					return true;
			for(int bit = 0; bit < 32; bit++) {
				if(result.getBit(bit).getValue() != zero.getBit(bit).getValue())
					return false;
			}
			return true;
		}
		return false;
	}

	public Word[] getRegisters() {
		return registers;
	}
}