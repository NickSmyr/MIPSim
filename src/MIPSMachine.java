public class MIPSMachine{
	Word[] registers;
	Word pc;
	Memory memory;
	
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
	//TODO
	/**
	* Adds enough bits from the left so that the result is a 32-bit word
	* If the leftmost bit is 1 , the added bits are all 1
	* If the leftmost bit is 0 , the added bits are all 0
	**/
	//public void signExtend(subset of bits within a word object){
	//}
	//TODO
	/**
	* Adds enough zero bits from the left so that the result is a 32-bit word
	**/
	public void zeroExtend(){}
	//TODO shift operator on words
	
	
}
