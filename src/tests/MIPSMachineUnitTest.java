public class MIPSMachineUnitTest {

	public boolean getsetRegisterTest(){
		MIPSMachine m = new MIPSMachine();
		Word testword = new Word(100).zeroExtend(32);
		m.setRegister("00001",testWord);

		boolean rightResult = testword.contents().equals(m.getRegister("00001"));

		m.setRegister("00000" , testword);

		rightResult = rightResult & new Word("0").zeroExtend(32).contents()
						.equals(m.getRegister("0000"));
	
		
			
	}
	public boolean registerNamesTest(){
		MIPSMachine m = new MIPSMachine();

	}
}
