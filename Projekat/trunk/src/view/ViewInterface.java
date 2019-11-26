/***********************************************************************
 * Module:  ViewInterface.java
 * Author:  Ca
 * Purpose: Defines the Interface ViewInterface
 ***********************************************************************/

package view;

import java.awt.event.ActionListener;

public interface ViewInterface extends observer.ObserverInterface {
	/** @param actionListener */
	void setListener(ActionListener actionListener);

}