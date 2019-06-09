public class ALU{
	//flags
	boolean OVERFLOW = false;
	//ALU ops
	//Basic addition
	//TODO add input carry bit 
	public String add(Word a1,Word a2){
		StringBuilder result = new StringBuilder();
		char carry = '0';
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
	//TODO adder wrapper for comparisons
	//Bitwise operators
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
}
