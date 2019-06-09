import java.util.Scanner;
import java.io.IOException;
public class Main{
	public static void main(String[] args)throws IOException{
		Utils.initiate();
		//System.out.println(Utils.hexToBinary);
		//System.out.println(Utils.binaryToHex);
		Scanner sc = new Scanner(System.in);
		Word word1 = new Word(sc.nextLine());
		Word word2 = new Word(sc.nextLine());
		MIPSMachine mips = new MIPSMachine();
		System.out.println(mips.add(word1,word2));
	}
}
