/***********************************************************************
 * Module:  SubjectInterface.java
 * Author:  Ca
 * Purpose: Defines the Interface SubjectInterface
 ***********************************************************************/

package observer;

public interface SubjectInterface {

	/** @param observer */
	public void addObserver(ObserverInterface observer);

	/** @param observer */
	public void removeObserver(ObserverInterface observer);

	public void notifyAllObservers();

}