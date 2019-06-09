import java.util.Scanner;
import java.io.IOException;
public class Main{
	public static void main(String[] args)throws IOException{
		Utils.initiate();
		//System.out.println(Utils.hexToBinary);
		//System.out.println(Utils.binaryToHex);
		Scanner sc = new Scanner(System.in);
		Word word1 = new Word("7FFFFFFF");
		Word word2 = new Word("00000001");
		//Word word2 = new Word(sc.nextLine());
		MIPSMachine mips = new MIPSMachine();
		System.out.println("Word 1 in binary : "
		 + Utils.hexToBinary(word1.contents));
		System.out.println("Word 2 in binary : "
		 + Utils.hexToBinary(word2.contents));
		for(int i = 0 ; i < 32 ; i++){
			System.out.printf("%d",word1.getBit(i) - '0');
		}
		System.out.printf("\nAddition of the input words %s" ,mips.add(word1,word2));
	}
}
