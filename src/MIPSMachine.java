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
	
	
	
	
}
