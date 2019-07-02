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
import java.util.TreeMap;
public class Memory{
	//A map that contains mappings from addresses to 32 bit words
	TreeMap<Long,Word> addresses = new TreeMap<Long,Word>();
	//Reads 4 bytes from address until address+4
	//TODO Test
	public Word read(long address){
		if(!validateAddress(address)){
			throw new RuntimeException("Referenced a memory address that is not a multiple of 4");
		}
		int offset = (int)address % 4;
		long allignedAddress = address - offset;

		Word a1 = addresses.get(allignedAddress); 
		Word a2 = addresses.get(allignedAddress + 4); 
		if(a1 == null){
			addresses.put(allignedAddress,new Word("00000000000000000000000000000000"));
			a1 = addresses.get(allignedAddress);
		}
		if(a2 == null){
			addresses.put(allignedAddress + 4,new Word("00000000000000000000000000000000"));
			a2 = addresses.get(allignedAddress+4);
		}
		
		Word res1 = a1.bits(32 - offset*8 - 1,0);
		Word res2 = new Word("");
		//Unalligned address
		if(offset > 0){
			res2 = a2.bits(a2.size() - 1,a2.size() - offset*8 );

		}
		return res1.append(res2);
	}
	//Writes 4 bytes at address until address +4		
	//TODO Test (of by 1 errors)...
	public void write(long address, Word data){
		if(!validateAddress(address)){
			throw new RuntimeException("Referenced a memory address that is not a multiple of 4");
		}
		int offset = (int)address % 4;
		long allignedAddress = address - offset;

		Word a1 = addresses.get(allignedAddress); 
		Word a2 = addresses.get(allignedAddress + 4); 
		if(a1 == null){
			addresses.put(allignedAddress,new Word("00000000000000000000000000000000"));
			a1 = addresses.get(allignedAddress);
		}
		if(a2 == null){
			addresses.put(allignedAddress+4,new Word("00000000000000000000000000000000"));
			a2 = addresses.get(allignedAddress+4);
		}
		//Bits that are kept the same at allignedAddress
		Word head = new Word("");
		if(offset > 0) head = head.append(a1.bits(31, 31 - offset*8));
		head = head.append(data.bits(31,offset *8));
		//Bits that are kept the same at allignedAddress+4
		Word tail = a2;
		if(offset > 0) tail=tail.bits(31 - offset*8 ,0);
		tail = data.bits(offset*8 -1 , 0).append(tail);

		addresses.put(allignedAddress,head);
		addresses.put(allignedAddress+4,tail);
		return;
	}
	public boolean validateAddress(long address){
		return address >=0;
	}
}
