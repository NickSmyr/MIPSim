import java.io.File;
import java.lang.reflect.*;
public class UnitTests{
	public static String[] classes = { "Word" , "ALU"};
	public static void loadTestClasses(){
		File dir = new File("./");
		String[] currentDirFiles = dir.list();
		int j = 0;
		for(int i = 0 ; i < currentDirFiles.length;i++){
			if(currentDirFiles[i].matches("[\\w]*UnitTest[\\w]*.class") &&
				!(currentDirFiles[i].equals("UnitTests.class"))){
				j++;
			}	
		}
		classes = new String[j];
		j = 0;
		for(String f : currentDirFiles){
			if(f.matches("[\\w]*UnitTest[\\w.]*.class") &&
				!f.equals("UnitTests.class")){
				classes[j] = f.substring(0,f.length()-6);
				j++;
			}
		}	
	}
	public static void main(String[] args){
		loadTestClasses();
		runAllTests();
	}
	public static void runTests(Class<?> c){
		Method[] methods = c.getMethods();
		Constructor<?>[] constructors = c.getConstructors();
		Constructor<?> con = constructors[0];
		Object classInstance;
		try{
			classInstance = con.newInstance(); 
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Failed to instantiate class" + c.getName() + "for unit testing.");
			return;
		}
		boolean unitTestsPassed = true;
		for(int i = 0 ; i < methods.length ; i++){
			if(methods[i].getName().matches("[\\w]*Test[\\w]*")){
				System.out.println("Found method " + methods[i]);
				try{
					if(!(boolean)methods[i].invoke(classInstance)){
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
	public static void runAllTests(){
		for(String c : classes){
			try{
				Class<?> currentClass = Class.forName(c);
				System.out.println("RUNNING UNIT TESTS FOR CLASS : "+ currentClass.getName()+ "\n");
				runTests(currentClass);
				System.out.println();
			}
			catch(ClassNotFoundException e){
				System.out.println("Oops didn't find class " + c);
				System.out.println(e.getMessage());
			}
		}
	}
}
