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
	//TODO test
	public static Word SHIFT(Word a1, Word shamt){
		return a1.rotateLeft(shamt.toUnsignedDecimal());
	}
	//TODO multiply
	//Should return a 64 bit word
	public static Word MULTIPLY(Word a1,Word a2){
		//Initialize 64 bit low+high register(word);
		//Result can have at most bits of a1 + bits of a2 
		Word result = zeroExtend(new Word("0"),a1.size() + a2.size());
		//While a2 is not empty
		//pop lsb
		//if 1 add to result
		//shift left result by 1 
	}
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
	/**
		An adder that can perform add or sub with numbers
		on two's complement form.
		
		Luckily i'm trying to virtualize a machine through a virtual machine
		on my machine and i can use 32 or 64 bit adder functionality
		through this method
	**/
	public String adder(Word a1,Word a2,char carry,boolean sub){
		OVERFLOW = false;
		if(a1.size() != a2.size()) throw new RuntimeException("Tried to use adder on different sized words");
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
		return  new Word(result.toString());
	}
	/**
		Returns the negation of the word
		plus 1. That is the negative value 
		of a number in twos complement
	**/
	public Word negate(Word in){
		//Negating in
		in = NOT(in);
		//Adding 1 to the Word
		return adder(in,zeroExtend("1",in.size()),"0",false);
		
	}
	/**
	* Adds enough bits from the left so that the result is a 32-bit word
	* If the leftmost bit is 1 , the added bits are all 1
	* If the leftmost bit is 0 , the added bits are all 0
	* TODO Test
	**/
	public static Word signExtend(Word in, int numBits){
		int size = in.size();
		if(size >= 32){
			return in;
		}
		//Leftmost bit of input
		char MSBBit = '0';
		//Might be empty
		if(size>0 && in.getBit(size-1)=='1' ) MSBBit = '1';
		//Extending with MSBBit
		StringBuilder result = new StringBuilder();
		while(size < numBits){
			result.append(MSBBit);
			size++;
		}
		//Starting with MSB and moving to LSB
		for(int i = in.size()-1; i >= 0 ; i--){
			result.append(in.getBit(i));
		}
		return new Word(result.toString());
	}
	
	/**
	* Adds enough zero bits from the left so that the result is a word
	* with numBits bits
	* TODO Test
	**/
	public static Word zeroExtend(Word in, int numBits){
		int size = in.size();
		if(size >= numBits){
			return in;
			size++;
		}
		StringBuilder result = new StringBuilder();
		//Adding enough zeros from the left
		while(size < numBits){
			result.append('0');
		}
		//Starting with MSB and moving to LSB
		for(int i = in.size()-1; i >= 0 ; i--){
			result.append(in.getBit(i));
		}
		return new Word(result.toString());
	}
}

