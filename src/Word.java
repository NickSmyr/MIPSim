public class Word{
	/**
	 * Contents in hexadecimal form
	 * contents.length must be 8 ( words
	 * represent 32 bit data ).MSB
	 */
	String contents;
	public Word(String contents){
		if(!verifyWord(contents)) throw new RuntimeException(
				"Illegal word contents");
		this.contents = contents;
	}
	public boolean verifyWord(String word){
		return word.matches("[0-9A-F]{8}");
	}
	//0 for MSB , 31 for LSB
	public char getBit(int bit){
		// bit = 4 * hexIndex + offset
		//The 4 bbits that contain the desired bit
		int hexIndex = bit / 4;
		char hexChar =contents.charAt(hexIndex);
		//The offset within those 4 bits
		int offset = bit % 4;
		//Result bit is one or zero
		char result = Utils.hexToBinary(hexChar).charAt(offset);	
		if(!(result == '0' || result == '1'))
		 throw new RuntimeException("Found invalid bit");
		return result;
	}

	
	public String toBinaryString(){
		return Utils.hexToBinary(contents);
	}
	public String toString(){
		return contents;
	}
	

}
