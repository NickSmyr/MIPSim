public class ALUUnitTest{
	ALU alu;
	public ALUUnitTest(){
		alu = new ALU();
	}
	public boolean AND_Test(){
		Word a1 = new Word("11110000");
		Word a2 = new Word("00001111");
		return alu.AND(a1,a2).contents().equals("00000000");
	}
	public boolean OR_Test(){
		Word a1 = new Word("11110000");
		Word a2 = new Word("00001111");
		return alu.OR(a1,a2).contents().equals("11111111");
	}
	public boolean XOR_Test(){
		Word a1 = new Word("00110011");
		Word a2 = new Word("00001111");
		return alu.XOR(a1,a2).contents().equals("00111100");
	}
	public boolean NOR_Test(){
		Word a1 = new Word("00110011");
		Word a2 = new Word("00001111");
		return alu.NOR(a1,a2).contents().equals("11000000");
	}
	public boolean SHIFT_Test(){
		Word a1 = new Word("00110011");
		Word shamt = new Word("011");
		return alu.SHIFT(a1,shamt).contents().equals("10011001");
	}
	public boolean NOT_Test(){
		Word a1 = new Word("00110011");
		return alu.NOT(a1).contents().equals("11001100");
	}
	
	public boolean GREATER_THAN_Test(){
		Word a1 = new Word("00110011");
		Word a2 = new Word("00110010");
		return alu.GREATER_THAN(a1,a2);
	}
	public boolean SUB_Test(){
		Word a1 = new Word("00110011");
		Word a2 = new Word("00110010");
		return alu.adder(a1,a2,'1',true).contents().equals("00000001");
	}
	public boolean zeroExtendTest(){
		Word a1 = new Word("00110011");
		Word a2 = alu.zeroExtend(a1,16);
		return a2.contents().equals("0000000000110011");
	}
	public boolean signExtendTest(){
		Word a1 = new Word("00110011");
		Word a2 = new Word("11001100");
		Word res1 = alu.signExtend(a1,16);
		Word res2 = alu.signExtend(a2,16);
		return res1.contents().equals("0000000000110011") && res2.contents().equals("1111111111001100") ;

	}
	public boolean EQUAL_Test(){
		Word a1 = new Word("10001");
		Word a2 = new Word("10001");
		return alu.EQUAL(a1,a2);
	}
	public boolean NOT_EQUAL_Test(){
		Word a1 = new Word("10001");
		Word a2 = new Word("10000");
		return alu.NOT_EQUAL(a1,a2);
	}
}
