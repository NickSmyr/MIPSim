public class MIPSMachine{
	Word[] registers;
	Word pc;
	Memory memory;
	ALU alu;
	//TODO register to integer translation
	public MIPSMachine(){
		//initialize registers
		registers = new Word[32];
		for(int i = 0 ; i < registers.length ; i++ ){
			registers[i] = new Word("0").zeroExtend(32);
		}
		//Initialize alu
		alu = new ALU();
	}
	/**
		Executes instruction, input instruction
		is 32 bits, MSB format
	**/
	//TODO
	public void exec(Word instruction){
	}
	//get register
	//TODO
	public Word getRegister(int i){
		return null;
	}
	//TODO
	public void setRegister(int i,Word value){
		return;
	}
	//operations on registers
	public void advance_pc(long amt){
		//pc += amt;
	}


	////////////////////////CORE INSTRUCTIONS/////////////////////////
	//TODO Test + Exception handling
	public void add(Word rs,Word rt,Word rd){
		Word a1 = getRegister(rs.toUnsignedDecimal());
		Word a2 = getRegister(rt.toUnsignedDecimal());
		Word res = alu.adder(a1,a2,'0',false);
		setRegister(rd.toUnsignedDecimal(),res);
	}
	//TODO Overflwo check
	public void addi(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		//immediate has less bits than the register (32)
	       	immediate = immediate.signExtend(a1.size());	
		Word res = alu.adder(a1,a2,'0',false);
		setRegister(rt,res);
	}
	//TODO Test
	public void addiu(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		//immediate has less bits than the register (32)
	       	immediate = immediate.signExtend(a1.size());	
		Word res = alu.adder(a1,a2,'0',false);
		setRegister(rt,res);

	}
	//TODO Test
	public void addu(Word rs,Word rt,Word rd){
		Word a1 = getRegister(rs.toUnsignedDecimal());
		Word a2 = getRegister(rt.toUnsignedDecimal());
		Word res = alu.adder(a1,a2,'0',false);
		setRegister(rd.toUnsignedDecimal(),res);
	}
	public void and(Word rs,Word rt,Word rd){
		Word a1 = getRegister(rd);
		Word a2 = getRegister(rt);
		Word res = alu.AND(a1,a2);
		setRegister(rd,res);
	}
	public void andi(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rd);
		immediate = immediate.zeroExtend(a1.size());
		Word res = alu.AND(a1,immediate);
		setRegister(rt,res);
	}
	//TODO PCstuff
	public void beq(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = getRegister(rt);
		if(alu.EQUAL(a1,a2)){
			Word res = adder(pc,new Word(4).signExtend(pc.size()),'0',false);
			//Adding branch address
			res = adder(res,immediate,'0',false);
		}
	}
	public void bne(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = getRegister(rt);
		if(!alu.EQUAL(a1,a2)){
			Word res = adder(pc,new Word(4).signExtend(pc.size()),'0',false);
			//Adding branch address
			res = adder(res,immediate,'0',false);
		}
	}	
	public void jump(Word address){
		pc = address;
	}
	//TODO 
	public void jal(Word address){
		//set ra to pc + 4
		pc = address;
	}
	public void jr(Word rs,Word rt,Word rd){
		pc = getRegister(rs);
	}
	
	
}
