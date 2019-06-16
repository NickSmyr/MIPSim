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
	String contents;
	public Word(String contents){
		if(!verifyWord(contents)) throw new RuntimeException(
				"Illegal word contents");
		this.contents = contents;
	}
	//Copycontructor
	public Word(Word other){
		if(!verifyWord(contents)) throw new RuntimeException(
				"Illegal word contents");
		this.contents = other.contents();
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
	//Returns amount of bits in the word
	public int size(){
		return contents.length();
	}
	/**
		Returns a new word with bits Word[i,j]
		i through j (inclusive)
		i is the msb and j lsb as in green card notation
		Word [26,16]
		TODO Test
	**/
	public Word bits(int i,int j){
		//Yea i know its confusing
		//But because i have decided that the words
		//Are going to be stored in MSB format
		//It is going to be more convenient for memory ops
		return new Word(contents.substring(contents.length()-1-i,contents.length()-1-j));
	}
	/**
		Rotates the word left by amt positions
		//TODO INVERSE INDEXES JESUSSSS
	**/
	public Word rotateRightLogical(long amt){
		int i = amount % size()();
  		return new Word(contents.substring(i) + contents.substring(0, i));
	}
	//TODO INVERSE I
	public Word rotateRightUnsigned(long amt){
		int i = amount % size()();
  		return new Word(contents.substring(i) + contents.substring(0, i));
	}
	/**
		Appends another word at the end of the current one
		//TODO Test
	**/
	public Word append(Word in){
		Stringbuilder result = new StringBuilder();	
		result.append(contents);
		result.append(in.contents);
		return new Word(result.toString());
	}

}
