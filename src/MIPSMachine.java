public class MIPSMachine{
	Word[] registers;
	long pc = 0;
	Memory memory;
	//flags
	boolean OVERFLOW = false;
	//get register
	public Word getRegister(int i){
		return null;
	}
	//operations on registers
	public void advance_pc(long amt){
		pc += amt;
	}
	//ALU ops
	public Word add(Word a1,Word a2){
		StringBuilder result = new StringBuilder();
		char carry = '0';
		for(int i = 31; i >= 0 ; i--){
			//These two XORs are equivalent to one
			//XOR gate with 3 inputs
			result.append(XOR(XOR(a1.getBit(i),a2.getBit(i)),carry));
			carry = AND(AND(a1.getBit(i),a2.getBit(i)), carry);
		}
		result.reverse();
		if(carry == '1') OVERFLOW = true;
		return new Word(result.toString());
	}
	//Bitwise operators
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
