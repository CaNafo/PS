/***********************************************************************
 * Module:  Specification.java
 * Author:  Ca
 * Purpose: Defines the Class Specification
 ***********************************************************************/

package model;

public class Specification {
	private String preConditions;
	private String actionSteps;
	private String extensionPoints;
	private String exceptions;
	private String postConditions;

	public Specification() {
		this.preConditions="";
		this.actionSteps="";
		this.extensionPoints="";
		this.exceptions="";
		this.postConditions="";
	}
	
	public Specification(String preConditions, String actionSteps, String extensionPoints, String exceptions,
			String postConditions) {
		this.preConditions = preConditions;
		this.actionSteps = actionSteps;
		this.extensionPoints = extensionPoints;
		this.exceptions = exceptions;
		this.postConditions = postConditions;
	}
	
	/**
	 * @param preconditions
	 * @param actionSteps
	 * @param extensions
	 * @param exceptions
	 * @param postconditions
	 */
	public void setSpecification(String preConditions, String actionSteps, String extensionPoints, String exceptions,
			String postConditions) {
		this.preConditions = preConditions;
		this.actionSteps = actionSteps;
		this.extensionPoints = extensionPoints;
		this.exceptions = exceptions;
		this.postConditions = postConditions;
	}

	public String getPreConditions() {
		return preConditions;
	}

	public String getActionSteps() {
		return actionSteps;
	}

	public String getExtensionPoints() {
		return extensionPoints;
	}

	public String getExceptions() {
		return exceptions;
	}

	public String getPostConditions() {
		return postConditions;
	}

	
	
}