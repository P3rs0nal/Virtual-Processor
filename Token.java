
public class Token {
	enum Tokens{
		MATH, ADD, SUBTRACT, MULTIPLY, AND, OR, NOT, XOR, COPY, HALT,
		BRANCH, JUMP, CALL, PUSH, LOAD, RETURN, STORE, PEEK, POP,
		INTERRUPT, EQUAL, UNEQUAL, GREATER, LESS, GREATEROREQUAL, 
		LESSOREQUAL, SHIFT, LEFT, RIGHT, SEPARATOR, NUMBER, WORD, R, DESTONLY, THREER, TWOR, REGISTER
	}
	private Tokens tokenType;
	private String value;
	private int lineNumber;
	private int startPosition;
	
	// value constructor
	Token(Tokens type, String value, int lineNum, int pos){
		tokenType = type;
		this.value = value;
		lineNum = lineNumber;
		startPosition = pos;
	}
	// non-value constructor
	Token(Tokens type, int lineNum, int pos){
		tokenType = type;
		lineNum = lineNumber;
		startPosition = pos;
	}
	
	public Tokens getTokenType() {
		return tokenType;
	}
	
	public String getTokenValue() {
		return value;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public int getPosition() {
		return startPosition;
	}
	
	public String toString() {
		if(tokenType == Tokens.SEPARATOR)
			return "SEPARATOR";
		else if(tokenType == Tokens.NUMBER)
			return "NUMBER (" + value + ")";
		else if(tokenType == Tokens.WORD)
			return "WORD (" + value + ")";
		else if(tokenType == Tokens.REGISTER)
			return "REGISTER (" + value + ")";
		else
			return tokenType.toString();
	}
}