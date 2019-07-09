//immutable
public class Word{
	/**
	 * Contents in hexadecimal form
	 * contents.length must be 8 ( words
	 * represent 32 bit data ).MSB
	 * that means the word is stored in the form 
	 * 0b x1x2x3x4 .... x28x29x30x31
	 *	contents[0] is the MSB x1
	 * contents[contents.length -1] is the LSB x31
	 */
	private String contents;
	public Word(String contents){
		if(!verifyWord(contents)) throw new RuntimeException(
				"Illegal word contents");
		this.contents = contents;
	}
	//Copy constructor
	public Word(Word other){
		if(!verifyWord(contents)) throw new RuntimeException(
				"Illegal word contents");
		this.contents = other.contents();
	}
	//Creates a word based on an integer value
	public Word(long value){
		contents = decimalToBinary(value);
		if(!verifyWord(contents)) throw new RuntimeException(
				"Illegal word contents");
	}
	/**
	 *	For the purpose of representing characters as Word objects
	 *	the arithmetic value of the character is stored in the Word
	 *	object with, so that no involvment with character encodings
	 *	is required 
	 **/
	public static final int CHARACTER_BITS = 16;
	/**
	 * Creates a word with CHARACTER_BITS amount of bits
	 * that represents the input character
	 **/
	public static Word createFromChar(char in){
		return new Word((int)in).zeroExtend(CHARACTER_BITS); 	
	}
	/** 
	 * Returns the character this word represents
	 **/
	public char getAsChar(){
		return (char)toUnsignedDecimal();
	}
	public boolean verifyWord(String word){
		return word.matches("[01]*");
	}
	//0 for LSB , 31 for MSB
	public char getBit(int bit){
		return contents.charAt(contents.length()-1-bit);
	}
	/**
		Returns the raw contents of the word as a String
	**/
	public String contents(){
		return contents;
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
	/*
		Returns an unsigned integer that
		is equal to the word in binary 
	*/
	public long toUnsignedDecimal(){
		long value = 0;
		long currentBitValue = 1;
		for(int i = 0; i < contents.length(); i++){
			if(getBit(i) == '1'){
				value += currentBitValue;
			}
			currentBitValue *=2;
		}
		return value;
	}
	/**
		Returns the equivalent number in binary format
		with the leftmost character being the MSB
	**/
	public static String decimalToBinary(long in){
		if(in == 0) return "0";
		StringBuilder result = new StringBuilder();
		while(in>0) {
			if(in % 2 == 1) result.append('1');
			else result.append('0');
			in /= 2;
		}
		return result.reverse().toString();
	}
	//Returns amount of bits in the word
	public int size(){
		return contents.length();
	}
	/**
		Returns a new word with bits Word[i,j]
		i through j (inclusive)
		i is the msb and j lsb as in green card notation
		Word [26,16]
	**/
	public Word bits(int i,int j){
		//Yea i know its confusing
		//But because i have decided that the words
		//Are going to be stored in MSB format
		//It is going to be more convenient for memory ops
		return new Word(contents.substring(contents.length()-1-i,contents.length()-1-j+1));
	}
	/**
		Rotates the word left by amt positions
		Bits that are removed from the right side
		are appended from the left side
	**/
	public Word shiftRightLogical(int amount){
		int i = amount % size();
  		return new Word(contents.substring(size()-i, size()) + contents.substring(0,size()-i));
	}
	/**
		Rotates the word left by amt positions
		Bits that are removed from the right side
		are appended from the left side
	**/
	public Word shiftLeftLogical(int amount){
		int i = amount % size();
  		return new Word( contents.substring(i,size()) + contents.substring(0, i));
	}
	/**
		Shifts the bits right by amount positions.
		Zeros are appended from the left so that
		the new word has the same amount of bits as 
		the old word
	**/
	public Word shiftRightUnsigned(int amount){
		int i = amount % size();
		StringBuffer zeros = new StringBuffer();
		for(int j = 0 ; j < i ; j++){
			zeros.append('0');
		}
		return new Word(zeros.toString()+contents.substring(0,size()-i));
	}
	/**
		Shifts the bits left by amount positions.
		Zeros are appended from the right so that
		the new word has the same amount of bits as 
		the old word
	**/
	public Word shiftLeftUnsigned(int amount){
		int i = amount % size();
		StringBuffer zeros = new StringBuffer();
		for(int j = 0 ; j < i ; j++){
			zeros.append('0');
		}
		return new Word(contents.substring(i,size()) + zeros.toString());
	}
	/**
		Appends another word at the end of the current one
	**/
	public Word append(Word in){
		StringBuilder result = new StringBuilder();	
		result.append(contents);
		result.append(in.contents);
		return new Word(result.toString());
	}
	/**
	* Adds enough bits from the left so that the result is a 32-bit word
	* If the leftmost bit is 1 , the added bits are all 1
	* If the leftmost bit is 0 , the added bits are all 0
	**/
	public Word signExtend(int numBits){
		int size = this.size();
		if(size >= 32){
			return new Word(this);
		}
		//Leftmost bit of input
		char MSBBit = '0';
		//Might be empty
		if(size>0 && this.getBit(size-1)=='1' ) MSBBit = '1';
		//Extending with MSBBit
		StringBuilder result = new StringBuilder();
		while(size < numBits){
			result.append(MSBBit);
			size++;
		}
		//Starting with MSB and moving to LSB
		for(int i = this.size()-1; i >= 0 ; i--){
			result.append(this.getBit(i));
		}
		return new Word(result.toString());
	}
	
	/**
	* Adds enough zero bits from the left so that the result is a word
	* with numBits bits
	**/
	public Word zeroExtend(int numBits){
		int size = this.size();
		if(size >= numBits){
			return new Word(this);
		}
		StringBuilder result = new StringBuilder();
		//Adding enough zeros from the left
		while(size < numBits){
			result.append('0');
			size++;
		}
		//Starting with MSB and moving to LSB
		for(int i = this.size()-1; i >= 0 ; i--){
			result.append(this.getBit(i));
		}
		return new Word(result.toString());
	}

	@Override
	public String toString(){
		//return String.valueOf(toUnsignedDecimal());
		return contents();
	}
}
