import java.util.LinkedList;
import java.util.HashMap;

public class Lexer {
	private int line;
	private int charPos;
	private StringHandler strhdlr;
	private LinkedList<Token> tokenCollection = new LinkedList<>();
	private HashMap<String, Token.Tokens> keyWord = new HashMap<>();
	Lexer(String input){
		strhdlr = new StringHandler(input);
		line = 0;
		charPos = 0;
		populator();
		}
	
	public void populator() {
		keyWord.put("math", Token.Tokens.MATH);
		keyWord.put("add", Token.Tokens.ADD);
		keyWord.put("subtract", Token.Tokens.SUBTRACT);
		keyWord.put("multiply", Token.Tokens.MULTIPLY);
		keyWord.put("and", Token.Tokens.AND);
		keyWord.put("or", Token.Tokens.OR);
		keyWord.put("not", Token.Tokens.NOT);
		keyWord.put("xor", Token.Tokens.XOR);
		keyWord.put("copy", Token.Tokens.COPY);
		keyWord.put("halt", Token.Tokens.HALT);
		keyWord.put("branch", Token.Tokens.BRANCH);
		keyWord.put("jump", Token.Tokens.JUMP);
		keyWord.put("call", Token.Tokens.CALL);
		keyWord.put("push", Token.Tokens.PUSH);
		keyWord.put("load", Token.Tokens.LOAD);
		keyWord.put("return", Token.Tokens.RETURN);
		keyWord.put("store", Token.Tokens.STORE);
		keyWord.put("peek", Token.Tokens.PEEK);
		keyWord.put("pop", Token.Tokens.POP);
		keyWord.put("interrupt", Token.Tokens.INTERRUPT);
		keyWord.put("equal", Token.Tokens.EQUAL);
		keyWord.put("unequal", Token.Tokens.UNEQUAL);
		keyWord.put("greater", Token.Tokens.GREATER);
		keyWord.put("less", Token.Tokens.LESS);
		keyWord.put("greaterOrEqual", Token.Tokens.GREATEROREQUAL);
		keyWord.put("lessOrEqual", Token.Tokens.LESSOREQUAL);
		keyWord.put("shift", Token.Tokens.SHIFT);
		keyWord.put("left", Token.Tokens.LEFT);
		keyWord.put("right", Token.Tokens.RIGHT);
		keyWord.put("r", Token.Tokens.R);
	}
	public void Lex() throws Exception {
		while(!strhdlr.isDone()) {
			char current = strhdlr.peek(charPos);
			if(current == '#') {
				// loop to the end of the line
				while(strhdlr.peek(charPos) != '\n' || strhdlr.isDone()) {
					charPos++;
					strhdlr.swallow(1);
				}
				line++;
			}
			else if(current == ' ' || current == '\t') {
				charPos++;
				strhdlr.swallow(1);
			}
			else if(current == '\n' || current == ';') {
				tokenCollection.add(new Token(Token.Tokens.SEPARATOR, line, charPos));
				line++;
				charPos++;
				strhdlr.swallow(1);
			}
			else if(current == '\r') {
				charPos++;
				strhdlr.swallow(1);		
			}//avoid parsing right and return as register
			else if((current == 'r' || current == 'R') && !(strhdlr.peek(charPos+1) == 'e' || strhdlr.peek(charPos+1) == 'E' ) && !(strhdlr.peek(charPos+1) == 'i' || strhdlr.peek(charPos+1) == 'I' )) {
				tokenCollection.add(processRegister());
			}
			else if(Character.isLetter(current)){
				tokenCollection.add(processWord());
			}
			else if(Character.isDigit(current) || current == '.') {
				tokenCollection.add(processNumber());
			}
			else {
				throw new Exception("Unexpected character");
			}
		}
		tokenCollection.add(new Token(Token.Tokens.SEPARATOR, line, charPos));
	}
	
	public Token processWord() {
		String word = "";
		while((Character.isLetter(strhdlr.peek(charPos)) || Character.isDigit(strhdlr.peek((charPos))) || strhdlr.peek(charPos) == '_')) {
			word += strhdlr.getChar();
			charPos++;
		}
		String wordC = word.toUpperCase();
		String wordL = word.toLowerCase();
		if(keyWord.containsKey(wordL) || keyWord.containsKey(wordC)) {
			Token.Tokens ret = keyWord.get(wordL);
			if (ret == null)
				ret = keyWord.get(wordC);
			return new Token(ret, line, charPos);
			}
		return new Token(Token.Tokens.WORD, word, line, charPos);
	}
	
	public Token processNumber() throws Exception {
		String number = "";
		boolean decimal = false;
		// boolean flag to keep track of multiple decimals
		while(((Character.isDigit(strhdlr.peek(charPos)) || (strhdlr.peek(charPos) == '.' && !decimal)))) {
			if(strhdlr.peek(charPos) == '.')
				decimal = !decimal;
			number += strhdlr.getChar();
			charPos++;
		}
		// will stop lexer from breaking an invalid number into two valid numbers with the 2nd starting from a decimal
		if(strhdlr.peek(charPos) == '.')
			throw new Exception("Unexpected character");
		return new Token(Token.Tokens.NUMBER, number, line, charPos);
	}
	
	public Token processRegister() throws Exception {
		String number = "";
		charPos++;
		strhdlr.swallow(1);
		while(Character.isDigit(strhdlr.peek(charPos))) {
			number += strhdlr.getChar();
			charPos++;
		}
		return new Token(Token.Tokens.REGISTER, number, line, charPos);
	}
	
	public LinkedList<Token> getTokens(){
		return tokenCollection;
	}
	public String toString() {
		return tokenCollection.toString();
	}
}