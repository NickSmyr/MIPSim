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
	//TODO Test
	public Word getRegister(Word reg){
		return registers[in.toUnsignedDecimal()];
	}
	//TODO Test
	public void setRegister(Word reg,Word value){
		long regNum = reg.toUnsignedDecimal();
		if( regNum ==0 ) return;
		registers[regNum] = value;
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
	//TODO Correct address modification 
	// i think i need to shift it by 2
	public void jal(Word address){
		//set ra to pc + 4
		pc = address;
	}
	public void jr(Word rs,Word rt,Word rd){
		pc = getRegister(rs);
	}
	public void lbu(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = immediate.signExtend(a1.size());
		//Adding immediate address and rt register\s contents
		Word a1plusa2 = adder(a1,a2,'0',false);
		Word result = memory.read(a1plusa2).bits(7,0).zeroExtend(a1.size());
		setRegister(rt,result);
	}
	public void lhu(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = immediate.signExtend(a1.size());
		//Adding immediate address and rt register\s contents
		Word a1plusa2 = adder(a1,a2,'0',false);
		Word result = memory.read(a1plusa2).bits(15,0).zeroExtend(a1.size());
		setRegister(rt,result);
	}	
	public void loadLinked(Word rs,Word rt,Word immediate){
		throw new RuntimeException("This simulator doesn't not support load linked op");
	}
	public void loadUpperImmediate(Word rs,Word rt,Word immediate){
		setRegister(rt,immediate.append(new Word("0").zeroExtend(32)));
	}
	public void loadWord(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = immediate.signExtend(a1.size());
		Word a1plusa2 = alu.adder(a1,a2,'0',false);
		Word result = memory.read(a1plusa2);
		setRegister(rt,result);
	}	
	public void nor(Word rs,Word rt,Word rd){
		setRegister(rd,alu.NOR(getRegister(rs),getRegister(rt)));
	}
	public void or(Word rs,Word rt,Word rd){
		setRegister(rd,alu.OR(getRegister(rs),getRegister(rt)));
	}
	public void ori(Word rs,Word rt,Word immediate){
		setRegister(rs,alu.OR(getRegister(rs),immediate.zeroExtend()));
	}
	//TODO less than operator in alu
	public void slt(Word rs,Word rt,Word rd){
	}
	//TODO Unsigned adder ?
	public void sltiu(){
	}
	//TODO Unsigned adder?
	public void sltu(){
	}
	public void sll(Word rd,Word rt,Wort shamt){
		setRegister(rd,getRegister(rt).shiftLeftLogical(shamt.toUnsignedDecimal()));
	}
	public void srl(Word rd,Word rt,Wort shamt){
		setRegister(rd,getRegister(rt).shiftRightLogical(shamt.toUnsignedDecimal()));
	}
	public void sb(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = immediate.signExtend(a1.size());
		Word address = adder(a1,a2);

		Word prev = memory.load(address);
		Word res = prev.bits(prev.size()-1,8).append(getRegister(rt).bits(7,0));
		memory.write(address,res);

	}
	public void sc(Word rs,Word rt,Word immediate){
		throw new RuntimeException("This simulator doesn't support store conditional op");
	}
	public void sh(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = immediate.signExtend(a1.size());
		Word address = adder(a1,a2);

		Word prev = memory.load(address);
		Word res = prev.bits(prev.size()-1,16).append(getRegister(rt).bits(15,0));
		memory.write(address,res);
	}
	public void sw(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = immediate.signExtend(a1.size());
		Word address = adder(a1,a2);

		memory.write(address,getRegister(rt));
	}
	public void sub(Word rs,Word rt,Word rd){
		Word a1 = getRegister(rs);
		Word a2 = getRegister(rt);

		setRegister(rd,adder(a1,a2,'1',true));
	}
	//TODO unsigned subber 
	public void subu(Word rs,Word rt,Word rd){
		
	}
}
