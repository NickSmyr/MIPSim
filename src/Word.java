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
		return word.matches("[01]*");
	}
	//0 for LSB , 31 for MSB
	public char getBit(int bit){
		return contents.charAt(contents.length()-1-bit);
	}

	
	public String toBinaryString(){
		return Utils.hexToBinary(contents);
	}
	public String toHexString(){
		if(contents.length()%4!=0){
			return "unhexableWord";
		}
		else{
			return Utils.binaryToHex(contents);
		}
	}
	//TODO Binary to int (Signed or unsigned)
	public String toDecimalString(){
		long value = 0;
		long currentBitValue = 1;
		for(int i = 0; i < contents.length(); i++){
			if(getBit(i) == '1'){
				value += currentBitValue;
			}
			currentBitValue *=2;
		}
		return String.valueOf(value);
	}

}
