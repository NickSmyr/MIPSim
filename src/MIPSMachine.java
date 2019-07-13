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
	HashMap<String,Integer> name2reg = new HashMap<String,Integer>();
	public void initializeRegisterNames()throws IOException{
		Scanner sc = new Scanner(new File("data/reg2num.conf"));
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
		Word opcode = instruction.bits(31,26);
		Word rs = instruction.bits(25,21);
		Word rt = instruction.bits(20,16);
		Word rd = instruction.bits(15,11);
		Word shamt = instruction.bits(10,6);
		Word funct = instruction.bits(5,0);
		Word immediate = instruction.bits(15,0);
		Word address = instruction.bits(25,0);
		switch(opcode.toUnsignedDecimal()){
			//R format 
			case 0:
				switch(funct.toUnsignedDecimal()){
					case 0:
						sll(rd,rt,shamt);
						break;
					case 2:
						srl(rd,rt,shamt);
						break;
					case 8:
						jr(rs,rt,rd);
						break;
					case 2*16 :
						add(rs,rt,rd);
						break;
					case 2*16 +1:
						addu(rs,rt,rd);
						break;
					case 2*16 +2:
						sub(rs,rt,rd);
						break;
					case 2*16 +3:
						subu(rs,rt,rd);
						break;
					case 2*16 +4:
						and(rs,rt,rd);
						break;
					case 2*16 +5:
						or(rs,rt,rd);
						break;
					case 2*16 + 7:
						nor(rs,rt,rd);
						break;
					case 2*16 + 10:
						slt(rs,rt,rd);
						break;
					case 2*16 + 11:
						sltu(rs,rt,rd);
						break;
					default:
						throw new RuntimeException("Unsupported instruction with opcode 0");
						break;
				}
				break;
			case 8:
				addi(rs,rt,immediate);
				break;
			case 9:
				addiu(rs,rt,immediate);	
				break;
			//0xc
			case 12:
				andi(rs,rt,immediate);
				break;
			case 4:
				beq(rs,rt,immediate);
				break;
			case 5:
				bne(rs,rt,immediate);
				break;
			case 2:
				j(address);
				break;
			case 3: jal(address);
				break;	
			//TODO Rename loadlinked
			case 3*16: 
				break;
			//0xf
			case 15:
				lui(rs,rt,immediate);
				break;
			case 2*16 +3:
				lw(rs,rt,immediate);
				break;
			//0xd
			case 13:
				ori(rs,rt,immediate);
				break;
			//0xa
			case 10:
				slti(rs,rt,immediate);
				break;
			//0xb
			case 11:
				sltiu(rs,rt,immediate);
				break;
			case 2*16 + 8:
				sb(rs,rt,immediate);
				break;
			//TODO Rename store conditional
			case 3*16 + 8:
				sc()
				break;
			case 2* 16 + 9:
				sh(rs,rt,immediate);
				break;
			case 2 * 16 + 11:
				sw(rs,rt,immediate);
				break;
			//Syscall opcode
			//Credits to vaggourinos
			case 27:
				syscall();
				break;
			default:
				throw new RuntimException("Unsupported opcode by the machine");
		}
			
	}
	//get register
	//TODO Test
	public Word getRegister(Word reg){
		return registers[in.toUnsignedDecimal()];
	}
	//Returns a the register from the register name
	// e.g getReg("ra")
	public Word getRegister(String regname){
		return registers[name2reg.get(regname)];
	}
	public void setRegister(String regname,Word value){
		long regNum = name2reg.get(regname); 
		if( regNum ==0 ) return;
		registers[regNum] = value;
		return;
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
		setRegister("ra",pc);
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
	public void ll(Word rs,Word rt,Word immediate){
		throw new RuntimeException("This simulator doesn't not support load linked op");
	}
	public void lui(Word rs,Word rt,Word immediate){
		setRegister(rt,immediate.append(new Word("0").zeroExtend(32)));
	}
	public void lw(Word rs,Word rt,Word immediate){
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
	public void sltu(Word rs,Word rt,Word rd){ 
		Word a1 = getRegister(rs);
		Word a2 = getRegister(rt);

		if (alu.LESS_THAN_UNSIGNED(a1,a2)){
			setRegister(rd,new Word('1').zeroExtend(a1.size()));
		}
		else {
			setRegister(rd,new Word('0').zeroExtend(a1.size()));
		}
	}
	public void slti(Word rs,Word rt,Word rd){
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
		//Words are treated as being 33 bit signed values
		//and then they are subbed as usual
		Word a1 = new Word("0").append(getRegister(rs));
		Word a2 = new Word("0").append(getRegister(rt));		
		Word res = alu.adder(a1,a2,'1',true);
		//No overflow check on unsigned sub
		setRegister(rd,res.bits(31,0));
	}
	////////////////////////SYSCALLS/////////////////////////
	//Must check the specified registers and then
	//Do the appropriate action
	public void syscall(){
		//print integer
		//print string	
		//read integer
		//read string
		//exit
	}
	/**
	 * Prints the value of a0 to screen
	 **/
	public void print_integer(){
		System.out.print(getRegister("a0").toUnsignedDecimal());
	}
	/**
	 * Reads an integer from input and stores it at register v0.
	 */
	public void read_integer(){
		Scanner sc = new Scanner(System.in);
		int res = sc.nextInt();
		Word result = new Word(res).zeroExtend(32);
		if(result.size() > 32) {
			throw new RuntimeException("Input value too large for 32 bits");
		}
		//Outputing result to v0 register
		setRegister("v0",result);
	}
	/**
	 * Prints the string at the address that a0 points to
	 **/
	public void print_string(){
		//Get address of a0
		long address = getRegister("a0");
		int characterSize = Word.CHARACTER_BITS;
		//Geting char at address (one char ) 
		Word currentChar = Memory.read(address).bits(31,32 - characterSize);
		//while char is not '\0'
		while (currrentChar.getAsChar() != '\0'){
			System.out.print(currentChar.getAsChar());
			address = address + characterSize;
			currentChar = Memory.read(address).bits(31,32 - characterSize);
		}	
	}
	public void read_string(){
		//Read string from stdin
		String input = "" 
		try{
			Scanner sc = new Scanner(System.in);
			input = sc.nextLine();
		}
		catch(IOException e){
			System.err.println("Couldn't read from stdin");
			return;
		}
		finally{
			sc.close();
		}
		//Loop through every character
		long address = getRegister("a0");
		int numChars = getRegister("a1");
		int characterSize = Word.CHARACTER_BITS;
		// word that gets filled with the characters that
		// are to be output
		Word outputWord = new Word("0");
		for (int i = 0 ; i < numChars ; i++){
			//While there are still characters left
			//and enough haven't been gathered to fill
			//a word
			while(outputWord.size() < 32 && i < numChars){
				Word currentChar = Word.createFromChar(input.charAt(i));
				outputWord.append(currentChar);
				i++;
			}
			//If the string has not been completed
			//the remaining characters are not enough
			//to fill up a word and so the loop must
			//broken 
			if(i >= numChars){
				break;
			}
			//This means that enough characters have been
			//gathered so that they can fill a word
			memory.write(address,outputWord);
			outputWord = new Word("");
			address += 4;
		}
		//now the outputWord contains the characters that didnt
		//fill up a word
		outputWord.append(Word.createFromChar('\0'));
		if(outputWord.size() == 32){
			memory.write(address,outputWord);
		}
		else{
			//We must write data that is less than a word
			//Already existing data in memory at current
			//address must be preserved
			Word previousWord = memory.read(address);
			//Data we are going to preserve
			Word preserved = previousWord.bits(32-outputWord.size()-1,0);
			//The final form of the word at current address
			Word finalWord  = outputWord.append(preserved);
			memory.write(address,finalWord);
		}
	}

}
