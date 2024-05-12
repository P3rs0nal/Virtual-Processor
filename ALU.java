import java.util.ArrayList;

public class ALU {
	Word op1 = new Word();
	Word op2 = new Word();
	Word result;
	
	public ALU() {
		
	}
	
	public ALU(Word one, Word two) {
		op1 = one;
		op2 = two;
	}
	
	public void doOperation(Bit[] operation) throws Exception {
		Bit one = operation[0];
		Bit two = operation[1];
		Bit three = operation[2];
		Bit four = operation[3];
		
		// and
		if((one.and(two.not())).and(three.not().and(four.not())).getValue()){
			result = op1.and(op2);
			Processor.currentClockCycle+=2;
		}
		// or
		else if((one.and(two.not())).and(three.not().and(four)).getValue()) {
			result = op1.or(op2);
			Processor.currentClockCycle+=2;
		}
		// xor
		else if((one.and(two.not())).and(three.and(four.not())).getValue()) {
			result = op1.xor(op2);
			Processor.currentClockCycle+=2;
		}
		// not
		else if((one.and(two.not())).and(three.and(four)).getValue()) {
			result = op1.not();
			Processor.currentClockCycle+=2;
		}
		// LS
		else if((one.and(two)).and(three.not().and(four.not())).getValue()) {
			result = op1.leftShift(op2.getSigned());
			Processor.currentClockCycle+=2;
		}
		// RS
		else if((one.and(two)).and(three.not().and(four)).getValue()) {
			result = op1.rightShift(op2.getSigned());
			Processor.currentClockCycle+=2;
		}
		//add
		else if(((one.and(two)).and(three.and(four.not())).getValue())) {
			result = addTwo(op1, op2);
			Processor.currentClockCycle+=2;
		}
		//subtract
		else if((one.and(two)).and(three.and(four)).getValue()) {
			Processor.currentClockCycle+=2;
			op2 = op2.not();
    		if(op2.getBits().get(31).getValue()) {
    			op2.getBits().set(31, new Bit());
    			for(int i = 30; i > 0; i--){
		    		if(op2.getBits().get(i).getValue()) {
		    			op2.getBits().set(i, new Bit());
		    		}
		    		else {
		    			op2.getBits().set(i, new Bit(true));
		    			break;
		    		}
    			}
    		}
    		else {
    			op2.getBits().set(31, new Bit(true));
    		}
			result = addTwo(op1, op2);
		}
		// multiply
		else if((two.and(one.not())).and(three.and(four)).getValue()){
			Processor.currentClockCycle+=10;
			ArrayList<Word> words = new ArrayList<>();
			int shiftAmt =  0;
			for(int lowerBits = 31; lowerBits>= 0; lowerBits--) {
				Word temp = new Word();
				// copy op1 values
				if(op2.getBit(lowerBits).getValue()) {
					for(int upperBits = 31; upperBits>= 0; upperBits--) {
						temp.setBit(upperBits, op1.getBit(upperBits));
					}
					temp = temp.leftShift(shiftAmt);
				}
				words.add(temp);
				shiftAmt++;
			}
			int round = 8;
			while(round > 0) {
				words.add(addFour(words.get(0),words.get(1),words.get(2),words.get(3)));
				words.removeAll(words.subList(0, 4));
				round--;
				
			}
			round = 2;
			while(round > 0) {
				words.add(addFour(words.get(0),words.get(1),words.get(2),words.get(3)));
				words.removeAll(words.subList(0, 4));
				round--;
			}
			result = addTwo(words.get(0), words.get(1));
		}
		else {
			throw new Exception("invalid code: " + operation[0] + operation[1] + operation[2] + operation[3]);
		}
	}
	
	public Word addTwo(Word one, Word two) {
		Word ret = new Word();
		Bit Cout = new Bit();
		for(int i = 31; i>=0; i--) {
			Bit res = one.getBit(i).xor(two.getBit(i)).xor(Cout);
			Cout = one.getBit(i).and(two.getBit(i)).or((one.getBit(i).xor(two.getBit(i))).and(Cout));
			ret.setBit(i, res);
		}
		return ret;
	}
	
	public Word addFour(Word one, Word two, Word three, Word four) {
		Word ret = new Word();
		int carries = 0;
		int numOfTrueBits = 0;
		for(int i = 31; i>=0; i--) {
			if(one.getBit(i).getValue())
				numOfTrueBits++;
			if(two.getBit(i).getValue())
				numOfTrueBits++;
			if(three.getBit(i).getValue())
				numOfTrueBits++;
			if(four.getBit(i).getValue())
				numOfTrueBits++;
			numOfTrueBits+=carries;
			if(numOfTrueBits%2 == 1) {
				ret.setBit(i, new Bit(true));
				numOfTrueBits--;
			}
			// carry the prev carries
			carries = 0;
			while(numOfTrueBits > 0) {
				carries++;
				numOfTrueBits-=2;
			}		
		}
		return ret;
	}
}
