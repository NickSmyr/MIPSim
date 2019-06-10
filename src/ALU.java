public class ALU{
	//flags
	boolean OVERFLOW = false;
	//ALU ops
	//Basic addition
	/**
		Function that has the function of an adder
		That means it can do both addition and 
		subtraction
		//TODO valid overflow check
	**/
	
	
	public Word NOT(Word in){
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < in.size();i++){
			result.append(in.getBit(i));
		}
		return new Word(result.reverse().toString());
		
	}
	//ALL FOR THE SAKE OF DRY 
	public Word AND(Word in1,Word in2){
		return binaryFunction(in1,in2,(x,y)->return AND(x,y));
	}
	public Word OR(Word in1,Word in2){
		return binaryFunction(in1,in2,(x,y)->return OR(x,y));
	}
	public Word XOR(Word in1,Word in2){
		return binaryFunction(in1,in2,(x,y)->return XOR(x,y));
	}
	public boolean GREATER_THAN(Word in1,Word in2){
		return binaryPredicate(in1,in2,(x) -> return x>0;)
	}
	//TODO EQUAL
	public Word binaryFunction(Word in1,Word in2,BiFunction<char,char,char> func){
		verifyEqualLengths(in1,in2);
		StringBuilder result = new StringBuilder();
		for(int i=0;i<in1.length();i++){
			result.append(func.apply(in1,in2));
		}
		return new Word(result.reverse().toString());
	}
	public boolean binaryPredicate(Word in1,Word in2,Predicate<Integer> predicate){
		verifyEqualLengths(in1,in2);
		//Subtract in1-in2
		Word temp = adder(in1,in2,"1",true);
		//COnverts the substraction to a string of a integer and then to an integer
		return predicate.test(Long.valueOf(temp.toIntegerString()));
	}
	//Bitwise operators
	//TODO shift operator on words
	//TODO multiply
	//TODO divide
	public static char NOT(char a){
		if(a == '0'){
			return '1';
		}
		else return '0';
	}
	public static char OR(char a,char b){
		if(a == '1' || b == '1'){
			return '1';
		}
		return '0';
	}
	public static char AND(char a,char b){
		if(a == '1' && b == '1'){
			return '1';
		}
		return '0';
	}
	public static char XOR(char a,char b){
		if( a == '1' && b == '1'){
			return '0';
		}
		else return OR(a,b);
	}
	public static char NOR(char a,char b){
		return NOT(OR(a,b));
	}
	public String adder(Word a1,Word a2,char carry,boolean sub){
		StringBuilder result = new StringBuilder();
		char carry = '0';
		if(sub) {
			a2 = NOT(a2);	
		}
		for(int i = 0; i < 32 ; i++){
			//These two XORs are equivalent to one
			//XOR gate with 3 inputs
			char a = a1.getBit(i);
			char b = a2.getBit(i);
			result.append(XOR(XOR(a,b),carry));
			carry = OR(OR(OR(a,b) , OR(a,carry)) , OR(b,carry));
		}
		result.reverse();
		if(carry == '1') OVERFLOW = true;
		return  result.toString();
	}
}
