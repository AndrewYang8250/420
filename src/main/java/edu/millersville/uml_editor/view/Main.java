package edu.millersville.uml_editor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.millersville.uml_editor.model.*;
import edu.millersville.uml_editor.controller.*;
import edu.millersville.uml_editor.view.*;

import javax.lang.model.util.ElementScanner6;

//import org.apache.commons.io.FileUtils;

//import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static UMLController controller;
    private static UMLModel model;
    public Main(UMLModel m) {
        this.model = m;
        this.controller = null;
    }
	
    private static Map<String, ClassObject> classMap = 
			new HashMap<String, ClassObject>() {
		@Override
		public java.lang.String toString() {
	    	StringBuffer s = new StringBuffer();
	    	s.append("{");
	    	s.append(System.lineSeparator());
	    	boolean firstRow = true;
	    	for (String key: classMap.keySet()) {
	    		if (firstRow) {
	    			firstRow = false;

	    		} else {
	    			s.append(",\n");
	    		}
	    		s.append("\"" + key + "\" : ");
	    		s.append("{");
	    		s.append(System.lineSeparator());
	    		s.append(classMap.get(key).toString());
	    		s.append("}");
	    	}
	    	s.append("\n");
	    	s.append("}\n");
	    	System.out.println(s.toString());
	    	return s.toString();
	    }
	};
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
        //Loop that controls the entire program
        while(loop)
        {
            int number = 0;

        //Display menu
            System.out.println();
            System.out.println("1. Classes");
            System.out.println("2. Methods");
            System.out.println("3. Fields");
            System.out.println("4. Relationships");
            System.out.println("5. List Classes/Attributes/Relationships");
	        System.out.println("6. Create JSON file");
            System.out.println("7. Load from a JSON file");
            System.out.println("8. Help");
            System.out.println("9. Exit the program");
            System.out.println();
            System.out.print("Please select a menu option: ");
            
            boolean isNumber = false;
            
            //****While loop that considers string input...repeated throughout the program for each switch statement****\\
            while (!isNumber)
            {
            	String temp = console.next();
            	try {
            		number = Integer.parseInt(temp);
            		isNumber = true;
            	}
            	catch (NumberFormatException ex) {
            		System.out.println();
            		System.out.println("This is not a number. Use a number when selecting a menu option.");
            		System.out.println();
            		System.out.print("Please select a menu option: ");
            	}
            }
            
            switch(number){
                //Case for Classes
                case 1: 
                int classNum = 0;

                //Class menu
                System.out.println();
                System.out.println("1. Add a class");
                System.out.println("2. Delete a class");
                System.out.println("3. Rename a class");
                System.out.println("4. Go back to main menu");
                System.out.println();
                System.out.print("What would you like to do with classes? ");
                
                isNumber = false;
                
                while (!isNumber)
                {
                	String temp = console.next();
                	try {
                		classNum = Integer.parseInt(temp);
                		isNumber = true;
                	}
                	catch (NumberFormatException ex) {
                		System.out.println();
                		System.out.println("This is not a number. Use a number when selecting a menu option.");
                		System.out.println();
                		System.out.print("Please select a menu option: ");
                	}
                }

                //Switch statement within the class menu
                switch(classNum){
                    //Asks for class name and adds
                    case 1:
                    System.out.println();
                    System.out.print("Enter the new class name: ");
                    String nameAdd = console.next();


                    createNewClassCLI(nameAdd);
                    break;

                    //Asks for class name and deletes
                    case 2:
                    String nameDel = "";
                    System.out.println();
                    System.out.print("Enter the class to delete: ");
                    nameDel = console.next();

                    deleteClass(nameDel);
                    break;

                    //Asks for class name, new class name and renames
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
                    
                    case 4:
                    break;
                    
                    //Default case that sends user back to main menu if a number not on the menu is entered
                    default:
                    System.out.println();
                    System.out.print("That is not a menu option! Please try again.");
                    System.out.println();
                    break;
                }
                break;

                //Case for Methods
                case 2:
                int methodNum = 0;

                //Attribute menu
                System.out.println();
                System.out.println("1. Add a method");
                System.out.println("2. Delete a method");
                System.out.println("3. Rename a method");
                System.out.println("4. Parameters");
                System.out.println("5. Go back to main menu");
                System.out.println();
                System.out.print("What would you like to do with methods? ");
                
                isNumber = false;
                
                while (!isNumber)
                {
                	String temp = console.next();
                	try {
                		methodNum = Integer.parseInt(temp);
                		isNumber = true;
                	}
                	catch (NumberFormatException ex) {
                		System.out.println();
                		System.out.println("This is not a number. Use a number when selecting a menu option.");
                		System.out.println();
                		System.out.print("Please select a menu option: ");
                	}
                }

                //Switch statement within attribute menu
                switch(methodNum){
                    case 1:
                    String classAdd = "";
                    String methodAdd = "";
                    String methodType = "";
                    System.out.println();
                    System.out.print("Enter the class name for the method: ");
                    classAdd = console.next();
                    //Checks to see if the class name exists
                    if(!classMap.containsKey(classAdd))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    //If it does, prompts for attribute name and adds
                    else
                    {
                        ClassObject classCall = classMap.get(classAdd);
                        System.out.print("Enter the name of the method to add: ");
                        methodAdd = console.next();
                        System.out.print("Enter the type of the method: ");
                        methodType = console.next();
                        classCall.addMethod(methodAdd, methodType);
                    }
                    break;
                    
                    case 2:
                    String classDel = "";
                    String methodDel = "";
                    System.out.println();
                    System.out.print("Enter the class name for the method: ");
                    classDel = console.next();
                    //Checks to see if the class exists
                    if(!classMap.containsKey(classDel))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    //If it does, ask for attribute name and delete
                    else 
                    {
                        ClassObject classCall = classMap.get(classDel);
                        System.out.print("Enter the name of the method to delete: ");
                        methodDel = console.next();
                        
                        Method renameMethod = classCall.getMethod(methodDel);
                        if (renameMethod == null)
                        {
                        	System.out.println("The method does not exist.");
                        	break;
                        }
                        
                        classCall.deleteMethod(methodDel);
                    }
                    break;

                    case 3: 
                    String classRen = "";
                    String methodOld = "";
                    String methodNew = "";
                    System.out.println();
                    System.out.print("Enter the class for the method: ");
                    classRen = console.next();
                    //Checks to see if the class exists
                    if(!classMap.containsKey(classRen))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    //If it does, ask for current and new name for attribute and renames
                    else 
                    {
                        ClassObject classCall = classMap.get(classRen);
                        System.out.print("Enter the current name for the method: ");
                        methodOld = console.next();
                        
                        Method renameMethod = classCall.getMethod(methodOld);
                        if (renameMethod == null)
                        {
                        	System.out.println("The method does not exist.");
                        	break;
                        }
                        
                        System.out.print("Enter the new name for the method: ");
                        methodNew = console.next();
                        classCall.renameMethod(methodOld, methodNew);
                    }
                    
                    break;
                    
                    case 4:
	                
                    break;
                    
                    case 5:
                    break;

                    //Default case that sends user back to main menu if a number not on the menu is entered
                    default:
                    System.out.println();
                    System.out.print("That is not a menu option! Please try again.");
                    System.out.println();
                    break;
                }
                break;
                
                // Case for Fields 
                case 3:
            
            	int fieldNum = 0;

                //Attribute menu
                System.out.println();
                System.out.println("1. Add a field");
                System.out.println("2. Delete a field");
                System.out.println("3. Rename a field");
                System.out.println("4. Change a field type");
                System.out.println("5. Go back to main menu");
                System.out.println();
                System.out.print("What would you like to do with fields? ");
                
                isNumber = false;
                
                while (!isNumber)
                {
                	String temp = console.next();
                	try {
                		fieldNum = Integer.parseInt(temp);
                		isNumber = true;
                	}
                	catch (NumberFormatException ex) {
                		System.out.println();
                		System.out.println("This is not a number. Use a number when selecting a menu option.");
                		System.out.println();
                		System.out.print("Please select a menu option: ");
                	}
                }

                //Switch statement within field menu
                switch(fieldNum){
                    case 1:
                    String classAdd = "";
                    String fieldAdd = "";
                    String fieldType = "";
                    System.out.println();
                    System.out.print("Enter the class name for the field: ");
                    classAdd = console.next();
                    //Checks to see if the class name exists
                    if(!classMap.containsKey(classAdd))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    //If it does, prompts for attribute name and adds
                    else
                    {
                        ClassObject classCall = classMap.get(classAdd);
                        System.out.print("Enter the name of the field to add: ");
                        fieldAdd = console.next();
                        System.out.print("Enter the type of the field: ");
                        fieldType = console.next();
                        classCall.addField(fieldAdd, fieldType);
                    }
                    break;
                    
                    case 2:
                    String classDel = "";
                    String fieldDel = "";
                    System.out.println();
                    System.out.print("Enter the class name for the field: ");
                    classDel = console.next();
                    //Checks to see if the class exists
                    if(!classMap.containsKey(classDel))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    //If it does, ask for field name and delete
                    else 
                    {
                        ClassObject classCall = classMap.get(classDel);
                        System.out.print("Enter the name of the field to delete: ");
                        fieldDel = console.next();
                        
                        Field renamefield = classCall.getField(fieldDel);
                        if (renamefield == null)
                        {
                        	System.out.println("The field does not exist.");
                        	break;
                        }
                        
                        classCall.deleteField(fieldDel);
                    }
                    break;

                    case 3: 
                    String classRen = "";
                    String fieldOld = "";
                    String fieldNew = "";
                    System.out.println();
                    System.out.print("Enter the class for the field: ");
                    classRen = console.next();
                    //Checks to see if the class exists
                    if(!classMap.containsKey(classRen))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    //If it does, ask for current and new name for attribute and renames
                    else 
                    {
                        ClassObject classCall = classMap.get(classRen);
                        System.out.print("Enter the current name for the field: ");
                        fieldOld = console.next();
                        
                        Field renamefield = classCall.getField(fieldOld);
                        if (renamefield == null)
                        {
                        	System.out.println("The field does not exist.");
                        	break;
                        }
                        
                        System.out.print("Enter the new name for the field: ");
                        fieldNew = console.next();
                        classCall.renameField(fieldOld, fieldNew);
                    }
                    
                    break;
                    
                    case 4:
	                String className = "";
	                String fieldName = "";
	                String newType = "";
	                System.out.println();
	                System.out.print("Enter the class for the field: ");
	                classRen = console.next();
	                //Checks to see if the class exists
	                if(!classMap.containsKey(classRen))
	                {
	                	System.out.println("There is not a class with this name.");
	                	break;
                    }
                    //If it does, ask for current and new name for attribute and renames
                    else 
                    {
                        ClassObject classCall = classMap.get(classRen);
                        System.out.print("Enter the current name for the field: ");
                        fieldName = console.next();

                        Field typeField = classCall.getField(fieldName);
                        if (typeField == null)
                        {
                        	System.out.println("The field does not exist.");
                        	break;
                        }
                        
                        System.out.print("Enter the new type for the field: ");
                        newType = console.next();
                        
                        classCall.changeFieldType(fieldName, newType);
                    }
                    break;
                    
                    case 5:
                    break;

                    //Default case that sends user back to main menu if a number not on the menu is entered
                    default:
                    System.out.println();
                    System.out.print("That is not a menu option! Please try again.");
                    System.out.println();
                    break;
                }
                
                break;

                //Case for Relationships
                case 4:
                int relNum = 0;

                //Relationship menu
                System.out.println();
                System.out.println("1. Add a relationship");
                System.out.println("2. Delete a relationship");
                System.out.println("3. Change Relationship Type");
                System.out.println("4. Go back to main menu");
                System.out.println();
                System.out.print("What would you like to do with relationships? ");
                
                isNumber = false;
                
                while (!isNumber)
                {
                	String temp = console.next();
                	try {
                		relNum = Integer.parseInt(temp);
                		isNumber = true;
                	}
                	catch (NumberFormatException ex) {
                		System.out.println();
                		System.out.println("This is not a number. Use a number when selecting a menu option.");
                		System.out.println();
                		System.out.print("Please select a menu option: ");
                	}
                }

                //Switch statement within relationship menu
                switch(relNum){
                    //Case to add relationship
                    case 1:
                    String sourceAdd = "";
                    String destAdd = "";
                    String ID = "";
                    System.out.println();
                    System.out.print("Enter the source of the relationship: ");
                    sourceAdd = console.next();
                    System.out.print("Enter the destination of the relationship: ");
                    destAdd = console.next();
                    //If statements that check to see if the classes entered for source and destination exist
                    if(!classMap.containsKey(sourceAdd))
                    {
                        System.out.println("There is not a class with the source name.");
    		            break;
                    }
                    else if(!classMap.containsKey(destAdd))
                    {
                        System.out.println("There is not a class with the destination name.");
    		            break;
                    }
                    //If they do, ask for ID and adds relationship
                    else 
                    {
                        System.out.print("Enter an ID for the relationship: ");
                        ID = console.next();

                        System.out.print("Please enter A, C, I, or R for the type: ");
                        String newType = console.next();
                        if(newType.equals("A") || newType.equals("C") || newType.equals("I") || newType.equals("R"))
                            createRelationship(sourceAdd, destAdd, ID, newType);
                        else
                        {
                         	System.out.println();
                        	System.out.println("This is not a proper type");
                         	break;
                        }
                    }
                    break;
                    
                    //Case to delete relationship
                    case 2:
                    String delID = "";
                    System.out.println();
                    System.out.print("Enter the ID of the relationship: ");
                    delID = console.next();
                    //Checks if ID exists
                    if(!relMap.containsKey(delID))
                    {
                        System.out.println("There is not a relationship with this ID.");
    		            break;
                    }
                    //If it does, delete relationship
                    deleteRelationship(delID);
                    break;
                    
                    case 3:
                    System.out.print("Enter the relationship ID: ");
                    String relID = console.next();

                    if(!relMap.containsKey(relID))
                    {
                        System.out.println("There is not a relationship with this ID.");
    		            break;
                    }
                    System.out.print("Please enter: Aggregation, Composition, Inheritance, Realization: ");
                    String newType = console.next();
                    if(newType.equals("Aggregation") || newType.equals("Composition") || newType.equals("Inheritance") || newType.equals("Realization"))
                        changeRelationshipType(relID, newType);
                        else 
                        {
                            System.out.println();
                            System.out.println("This is not a proper type");
                            break;
                        }

                    case 4:
                    break;

                    //Default case that sends user back to main menu if a number not on the menu is entered
                    default:
                    System.out.println();
                    System.out.print("That is not a menu option! Please try again.");
                    System.out.println();
                    break;
                }
                break;

                //Case to list data
                case 5:
                int listNum = 0;

                //List menu
                System.out.println();
                System.out.println("1. List classes");
                System.out.println("2. List attributes");
                System.out.println("3. List relationships");
                System.out.println("4. Go back to main menu");
                System.out.println();
                System.out.print("What would you like to list? ");
                
                isNumber = false;
                
                while (!isNumber)
                {
                	String temp = console.next();
                	try {
                		listNum = Integer.parseInt(temp);
                		isNumber = true;
                	}
                	catch (NumberFormatException ex) {
                		System.out.println();
                		System.out.println("This is not a number. Use a number when selecting a menu option.");
                		System.out.println();
                		System.out.print("Please select a menu option: ");
                	}
                }

                //Switch statement with the list option
                switch(listNum){
                    //List classes with their attributes
                    case 1:
                    System.out.println();
                    printClasses();
                    break;

                    //List a specific class with its attributes
                    case 2:
                    String listAttr = "";
                    System.out.println();
                    System.out.print("Enter the class name: ");
                    listAttr = console.next();
                    //Checks to see if the class exists
                    if(!classMap.containsKey(listAttr))
                    {
                        System.out.println("There is not a class with this name.");
    		            break;
                    }
                    //If it does, print it and its attributes
                    else 
                    {
                        System.out.println();
                        System.out.println("Class Name: " + listAttr);
                        System.out.print("Attributes: ");
                        classMap.get(listAttr).printAttr();
                    }
                    break;

                    //List relationships
                    case 3: 
                    System.out.println();
                    listRelationships();
                    break;
                    
                    case 4:
                    break;

                    //Default case that sends user back to main menu if a number not on the menu is entered
                    default:
                    System.out.println();
                    System.out.println("That is not a menu option! Please try again.");
                    break;
                }
                break;
		
                //Save JSON case
		        case 6:
		        System.out.println();   
            	System.out.print("Enter filepath (filepath+filename): ");
            	String filename = console.next();
            	saveJSON(filename, classMap);
            	System.out.println();
            	System.out.println("JSON file saved to: " + filename);
            	break;
			    
                //Load JSON case
		        case 7:
		        System.out.println();        
              	System.out.println("Enter filepath (filepath+filename) of file to open: ");
            	String filepath = console.next();
            	File jsonFile = new File(filepath);
            	if (jsonFile.exists()) {
            		//System.out.println(loadJSON(filepath));
            		System.out.println();
            	} else {
            		System.out.println("No such file exists. Please enter filepath again.");
            	}
                break;
                
                //Case that displays help instructions
                case 8:
                System.out.println();
                System.out.println("The menu options accept numbers only. Any words while selecting a menu option will have you try again.");
                System.out.println();
                
                System.out.println("In the Classes command, you'll be able to do the following: ");
                System.out.println(" -> Create a class");
                System.out.println(" -> Delete a class");
                System.out.println(" -> Rename a class");
                System.out.println();

                System.out.println("In the Attribute command, you'll be able to do the following: ");
                System.out.println(" -> Create an attribute");
                System.out.println(" -> Delete an attribute");
                System.out.println(" -> Rename an attribute");
                System.out.println();
                
                System.out.println("In the Relationships command, you'll be able to do the following: ");
                System.out.println(" -> Create a relationship");
                System.out.println(" -> Delete a relationship");
                System.out.println();
                
                System.out.println("In the List command, you'll be able to do the following: ");
                System.out.println(" -> List all the classes with their attributes");
                System.out.println(" -> List a specific class with its attributes");
                System.out.println(" -> List all the existing relationships");
                System.out.println();
                
                System.out.println("Once you are finished, you may exit the program by selecting 'Exit the program'");
                break;

                //Case that exists the program
                case 9:
                loop = false;
                break;

                //Default case that sends user back to main menu if a number not on the menu is entered
                default:
                System.out.println();
                System.out.print("That is not a menu option! Please try again.");
                System.out.println();
                break;
            }
        }
    }
 
///////////////////////////////////////////////////////////
//
//	createClass
//
///////////////////////////////////////////////////////////

    public static void createNewClassCLI(String className) 
    {
        //Checks if the class already exists
    	if (classMap.containsKey(className))
    	{
    		System.out.println("There is already a class with that name.");
    		return;
    	}
    	classMap.put(className, new ClassObject(className));
	    
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
        //Checks if class exists, doesn't exists or if the name is a duplicate
       	if (classMap.containsKey(newName))
    	{
    		System.out.println("There is a class with the new name.");
    		return;
    	}
        if(!classMap.containsKey(name))
        {
           System.out.println("There is not an existing class with the name: " + name + ".");
    		return; 
        }
        //Rename class but putting into map with new name and removing the old name
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
        //Checks if class exists
        if (!classMap.containsKey(name))
    	{
    		System.out.println("There is not a class with that name.");
    		return;
    	}
        //Deletes attributes and the deletes the class
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

    public static void createRelationship(String class1, String class2, String ID, String newType)
    {
        //Checks to make sure the relationship is not already created
        if(relMap.containsKey(ID))
        {
            System.out.println();
            System.out.println("This relationship already exists");
            return;
        }
        //Create temp classes to be able to create relationship
        ClassObject source = classMap.get(class1);
        ClassObject destination = classMap.get(class2);
        relMap.put(ID, new Relationships(source, destination, ID, newType)); 
	    
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
        //Checks to see if relationship exists
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

//////////////////////////////////////////////////////////
//
//	changeRelationshipType
//
///////////////////////////////////////////////////////////

    public static void changeRelationshipType(String ID, String newType)
    {
        if(newType.equals(relMap.get(ID).relType()))
        {
            System.out.println();
            System.out.println("There is already the type of the relationship.");
            return;
        }
        relMap.get(ID).changeType(newType);

        System.out.println();
        System.out.print("The relationship type has been changed!");
        System.out.println();
    }
    
///////////////////////////////////////////////////////////
//
//	printClass
//
///////////////////////////////////////////////////////////
      
    public static void printClass(String className) 
    {
        System.out.println("Class Name: " + className);  
        System.out.print("Attributes: ");
        classMap.get(className).printAttr();
    }

    public static void printClasses() 
    {
        for (String key : classMap.keySet()) 
    	{
            printClass(key);
    	}
    }

///////////////////////////////////////////////////////////
//
//	listRelationships
//
///////////////////////////////////////////////////////////

	public static void listRelationships()	
    {
		for (String key : relMap.keySet())
		{
			System.out.print(key + ": ");
			System.out.println(relMap.get(key).sourceName() + ", " + relMap.get(key).destinationName());
		}
	}
	
///////////////////////////////////////////////////////////
//
//	saveJSON(String, Map<String, Class>)
//
//	function that creates and saves classMap to a JSON file using 
//	a prompted file name and the classMap.
//
///////////////////////////////////////////////////////////

    public static void saveJSON(String name, Map<String, ClassObject> map) throws IOException{
    	//converts map into JSON object
    	String s = map.toString();
    	// writing map to JSON file
    	try {
    		FileWriter file = new FileWriter(name);
    		file.write(s.toString());
    		file.close();
    	} catch (IOException e) {
    		e.printStackTrace();
            System.out.println("The filepath does not exist. Enter a correct filepath.");
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
    
    /*public static Map<String, Class> loadJSON(String filepath) throws IOException, FileNotFoundException {
        // added JAR file
    	String file = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Class> newObj = objectMapper.readValue(file, HashMap.class);
    	classMap = newObj;
    	return newObj;
    }*/
}