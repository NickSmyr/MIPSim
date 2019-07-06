public class MIPSMachine{
	Word[] registers;
	Word pc;
	Memory memory;
	ALU alu;
	//TODO register to integer translation
	public MIPSMachine(){
		//initialize registers
		registers = new Word[32];
		for(int i = 0 ; i < registers.length ; i++){
			registers[i] = new Word("0").zeroExtend(32);
		}
		//Initialize alu
		alu = new ALU();
	}
	//Map that holds translations from register names to register
	//numbers (e.g a0 - > 4 , a1 -> 5 , a2 -> 6)
	HashMap name2reg = new HashMap()
	public void initializeRegisterNames()throws IOException{
		Scanner sc = new Scanner(new File("data/registerNumbers.conf"));
		while(sc.hasNextLine()){
			String[] data = sc.nextLine().split();
			name2reg.put(data[0],data[1]);
		}
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
	public void advance_pc(){
		//Adding 4 to the pc
		pc = alu.adder(pc,new Word(4).zeroExtend(pc.size()),'0',false);
	}


	////////////////////////CORE INSTRUCTIONS/////////////////////////
	//TODO Test + Exception handling
	public void add(Word rs,Word rt,Word rd){
		Word a1 = getRegister(rs.toUnsignedDecimal());
		Word a2 = getRegister(rt.toUnsignedDecimal());
		Word res = alu.adder(a1,a2,'0',false);
		if ( alu.OVERFLOW ) throw new RuntimeException("Overflow on add");
		setRegister(rd.toUnsignedDecimal(),res);
	}
	//TODO Overflwo check
	public void addi(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		//immediate has less bits than the register (32)
	       	immediate = immediate.signExtend(a1.size());	
		Word res = alu.adder(a1,a2,'0',false);
		if ( alu.OVERFLOW ) throw new RuntimeException("Overflow on addi");
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
		//Branch address according to green cheat sheet
		Word branchAddress = immediate.append(new Word("00")).signExtend(a1.size());
		if(alu.EQUAL(a1,a2)){
			Word res = adder(pc,new Word(4).signExtend(pc.size()),'0',false);
			// New address will be program counter + branch address
			// but program counter is the next instruction after 
			// this one
			pc =  adder(branchAddress,pc,'0',false);
		}
	}
	public void bne(Word rs,Word rt,Word immediate){
		Word a1 = getRegister(rs);
		Word a2 = getRegister(rt);
		//Branch address according to green cheat sheet
		Word branchAddress = immediate.append(new Word("00")).signExtend(a1.size());
		if(!alu.EQUAL(a1,a2)){
			Word res = adder(pc,new Word(4).signExtend(pc.size()),'0',false);
			// New address will be program counter + branch address
			// but program counter is the next instruction after 
			// this one
			pc =  adder(branchAddress,pc,'0',false);
		}
	}	
	public void j(Word address){
		Word jumpAddress = pc.bits(31,28).append(address)
			.append(new Word("00"));
		pc = jumpAddress;
	}
	//TODO register names to register array positions
	public void jal(Word address){
		//set ra to pc + 4
		//input words in set/getRegister methods must be
		//5 in size (up until number 31)
		setRegister(new Word(31).zeroExtend(5),pc);
		//Setting pc to jump address
		Word jumpAddress = pc.bits(31,28).append(address)
			.append(new Word("00"));
		pc = jumpAddress;
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
	public void slt(Word rs,Word rt,Word rd){
		Word a1 = getRegister(rs);
		Word a2 = getRegister(rt);

		if (alu.LESS_THAN(a1,a2)){
			setRegister(rd,new Word('1').zeroExtend(a1.size()));
		}
		else {
			setRegister(rd,new Word('0').zeroExtend(a1.size()));
		}
	}
	public void sltu(){ 
		Word a1 = getRegister(rs);
		Word a2 = getRegister(rt);

		if (alu.LESS_THAN_UNSIGNED(a1,a2)){
			setRegister(rd,new Word('1').zeroExtend(a1.size()));
		}
		else {
			setRegister(rd,new Word('0').zeroExtend(a1.size()));
		}
	}
	public void slti(){
		Word a1 = getRegister(rs);
		Word a2 = immediate.signExtend(a1.size());

		if (alu.LESS_THAN(a1,a2)){
			setRegister(rd,new Word('1').zeroExtend(a1.size()));
		}
		else {
			setRegister(rd,new Word('0').zeroExtend(a1.size()));
		}	
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
		Word res = adder(a1,a2,'1',true)
		if ( alu.OVERFLOW ) throw new RuntimeException("Overflow on sub");
		setRegister(rd,res);
	}
	//TODO How the hell should i implement this
	public void subu(Word rs,Word rt,Word rd){
		Word a1 = getRegister(rs);
		Word a2 = getRegister(rt)		
		Word res = alu.adder(a1,a2,'1',true);
		//No overflow check on unsigned sub
		setRegister(rd,res);
	}
	//Must check the specified registers and then
	//Do the appropriate action
	public void syscall(){
	
	}
}
