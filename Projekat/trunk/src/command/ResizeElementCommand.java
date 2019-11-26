/***********************************************************************
 * Module:  ResizeElementCommand.java
 * Author:  Boris
 * Purpose: Defines the Class ResizeElementCommand
 ***********************************************************************/

package command;

import model.DiagramModel;

import java.awt.Dimension;

public class ResizeElementCommand implements CommandInterface {
	@SuppressWarnings("unused")
	private String description;
	private Dimension oldDimensions;

	public DiagramModel model;

	/** @param model */
	public void ResizeEResizeElementCommandlementCommand(DiagramModel model) {
		// TODO: implement
	}

	public void execute(String[] args) {
		// TODO: implement
	}

	public void unexecute() {
		// TODO: implement
	}

	public Dimension getOldDimensions() {
		return oldDimensions;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}