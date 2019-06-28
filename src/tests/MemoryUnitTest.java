public class MemoryUnitTest {
	Memory memory;
	public boolean readWriteTest(){
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
}
