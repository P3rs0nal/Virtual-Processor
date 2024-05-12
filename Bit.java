
public class Bit {
	
	private boolean bitValue;
	
	public Bit() {
		bitValue = false;
	}
	
	public Bit(boolean value) {
		bitValue = value;
	}
	
	void set(boolean value) {
		bitValue = value;
	}
	
	void toggle() {
		if(bitValue)
			bitValue = false;
		else
			bitValue = true;
	}
	
	void set() {
		bitValue = true;
	}
	
	void clear() {
		bitValue = false;
	}
	
	boolean getValue() {
		return bitValue;
	}
	
	Bit and(Bit other) {
		if(other.bitValue) 
			if(bitValue)
				return new Bit(true);
		return new Bit();
	}
	
	Bit or(Bit other) {
		if(bitValue)
			return new Bit(true);
		if(other.bitValue)
			return new Bit(true);
		return new Bit();
	}
	
	Bit xor(Bit other) {
		if(other.bitValue) {
			if(bitValue)
				return new Bit();
			return new Bit(true);
		}
		if(bitValue) {
			if(other.bitValue)
				return new Bit();
			return new Bit(true);
		}
		return new Bit(false);	
	}
	
	Bit not() {
		if(bitValue)
			return new Bit(false);
		return new Bit(true);
	}
	
	public String toString() {
		return bitValue + "";
	}
}
