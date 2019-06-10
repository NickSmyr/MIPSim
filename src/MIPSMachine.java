public class MIPSMachine{
	Word[] registers;
	Word pc;
	Memory memory;
	ALU alu;
	/**
		Executes instruction, input instruction
		is 32 bits, MSB format
	**/
	public void exec(Word instruction){
	}
	//get register
	public Word getRegister(int i){
		return null;
	}
	//operations on registers
	public void advance_pc(long amt){
		//pc += amt;
	}
	/**
	* Adds enough bits from the left so that the result is a 32-bit word
	* If the leftmost bit is 1 , the added bits are all 1
	* If the leftmost bit is 0 , the added bits are all 0
	**/
	public void signExtend(Word in){
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
		while(size < 32){
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
	* Adds enough zero bits from the left so that the result is a 32-bit word
	**/
	public Word zeroExtend(Word in){
		int size = in.size();
		if(size >= 32){
			return in;
			size++;
		}
		StringBuilder result = new StringBuilder();
		while(size < 32){
			result.append('0');
		}
		//Starting with MSB and moving to LSB
		for(int i = in.size()-1; i >= 0 ; i--){
			result.append(in.getBit(i));
		}
		return new Word(result.toString());
	}
	
	
	
}
