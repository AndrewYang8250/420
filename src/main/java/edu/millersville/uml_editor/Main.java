package edu.millersville.uml_editor;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

/**
 * 
 *
 */
public class Main 
{

///////////////////////////////////////////////////////////
//
//	Private Variables
//
///////////////////////////////////////////////////////////
	
	private static Map<String, Class> classMap = 
			new HashMap<String, Class>();
	private static Map<String, Relationships> relMap =
			new HashMap<String, Relationships>();
	private static Scanner console = new Scanner(System.in);

///////////////////////////////////////////////////////////
//
//	Main Method
//
///////////////////////////////////////////////////////////

    public static void main(String[] args) throws IOException
    {
        boolean loop = true;
        while(loop)
        {
        	// home menu
            int number = 0;

            System.out.println();
            System.out.println("1. Classes");
            System.out.println("2. Attributes");
            System.out.println("3. Relationships");
            System.out.println("4. List Classes/Attributes/Relationships");
            System.out.println("5. Create JSON file");
            System.out.println("6. Load from a JSON file");
            System.out.println("7. Exit the program");
            System.out.println();
            System.out.print("Please select a menu option: ");
            number = console.nextInt();
            
            
            switch(number){
                
            	// Class menu
            	case 1: 
                int classNum = 0;
                
                System.out.println();
                System.out.println("1. Add a class");
                System.out.println("2. Delete a class");
                System.out.println("3. Rename a class");
                System.out.println();
                System.out.print("What would you like to do with classes? ");
                classNum = console.nextInt();
                
                
                switch(classNum){
                    // Adding class
                	case 1:
                    System.out.println();
                    System.out.print("Enter the new class name: ");
                    String nameAdd = console.next();

                    createNewClass(nameAdd);
                    break;

                    // Deleting class
                    case 2:
                    String nameDel = "";
                    System.out.println();
                    System.out.print("Enter the class to delete: ");
                    nameDel = console.next();

                    deleteClass(nameDel);
                    break;
                    
                    // Renaming class
                    case 3:
                    String className = "";
                    String newName = "";
                    System.out.println();
                    System.out.print("Enter the class to rename: ");
                    className = console.next();
                    System.out.println();
                    System.out.print("Enter the new name for the class: ");
                    newName = console.next();

                    renameClass(className, newName);
                    break;
                    
                    // Error
                    default:
                    System.out.println();
                    System.out.print("That is not a menu option! Please try again.");
                    System.out.println();
                    break;
                }
                break;
                
                // Attribute menu
                case 2:
                int attrNum = 0;

                System.out.println();
                System.out.println("1. Add an attribute");
                System.out.println("2. Delete an attribute");
                System.out.println("3. Rename an attribute");
                System.out.println();
                System.out.print("What would you like to do with attributes? ");
                attrNum = console.nextInt();

                switch(attrNum){
                	// Add attribute
                    case 1:
                    String classAdd = "";
                    String attrAdd = "";
                    System.out.println();
                    System.out.print("Enter the class name for the attribute: ");
                    classAdd = console.next();
                    if(!classMap.containsKey(classAdd))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    else
                    {
                        Class classCall = classMap.get(classAdd);
                        System.out.print("Enter the name of the attribute to add: ");
                        attrAdd = console.next();
                        classCall.addNewAttribute(attrAdd);
                    }
                    break;
                
                    // Delete attribute
                    case 2:
                    String classDel = "";
                    String attrDel = "";
                    System.out.println();
                    System.out.print("Enter the class name for the attribute: ");
                    classDel = console.next();
                    if(!classMap.containsKey(classDel))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    else 
                    {
                        Class classCall = classMap.get(classDel);
                        System.out.print("Enter the name of the attribute to delete: ");
                        attrDel = console.next();
                        classCall.deleteAttribute(attrDel);
                    }
                    break;

                    
                    // Rename attribute
                    case 3: 
                    String classRen = "";
                    String attrOld = "";
                    String attrNew = "";
                    System.out.println();
                    System.out.print("Enter the class for the attribute: ");
                    classRen = console.next();
                    if(!classMap.containsKey(classRen))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    else 
                    {
                        Class classCall = classMap.get(classRen);
                        System.out.print("Enter the current name for the attribute: ");
                        attrOld = console.next();
                        System.out.print("Enter the new name for the attribute: ");
                        attrNew = console.next();
                        classCall.renameAttribute(attrOld, attrNew);
                    }
                    break;

                    // Error
                    default:
                    System.out.println();
                    System.out.print("That is not a menu option! Please try again.");
                    System.out.println();
                    break;
                }
                break;

                // Relationship menu
                case 3:
                int relNum = 0;

                System.out.println();
                System.out.println("1. Add a relationship");
                System.out.println("2. Delete a relationship");
                System.out.println("3. Rename a relationship");
                System.out.println();
                System.out.print("What would you like to do with relationships? ");
                relNum = console.nextInt();

                switch(relNum){
                	// Add relationship
                    case 1:
                    String sourceAdd = "";
                    String destAdd = "";
                    String ID = "";
                    System.out.println();
                    System.out.print("Enter the source of the relationship: ");
                    sourceAdd = console.next();
                    System.out.print("Enter the destination of the relationship: ");
                    destAdd = console.next();
                    if(!classMap.containsKey(sourceAdd))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    else if(!classMap.containsKey(destAdd))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    else 
                    {
                        System.out.print("Enter an ID for the relationship: ");
                        ID = console.next();
                        createRelationship(sourceAdd, destAdd, ID);
                    }
                    break;
                    
                    // Delete relationship
                    case 2:
                    String delID = "";
                    System.out.println();
                    System.out.print("Enter the ID of the relationship: ");
                    delID = console.next();
                    if(!relMap.containsKey(delID))
                    {
                        System.out.println("There is not a relationship with this ID.");
    		            break;
                    }
                    deleteRelationship(delID);
                    break;

                    // Error
                    default:
                    System.out.println();
                    System.out.print("That is not a menu option! Please try again.");
                    System.out.println();
                    break;
                }
                break;

                // List Classes menu
                case 4:
                int listNum = 0;

                System.out.println();
                System.out.println("1. List classes");
                System.out.println("2. List attributes");
                System.out.println("3. List relationships");
                System.out.println();
                System.out.print("What would you like to list? ");
                listNum = console.nextInt();

                switch(listNum){
                	// List classes
                    case 1:

                    System.out.println();
                    printClasses();
                    break;

                    // List attributes
                    case 2:

                    String listAttr = "";
                    System.out.println();
                    System.out.print("Enter the class name: ");
                    listAttr = console.next();
                    if(!classMap.containsKey(listAttr))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    else 
                    {
                        Class classCall = classMap.get(listAttr);
                        System.out.println();
                        System.out.println(listAttr);
                        classCall.printAttr();
                    }
                    break;

                    // List relationships
                    case 3: 
                    
                    //***LIST RELATIONSHIPS HERE  ***/
                    System.out.println();
                    break;

                    // Error
                    default:
                    System.out.println();
                    System.out.print("That is not a menu option! Please try again.");
                    System.out.println();
                    break;
                }
                break;

                // Save JSON menu
            	case 5: 
                
                System.out.println();        
            	// filepath + new file name
            	// example: C:/Millersville/2020-2021/420/example.json
            	System.out.println("Enter filepath (filepath+filename): ");
            	String filename = console.next();
            	File testFile = new File(filename);
            	if(testFile.exists()) {
            		saveJSON(filename, classMap);
            		System.out.println();
            		System.out.println("JSON file saved to: " + filename);
            		System.out.println(classMap.toString());
            	} else {
            		System.out.println("No such file exists. Please enter filepath again.");
            	}
            	
                
            	break;
                
                
                // Load JSON menu
            	case 6: 
                
                System.out.println();        
                
            	// filepath + new file name
            	// example: C:/Millersville/2020-2021/420/example.json
              	System.out.println("Enter filepath (filepath+filename): ");
            	String filepath = console.next();
            	File jsonFile = new File(filepath);
            	if (jsonFile.exists()) {
            		System.out.println(loadJSON(filepath));
            		System.out.println();
            	} else {
            		System.out.println("No such file exists. Please enter filepath again.");
            	}
            	
                break;
               
                // Error
                case 7:
                loop = false;
                break;

                default:
                System.out.println();
                System.out.print("That is not a menu option! Please try again.");
                System.out.println();
                break;
            }
        }
    	
    	classMap.toString();
    }
 
    
///////////////////////////////////////////////////////////
//
//	createClass
//
///////////////////////////////////////////////////////////

    public static void createNewClass(String className) 
    {
    	if (classMap.containsKey(className))
    	{
    		System.out.println("There is already a class with that name.");
    		return;
    	}
    	
    	classMap.put(className, new Class());
	    
	System.out.println();
        System.out.print("The class has been added!");
        System.out.println();
    }
  
///////////////////////////////////////////////////////////
//
//	renameClass
//
///////////////////////////////////////////////////////////
  
    public static void renameClass(String name, String newName)
    {

       	if (classMap.containsKey(newName))
    	{
    		System.out.println("There is an class with the new name.");
    		return;
    	}

        if(!classMap.containsKey(name))
        {
           System.out.println("There is not an existing class with the name: " + name + ".");
    		return; 
        }

        classMap.put(newName, classMap.get(name));
        classMap.remove(name);
	    
	System.out.println();
        System.out.print("The class has been renamed!");
        System.out.println();
    }
  
///////////////////////////////////////////////////////////
//
//	deleteClass
//
///////////////////////////////////////////////////////////

    public static void deleteClass(String name)
    {

        if (!classMap.containsKey(name))
    	{
    		System.out.println("There is not a class with that name.");
    		return;
    	}
        classMap.get(name).deleteAttributes();
        classMap.remove(name);
	    
	System.out.println();
        System.out.print("The class has been deleted!");
        System.out.println();
    }

//////////////////////////////////////////////////////////
//
//	createRelationship
//
///////////////////////////////////////////////////////////
    public static void createRelationship(String class1, String class2, String ID )
    {

        //checks to make sure the relationship is not already created
        if(relMap.containsKey(ID))
        {
            System.out.println();
            System.out.println("This relationship already exists");
            return;
        }
        //create temp class to be able to create relationship
        Class source = classMap.get(class1);
        Class destination = classMap.get(class2);;

        relMap.put(ID, new Relationships(source, destination, ID)); 
	    
	System.out.println();
        System.out.print("The relationship has been added!");
        System.out.println();
    }

//////////////////////////////////////////////////////////
//
//	deleteRelationship
//
///////////////////////////////////////////////////////////

public static void deleteRelationship(String ID)
{
    if (!relMap.containsKey(ID))
    	{
    		System.out.println("There is not a relationship with that ID.");
    		return;
    	}
    relMap.remove(ID); 
    System.out.println();
    System.out.print("The relationship has been deleted!");
    System.out.println();
}
    
///////////////////////////////////////////////////////////
//
//	printClass
//
///////////////////////////////////////////////////////////
      
   public static void printClass(String className) {
       System.out.println(className);     
       classMap.get(className).printAttr();
    }
///////////////////////////////////////////////////////////
//
//	saveJSON(String, Map<String, Class>)
//
//	function that creates and saves classMap to a JSON file using 
//	a prompted file name and the classMap.
//
///////////////////////////////////////////////////////////

    public static void saveJSON(String name, Map<String, Class> map) throws IOException{
    	//converts map into JSON object
    	JSONObject jsonMap = new JSONObject(map);
    	String s = map.toString();
    	// writing map to JSON file
    	try {
    		FileWriter file = new FileWriter(name);
    		file.write(s.toString());
    		file.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    
    }
    
///////////////////////////////////////////////////////////
//
// loadJSON(String, Map<String, Class>)
//
// function that loads a JSON file using 
// a prompted filepath.
//
///////////////////////////////////////////////////////////
    
    public static String loadJSON(String filepath) throws IOException, FileNotFoundException {
        // added JAR file
    	return FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);   	
    	
    }
    
///////////////////////////////////////////////////////////
//
// toString()
//
// function that creates a string of the map. 
// Look at the Class toString().
//
///////////////////////////////////////////////////////////
    
    @Override
    public String toString() {
    	StringBuffer s = new StringBuffer();
    	s.append("{");
    	s.append("\n");
    	// streams the map and calls Class.toString()
    	classMap.values().stream().map(Class::toString);
    	s.append("}");
    	return s.toString();
    }
     

    public static void printClasses() {
        for (String key : classMap.keySet()) 
    	{
            printClass(key);
    	}
    }

}