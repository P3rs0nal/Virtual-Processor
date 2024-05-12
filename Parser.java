import java.util.ArrayList;
import java.util.LinkedList;

public class Parser {
	private TokenManager tokenMNG;
	public Parser(LinkedList<Token> tokenStream) {
		tokenMNG = new TokenManager(tokenStream);
	}
	
	public Parser(Lexer lexer) {
		tokenMNG = new TokenManager(lexer.getTokens());
	}
	
	public boolean acceptSeperators() {
		boolean seperator = false;
		while(tokenMNG.matchAndRemove(Token.Tokens.SEPARATOR).isPresent()) {
			seperator = true;
		}
		return seperator;
	}
	
	public ArrayList<String> parse() throws Exception {
		ArrayList<Node> nodes = new ArrayList<>();
		ArrayList<String> ret = new ArrayList<>();
		while(tokenMNG.moreTokens()) {
//			System.out.println(tokenMNG.getTokens());
			acceptSeperators();
			var t = parseStatement();
			if(t != null) {
				nodes.add(t);
			}
		}
		for(Node node: nodes) {
			ret.add(node.r.toStringCombined());
		}
		return ret;
	}
	
	public Node parseMath() throws Exception {
		int function = 0;
		
		tokenMNG.matchAndRemove(Token.Tokens.MATH);

		if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.AND)) {
			tokenMNG.matchAndRemove(Token.Tokens.AND);
			function = 8;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.OR)) {
			tokenMNG.matchAndRemove(Token.Tokens.OR);
			function = 9;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.XOR)) {
			tokenMNG.matchAndRemove(Token.Tokens.XOR);
			function = 10;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.NOT)) {
			tokenMNG.matchAndRemove(Token.Tokens.NOT);
			function = 11;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.ADD)) {
			tokenMNG.matchAndRemove(Token.Tokens.ADD);
			function = 14;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.SUBTRACT)) {
			tokenMNG.matchAndRemove(Token.Tokens.SUBTRACT);
			function = 15;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.MULTIPLY)) {
			tokenMNG.matchAndRemove(Token.Tokens.MULTIPLY);
			function = 7;
		}
		
		Node ret = parseR();
		if (ret instanceof node_3R) {
	        ((node_3R) ret).setFunction(function);
	        ((node_3R) ret).construct();
	    } else if (ret instanceof node_2R) {
	    	((node_2R) ret).setFunction(function);
	    	((node_2R) ret).construct();
	    } else if (ret instanceof node_Dest) {
	    	((node_Dest) ret).setFunction(function);
	    	((node_Dest) ret).construct();
	    }
		return ret;
	}
	
	public Node parseBranch() throws Exception {
		int function = 0;
		
		tokenMNG.matchAndRemove(Token.Tokens.BRANCH);

		if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.EQUAL)) {
			tokenMNG.matchAndRemove(Token.Tokens.EQUAL);
			function = 0;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.UNEQUAL)) {
			tokenMNG.matchAndRemove(Token.Tokens.UNEQUAL);
			function = 1;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.LESS)) {
			tokenMNG.matchAndRemove(Token.Tokens.LESS);
			function = 2;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.GREATEROREQUAL)) {
			tokenMNG.matchAndRemove(Token.Tokens.GREATEROREQUAL);
			function = 3;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.GREATER)) {
			tokenMNG.matchAndRemove(Token.Tokens.GREATER);
			function = 4;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.LESSOREQUAL)) {
			tokenMNG.matchAndRemove(Token.Tokens.LESSOREQUAL);
			function = 5;
		}
		
		Node ret = parseR();
		if (ret instanceof node_3R) {
	        ((node_3R) ret).setFunction(function);
	        ((node_3R) ret).setImm(Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.NUMBER).get().getTokenValue()));
	        ((node_3R) ret).construct();
	    } else if (ret instanceof node_2R) {
	    	((node_2R) ret).setFunction(function);
	        ((node_2R) ret).setImm(Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.NUMBER).get().getTokenValue()));
	    	((node_2R) ret).construct();
	    } else if (ret instanceof node_Dest) {
	    	((node_Dest) ret).setFunction(function);
	    	((node_Dest) ret).construct();
	    }		
		return ret;
	}
	
	public Node parseCall() throws Exception {
		int function = 0;
		
		tokenMNG.matchAndRemove(Token.Tokens.CALL);

		if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.EQUAL)) {
			tokenMNG.matchAndRemove(Token.Tokens.EQUAL);
			function = 0;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.UNEQUAL)) {
			tokenMNG.matchAndRemove(Token.Tokens.UNEQUAL);
			function = 1;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.LESS)) {
			tokenMNG.matchAndRemove(Token.Tokens.LESS);
			function = 2;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.GREATEROREQUAL)) {
			tokenMNG.matchAndRemove(Token.Tokens.GREATEROREQUAL);
			function = 3;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.GREATER)) {
			tokenMNG.matchAndRemove(Token.Tokens.GREATER);
			function = 4;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.LESSOREQUAL)) {
			tokenMNG.matchAndRemove(Token.Tokens.LESSOREQUAL);
			function = 5;
		}
		
		Node ret = parseR();
		if (ret instanceof node_3R) {
	        ((node_3R) ret).setFunction(function);
	        ((node_3R) ret).construct();
	    } else if (ret instanceof node_2R) {
	    	((node_2R) ret).setFunction(function);
	    	((node_2R) ret).construct();
	    } else if (ret instanceof node_Dest) {
	    	((node_Dest) ret).setFunction(function);
	    	((node_Dest) ret).construct();
	    }
		return ret;
	}
	
	public Node parseLoad() throws Exception {
		
		tokenMNG.matchAndRemove(Token.Tokens.LOAD);
		
		Node ret = parseR();
		int val = Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.NUMBER).get().getTokenValue());
		if (ret instanceof node_3R) {
			((node_3R) ret).setImm(val);
			((node_3R) ret).construct();
		}
	       
	    else if (ret instanceof node_2R) {
	    	((node_2R) ret).setImm(val);
	    	((node_2R) ret).construct();
	    }
	    	
	    else if (ret instanceof node_Dest) {
	    	((node_Dest) ret).setImm(val);
	    	((node_Dest) ret).construct();
	    }
		return ret;
	}
	
	public Node parsePush() throws Exception {
		
		tokenMNG.matchAndRemove(Token.Tokens.PUSH);
		
		Node ret = parseR();
		if (ret instanceof node_3R)
	        ((node_3R) ret).construct();
	    else if (ret instanceof node_2R)
	    	((node_2R) ret).construct();
	    else if (ret instanceof node_Dest)
	    	((node_Dest) ret).construct();
		return ret;
	}
	
	public Node parsePop() throws Exception {
		
		tokenMNG.matchAndRemove(Token.Tokens.POP);
		
		Node ret = parseR();
		if (ret instanceof node_3R)
	        ((node_3R) ret).construct();
	    else if (ret instanceof node_2R)
	    	((node_2R) ret).construct();
	    else if (ret instanceof node_Dest)
	    	((node_Dest) ret).construct();
		return ret;
	}
	
	public Node parseStore() throws Exception {
		
		tokenMNG.matchAndRemove(Token.Tokens.STORE);
		
		Node ret = parseR();
		int val = Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.NUMBER).get().getTokenValue());
		if (ret instanceof node_3R) {
			((node_3R) ret).construct();
		}
	    else if (ret instanceof node_2R) {
	    	((node_2R) ret).construct();
	    }
	    else if (ret instanceof node_Dest) {
	    	((node_Dest) ret).setRD(val);
	    	((node_Dest) ret).construct();
	    }
		return ret;
	}
	
	public Node parseShift() throws Exception {
		if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.SHIFT)) {
			tokenMNG.matchAndRemove(Token.Tokens.SHIFT);
			if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.RIGHT)) {
				tokenMNG.matchAndRemove(Token.Tokens.RIGHT);
				var ret = parseR();
				if(!(ret instanceof node_3R)) {
					if(!(ret instanceof node_2R)) {
						throw new Exception("expected at least 2 registers");
					}
					((node_2R) ret).setFunction(13); 
				}
			}
			else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.LEFT)) {
				tokenMNG.matchAndRemove(Token.Tokens.LEFT);
				var ret = parseR();
				if(!(ret instanceof node_3R)) {
					if(!(ret instanceof node_2R)) {
						throw new Exception("expected at least 2 registers");
					}
					((node_2R) ret).setFunction(12); 
				}
			}
			else
				throw new Exception("expected left or right");
		}
		return null;
	}
	
	public Node parseStatement() throws Exception{
		if(tokenMNG.peek(0) == null) {
			return null;
		}
		if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.MATH)) {
			return parseMath();
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.SHIFT)) {
			return parseShift();
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.HALT)) {
			tokenMNG.matchAndRemove(Token.Tokens.HALT);
			node_noR ret = new node_noR();
			ret.construct();
			return ret; // HALT
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.COPY)) {
			tokenMNG.matchAndRemove(Token.Tokens.COPY);
			int val = Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.NUMBER).get().getTokenValue());
			int reg = Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.REGISTER).get().getTokenValue());
			node_Dest ret = new node_Dest();
			ret.setRD(reg);
			ret.setImm(val);
			ret.construct();
			return ret;
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.JUMP)) {
			tokenMNG.matchAndRemove(Token.Tokens.JUMP);
			return new node_Dest(); // JUMP
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.CALL)) {
			return parseCall(); // CALL
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.BRANCH)) {
			return parseBranch(); // BRANCH
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.PUSH)) {
			return parsePush(); // PUSH
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.POP)) {
			return parsePop(); // POP
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.LOAD)) {
			return parseLoad(); // LOAD
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.STORE)) {
			return parseStore(); // STORE
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.RETURN)) {
			tokenMNG.matchAndRemove(Token.Tokens.RETURN);
			return parsePop(); // RETURN
		}
		else if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.PEEK)) {
			tokenMNG.matchAndRemove(Token.Tokens.PEEK);
			return new node_Dest(); // PEEK
		}
		
		return null;
	}
	
	public Node parseR() throws Exception {
		if(tokenMNG.peek(0).get().getTokenType().equals(Token.Tokens.REGISTER)) {
			if(tokenMNG.peek(1).get().getTokenType().equals(Token.Tokens.REGISTER)) {
				if(tokenMNG.peek(2).get().getTokenType().equals(Token.Tokens.REGISTER)) {
					node_3R ret = new node_3R();
					ret.setRS1(Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.REGISTER).get().getTokenValue()));
					ret.setRS2(Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.REGISTER).get().getTokenValue()));
					ret.setRD(Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.REGISTER).get().getTokenValue()));
					return ret;
				}
				else {
					node_2R ret = new node_2R();
					ret.setRS1(Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.REGISTER).get().getTokenValue()));
					ret.setRD(Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.REGISTER).get().getTokenValue()));
					return ret;
				}
			}
			else {
				node_Dest ret = new node_Dest();
				ret.setRD(Integer.parseInt(tokenMNG.matchAndRemove(Token.Tokens.REGISTER).get().getTokenValue()));
				return ret;
			}
		}
		return null;
	}
	
	public LinkedList<Token> getTokens(){
		return tokenMNG.getTokens();
	}
	public String toString() {
		return tokenMNG.toString();
	}
}