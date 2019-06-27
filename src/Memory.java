/**
 *	Class that represents the memory being used by a program
 *	running on the MIPSMachine	
 *
 *	Current implementation enforces that read and writes only happen
 *	on addresses that are multiples of 4 (lw that reference addresses
 *	not multiples of 4 must be handled by the cpu and entail multiple
 *	reads to the memory)
 *
 **/
public class Memory{
	//A map that contains mappings from addresses to 32 bit words
	TreeMap<> addresses = new TreeMap<Long,Word>();
	//Reads 4 bytes from address until address+4
	public Word read(long address){
		if(address % 4 != 0){
			throw new RuntimeException("Referenced a memory address that is not a multiple of 4");
		}
		Word a1 = addresses.get(address); 
		if(a1 == null){
			addresses.put(address,new Word("000000000000000000"))
		}
		return null;

	}
	//Writes 4 bytes at address until address +4		
	public void write(long address){
		return null;
	}

}
