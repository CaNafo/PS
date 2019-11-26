/***********************************************************************
 * Module:  DokumentAbstract.java
 * Author:  Ca
 * Purpose: Defines the Class DokumentAbstract
 ***********************************************************************/

package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import observer.ObserverInterface;
import observer.SubjectInterface;

public abstract class DocumentAbstract implements SubjectInterface {
	
	private String documentName;
	private String documentPath;
	
	public transient Collection<ObserverInterface> observerInterface;

	public abstract void save();

	public abstract void delete();

	public abstract void deleteFromDisc();

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
		
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	/** @pdGenerated default getter */
	public Collection<ObserverInterface> getObserverInterface() {
		if (observerInterface == null)
			observerInterface = new HashSet<ObserverInterface>();
		return observerInterface;
	}

	

	/** @pdGenerated default iterator getter */
	public Iterator<ObserverInterface> getIteratorObserverInterface() {
		if (observerInterface == null)
			observerInterface = new HashSet<ObserverInterface>();
		return observerInterface.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newObserverInterface
	 */
	public void setObserverInterface(Collection<ObserverInterface> newObserverInterface) {
		removeAllObserverInterface();
		for (Iterator<ObserverInterface> iter = newObserverInterface.iterator(); iter.hasNext();)
			addObserver((ObserverInterface) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newObserverInterface
	 */
	@Override
	public void addObserver(ObserverInterface newObserverInterface) {
		if (newObserverInterface == null)
			return;
		if (this.observerInterface == null)
			this.observerInterface = new HashSet<ObserverInterface>();
		if (!this.observerInterface.contains(newObserverInterface))
			this.observerInterface.add(newObserverInterface);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldObserverInterface
	 */
	@Override
	public void removeObserver(ObserverInterface oldObserverInterface) {
		if (oldObserverInterface == null)
			return;
		if (this.observerInterface != null)
			if (this.observerInterface.contains(oldObserverInterface))
				this.observerInterface.remove(oldObserverInterface);
	}

	
	
	@Override
	public void notifyAllObservers() {
		for (Iterator<ObserverInterface> iterator = getIteratorObserverInterface(); iterator.hasNext();) {
			ObserverInterface observer = (ObserverInterface) iterator.next();
			observer.update();
		}
	}

	/** @pdGenerated default removeAll */
	public void removeAllObserverInterface() {
		if (observerInterface != null)
			observerInterface.clear();
	}

}