/***********************************************************************
 * Module:  CommandInterface.java
 * Author:  Boris
 * Purpose: Defines the Interface CommandInterface
 ***********************************************************************/

package command;

public interface CommandInterface {

	public void execute(String[] args);

	public void unexecute();
	
	public String getDescription();

}