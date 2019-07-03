import java.util.function.*;
public class ALU{
	//flags
	boolean OVERFLOW = false;
	//ALU ops
	//Basic addition
		
	
	public Word NOT(Word in){
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < in.size();i++){
			result.append(NOT(in.getBit(i)));
		}
		return new Word(result.reverse().toString());
		
	}
	
	public Word AND(Word in1,Word in2){
		return binaryFunction(in1,in2,(x,y)->{return AND(x,y);});
	}
	public Word OR(Word in1,Word in2){
		return binaryFunction(in1,in2,(x,y)->{return OR(x,y);});
	}
	public Word XOR(Word in1,Word in2){
		return binaryFunction(in1,in2,(x,y)->{return XOR(x,y);});
	}
	public Word NOR(Word in1,Word in2){
		return binaryFunction(in1,in2,(x,y)->{return NOR(x,y);});
	}
	public boolean LESS_THAN(Word in1,Word in2){
		Word res = adder(in1,in2,'1',true); 
		//If MSB is 1 then the result is a negative number
		return res.getBit(res.size()-1) == '1';	
	}
	//TODO Test
	public boolean LESS_THAN_UNSIGNED(Word in1,Word in2){
		//The most significant bit that is different
		//between the words
		//determines which one
		//is greater than which
		for(int i = in1.size()-1; i >= 0; i--){
			if(in1.getBit(i) != in2.getBit(i)){
				if(in1.getBit(i) == '1'){
					return true;
				}
				else if(in2.getBit() == '1'){
					return false;
				}
			}
		}
		return false;
	}
	public boolean EQUAL(Word in1,Word in2){
		Word res = adder(in1,in2,'1',true);
		//returns true if the result is zero
		return new Word('0').zeroExtend(res.size()).contents().equals(res.contents());
	}
	public Word binaryFunction(Word in1,Word in2,BiFunction<Character,Character,Character> func){
		verifyEqualLengths(in1,in2);
		StringBuilder result = new StringBuilder();
		for(int i=0;i<in1.size();i++){
			result.append(func.apply(in1.getBit(i),in2.getBit(i)));
		}
		return new Word(result.reverse().toString());
	}
	//Bitwise operators
	public static Word SHIFT(Word a1, Word shamt){
		return a1.shiftLeftLogical((int)shamt.toUnsignedDecimal());
	}
	/**
		Multiplies the words. It is taken for granted
		that both of them are in an unsigned form
		TODO Test + Signed twos complement 
	**/
	public Word MULTIPLY(Word a1,Word a2){
		//Initialize 64 bit low+high register(word);
		//Result can have at most bits of a1 + bits of a2 
		int originalSize = a1.size() + a2.size();
		if(a1.size() != a2.size()) throw new RuntimeException("Tried to multiply different sized words");
		Word result = new Word("0").zeroExtend(originalSize);
		//While a2 is not empty
		while(a2.size() > 0){
			//pop lsb
			char lsb = a2.getBit(0);
			a2 = a2.shiftRightUnsigned(1);
			//if 1 add to result
			if(lsb == '1'){
				result = adder(result,a1.zeroExtend(originalSize),'0',false);
			}
			//shift left a1 by 1
			a1 = a1.shiftLeftUnsigned(1);
		}
		return result; 
	}
	/**
		Divides the words. It is taken for granted
		that both of them are in an unsigned form
		TODO Test + Signed number multiplication
	**/
	public Word DIVIDE(Word a1,Word a2){
		//Initialize 64 bit low+high register(word);
		//Result can have at most bits of a1 + bits of a2 
		int originalSize = a1.size() + a2.size();
		if(a1.size() != a2.size()) throw new RuntimeException("Tried to multiply different sized words");
		Word result = new Word("0").zeroExtend(originalSize);
		//While a2 is not empty
		while(a2.size() > 0){
			//pop lsb
			char lsb = a2.getBit(0);
			a2 = a2.shiftRightUnsigned(1);
			//if 1 add to result
			if(lsb == '1'){
				result = adder(result,a1.zeroExtend(originalSize),'0',false);
			}
			//shift left a1 by 1
			a1 = a1.shiftLeftUnsigned(1);
		}
		return result; 
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
		//TODO valid overflow check

	**/
	public Word adder(Word a1,Word a2,char carry,boolean sub){
		OVERFLOW = false;
		if(a1.size() != a2.size()) throw new RuntimeException("Tried to use adder on different sized words");
		StringBuilder result = new StringBuilder();
		carry = '0';	
		if(sub) {
			a2 = NOT(a2);
			carry = '1';	
		}
		for(int i = 0; i < a1.size() ; i++){
			//These two XORs are equivalent to one
			//XOR gate with 3 inputs
			char a = a1.getBit(i);
			char b = a2.getBit(i);
			result.append(XOR(XOR(a,b),carry));
			carry = OR(OR(AND(a,b) , AND(a,carry)) ,  AND(b,carry));
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
		Word result = adder(in,new Word("1").zeroExtend(in.size()),'0',false);
		return result;
		
	}
	public static boolean verifyEqualLengths(Word a1, Word a2){
		return a1.size()==a2.size();
	}
}

