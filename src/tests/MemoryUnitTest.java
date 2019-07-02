public class MemoryUnitTest {
	Memory memory = new Memory();
	public boolean mult4_readWriteTest(){
		memory = new Memory();
		memory.write(0,new Word(0).zeroExtend(32));
		memory.write(4,new Word(1).zeroExtend(32));
		memory.write(8,new Word(2).zeroExtend(32));
		memory.write(404,new Word(500).zeroExtend(32));
		memory.write(4,new Word(2).zeroExtend(32));
		
		//reading
		Word res1 = memory.read(0);
		Word res2 = memory.read(4);
		Word res3 = memory.read(8);
		Word res4 = memory.read(404);

		return res1.contents().equals(new Word(0).zeroExtend(32).contents()) &&
		res2.contents().equals(new Word(2).zeroExtend(32).contents()) &&	
		res3.contents().equals(new Word(2).zeroExtend(32).contents()) &&	
		res4.contents().equals(new Word(500).zeroExtend(32).contents()); 	
	       	

	}
	public boolean mult2_readWriteTest(){
		memory = new Memory();
		memory.write(0,new Word(0).zeroExtend(32));
		memory.write(6,new Word(1).zeroExtend(32));
		memory.write(10,new Word(2).zeroExtend(32));
		memory.write(406,new Word(500).zeroExtend(32));
		memory.write(6,new Word(2).zeroExtend(32));
		
		//reading
		Word res1 = memory.read(0);
		Word res2 = memory.read(6);
		Word res3 = memory.read(10);
		Word res4 = memory.read(406);

		return res1.contents().equals(new Word(0).zeroExtend(32).contents()) &&
		res2.contents().equals(new Word(2).zeroExtend(32).contents()) &&	
		res3.contents().equals(new Word(2).zeroExtend(32).contents()) &&	
		res4.contents().equals(new Word(500).zeroExtend(32).contents()); 	
	       	

	}
	public boolean mult3_readWriteTest(){
		memory = new Memory();
		memory.write(1,new Word(0).zeroExtend(32));
		memory.write(7,new Word(1).zeroExtend(32));
		memory.write(11,new Word(2).zeroExtend(32));
		memory.write(407,new Word(500).zeroExtend(32));
		memory.write(7,new Word(2).zeroExtend(32));
		
		//reading
		Word res1 = memory.read(1);
		Word res2 = memory.read(7);
		Word res3 = memory.read(11);
		Word res4 = memory.read(407);

		return res1.contents().equals(new Word(0).zeroExtend(32).contents()) &&
		res2.contents().equals(new Word(2).zeroExtend(32).contents()) &&	
		res3.contents().equals(new Word(2).zeroExtend(32).contents()) &&	
		res4.contents().equals(new Word(500).zeroExtend(32).contents()); 	
	}
}
