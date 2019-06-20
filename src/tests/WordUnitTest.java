import java.lang.reflect.*;
public class WordUnitTest {
	/**
		All unit test methods must have the characters "Test"
		in them
	**/
	public boolean bitsTest(){
		Word a1 = new Word("001010111111");
		Word a2 = a1.bits(a1.size()-3,0);
		return a2.contents().equals("1010111111");
	}
	public boolean shiftRightLogicalTest(){
		Word a1 = new Word("1001");
		Word a2 = a1.shiftRightLogical(1);
		return a2.contents().equals("1100");
	}
	public boolean shiftLeftLogicalTest(){
		Word a1 = new Word("1001");
		Word a2 = a1.shiftLeftLogical(1);
		return a2.contents().equals("0011");
	}
	public boolean shiftRightUnsignedTest(){
		Word a1 = new Word("1001");
		Word a2 = a1.shiftRightUnsigned(1);
		return a2.contents().equals("0100");
	}
	public boolean shiftLeftUnsignedTest(){
		Word a1 = new Word("1001");
		Word a2 = a1.shiftLeftUnsigned(1);
		return a2.contents().equals("0010");
	}
	public boolean appendTest(){
		Word a1 = new Word("1001");
		Word a2 = new Word("10001");
		return a1.append(a2).contents().equals("100110001");
	}
	public void runAll(){
		System.out.println("It works");
		System.out.println(getClass());
		Class<?> c = getClass();
		Method[] methods = c.getMethods();
		boolean unitTestsPassed = true;
		for(int i = 0 ; i < methods.length ; i++){
			if(methods[i].getName().matches("[\\w]*Test[\\w]*")){
				System.out.println("Found method " + methods[i]);
				try{
					if(!(boolean)methods[i].invoke(this)){
						unitTestsPassed = false;
						System.out.println("[X] Failed To pass unit test " + methods[i].getName());
					}
				}
				catch(Exception e){
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		if(unitTestsPassed){
			System.out.println("All unit tests Passed!");
		}
	}
	public static void main(String[] args){
		new WordUnitTest().runAll();
	}
	
}
