public class Memory{
	byte[][] instructions;
	//Reads 4 bytes from address until address+4
	public byte[] read(long address){return null;}
	//Writes 4 bytes at address until address +4		
	public byte[] write(long address){return null;}

	////////DEBUG
	//Allocates 16MB of memory for testing purposes
	byte[][] mem;
	public void prototypeInit(){
		mem = new byte[1<<22][4];
		System.err.printf("Memory size1 = %d\n",mem.length);
		System.err.printf("Memory size2 = %d\n",mem[0].length);
	}
}
