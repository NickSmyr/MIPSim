import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
public class Utils{
	public static HashMap<String,String> hexToBinary = new HashMap<>();
	public static HashMap<String,String> binaryToHex = new HashMap<>();

	public static void initiate(){
		try{
			Scanner sc = new Scanner(new File("data/hextobinary.config"));
			while(sc.hasNextLine()){
				//Each line is in the forrm bbbb - x
				String[] a = sc.nextLine().split("\\s");
				binaryToHex.put(a[0],a[1]);
				hexToBinary.put(a[1],a[0]);
			}	
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	} 
	public static String hexToBinary(String in){
		String result = "";
		for(char i : in.toCharArray()){
			result+=hexToBinary(i);
		}
		return result;
	}
	public static String hexToBinary(char in){
		return hexToBinary.get(String.valueOf(in));
	}
	
}
