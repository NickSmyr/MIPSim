public class UnitTests{
	public static String[] classes = { "Word" , "ALU"};
	public static void main(String[] args){
		runAllTests();
	}
	public static void runTests(Class<?> targetClass){
	}
	public static void runAllTests(){
		for(String c : classes){
			try{
				System.out.println(Class.forName(c));
			}
			catch(ClassNotFoundException e){
				System.out.println("Oops didn't find class " + c);
				System.out.println(e.getMessage());
			}
		}
	}
}
