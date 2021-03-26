package edu.millersville.uml_editor.cli_command;

import edu.millersville.uml_editor.model.*;
import edu.millersville.uml_editor.view.*;

public abstract class Command {
	
	 protected UMLModel model;
	 protected CLIView view;
	 protected String[] commands;
	 protected String errorMessage = "Error in parsing command. Proper command usage is: ";
	 protected String[] commandUsage;
	 protected static boolean prompt;
	 
	 
	 public void CommandObj(UMLModel m, CLIView view, String[] com, boolean p) {
			this.model = m;
			this.view = view;
			this.commands = com;
			commandUsage = getCommandUsage();
			prompt = p;
	 }
	 
	 private static String[] getCommandUsage() {
			String[] commandUsage = { 
				"\n  save <name>.json", 
				"\n  load <path>", 
				"\n  create class <name>",
				"\n  create field <class name> <field name> <field type>",
				"\n  create method <class name> <method name> <method type>",
				"\n  create relationship <class name1> <class name2> <relationship ID> <relationship type>",
				"\n  create parameter <class name> <method> <parameter name> <parameter type>",
				"\n  delete class <name>", 
				"\n  delete field <class name> <field name>",
				"\n  delete method <class name> <method name>",
				"\n  delete relationship <relationship ID>",
				"\n  delete parameter <class name> <method name>, <parameter name>",
				"\n  rename class <name> <newname>", 
				"\n  rename field <class name> <field name> <newname>",
				"\n  rename method <class name> <method name> <newname>",
				"\n  rename parameter <class name> <method name> <parameter name> <parameter newname>",
				"\n  list classes",
				"\n  list relationships", 
				"\n  clear", 
				"\n quit" 
				};
			return commandUsage;
	}
	 protected abstract boolean execute();
}