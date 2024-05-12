import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class Word {
	
	private ArrayList<Bit> bits	= new ArrayList<Bit>(32);
	
	public Word(ArrayList<Bit> bits) {
		this.bits = bits;
	}
	
	public Word() {
		for(int i = 0; i < 32; i++) {
			bits.add(i, new Bit(false));
		}
	}
	
	public Word(int value) {
		for(int i = 0; i < 32; i++) {
			bits.add(i, new Bit(false));
		}
		this.set(value);
	}
	
    Bit getBit(int i) {
    	return bits.get(i);
    }
    
    void setBit(int i, Bit value) {
    	bits.set(i, value);
    }
    
    Word and(Word other) {
    	int i = 0;
    	Word ret = new Word();
    	while(i<other.bits.size()) {
    		ret.setBit(i,other.bits.get(i).and(bits.get(i)));
    		i++;
    	}
    	return ret;
    }
    
    Word or(Word other) {
    	int i = 0;
    	Word ret = new Word();
    	while(i<other.bits.size()) {
    		ret.setBit(i,other.bits.get(i).or(bits.get(i)));
    		i++;
    	}
    	return ret;
    }
    
    Word xor(Word other){
    	int i = 0;
    	Word ret = new Word();
    	while(i<other.bits.size()) {
    		ret.setBit(i,other.bits.get(i).xor(bits.get(i)));
    		i++;
    	}
    	return ret;
    }
    
    Word not() {
    	int i = 0;
    	Word ret = new Word();
    	for(Bit b : bits) {
    		ret.setBit(i, b.not());
    		i++;
    	}
    	return ret;
    }
    
	Word rightShift(int amount) {
		// empty shift
    	if(amount > 31)
    		return new Word();
    	// pointless shift
    	if(amount == 0)
    		return new Word(bits);
    	// inverse shift
    	if(amount < 0)
    		return leftShift(amount*-1);
    	Word shifted = new Word();
    	int index = 0;
    	for(Bit b: bits) {
    		if(index + amount > 31)
    			break;
    		shifted.setBit(index + amount, b);
    		index++;
    	}
    	return shifted;
    }
    
    Word leftShift(int amount){
    	// empty shift
    	if(amount > 31)
    		return new Word();
    	// pointless shift
    	if(amount == 0)
    		return new Word(bits);
    	// inverse shift
    	if(amount < 0)
    		return rightShift(amount*-1);
    	Word shifted = new Word();
    	int index = 0;
    	for(Bit b: bits) {
    		if(!(index - amount < 0))
    			shifted.setBit(index - amount, b);
    		index++;
    	}
    	return shifted;
    }
    public String toString(){
    	int i = 0;
    	String ret = "";
    	for(Bit b: bits) {
    		if(b.getValue())
    			ret+= "1";
    		else
    			ret+= "0";
    		i++;
    		if(i%4 == 0)
    			ret+= " ";
    	}
    	//remove excess space at the end
    	ret = ret.substring(0,39);
    	return ret;
    }
    
    // for parser, returns a toString with no spaces
    public String toStringCombined(){
    	String ret = "";
    	for(Bit b: bits) {
    		if(b.getValue())
    			ret+= "1";
    		else
    			ret+= "0";
    	}
    	return ret;
    }
    
    int getSigned(){
    	int ret = 0;
    	Word sign = new Word();	
    	sign.copy(this);
    	for(Bit b: sign.bits) {
    		b.toggle();
    	}
		for(int i = 31; i >= 0; i--){
	    	if(sign.bits.get(i).getValue()) {
	    		sign.bits.set(i, new Bit());
	    	}
	    	else {
	    		sign.bits.set(i, new Bit(true));
	    		break;
	    	}
		}
    	ret = (int)sign.getUnsigned();
    	return ret*-1;
    }
    
    long getUnsigned() {
    	long ret = 0;
    	int index = bits.size()-1;
    	for(Bit b: bits) {
    		if(b.getValue()) { 
    			ret+=Math.pow(2, index);
    		}
    		index--;
    	}
    	return ret;
    }
    
    void copy(Word other) {
    	for(int i = 0; i < 32; i++) {
    		bits.set(i, new Bit(other.getBit(i).getValue()));
    	}
    }
    
    void set(int value) {
    	// clear array to prevent values from being unwritten
    	clearBits();
    	int index = 31;
    	boolean sign = false;
    	if(value < 0) {
    		sign = true;
    		value *= -1;
    	}
    	while(value > 0) {
	    		if(value%2 == 1) {
	    			bits.set(index--, new Bit(true));
	    		}
	    		else
	    			bits.set(index--, new Bit());
	    		value /= 2;
	    	}
    	if(sign) {
    		for(Bit b: bits)
    			b.toggle();
    		if(bits.get(31).getValue()) {
    			bits.set(31, new Bit());
    			for(int i = 30; i > 0; i--){
		    		if(bits.get(i).getValue()) {
		    			bits.set(i, new Bit());
		    		}
		    		else {
		    			bits.set(i, new Bit(true));
		    			break;
		    		}
    			}
    		}
    		else {
    			bits.set(31, new Bit(true));
    		}
    	}
    }
    
    public void clearBits() {
    	for(Bit b: this.bits) {
    		b.clear();
    	}
    }
    
    public ArrayList<Bit> getBits(){
    	return bits;
    }
    
    public void increment() {
		Bit Cout = new Bit();
		Word one = new Word(1);
		for(int i = 31; i>=0; i--) {
			Bit res = this.getBit(i).xor(one.getBit(i)).xor(Cout);
			Cout = this.getBit(i).and(one.getBit(i)).or((this.getBit(i).xor(one.getBit(i))).and(Cout));
			this.setBit(i, res);
		}
    }
    
    public void decrement() {
		Bit Cout = new Bit();
		Word one = new Word(-1);
		for(int i = 31; i>=0; i--) {
			Bit res = this.getBit(i).xor(one.getBit(i)).xor(Cout);
			Cout = this.getBit(i).and(one.getBit(i)).or((this.getBit(i).xor(one.getBit(i))).and(Cout));
			this.setBit(i, res);
		}
    }
}