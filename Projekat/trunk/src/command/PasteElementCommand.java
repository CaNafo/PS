package command;

import model.DiagramModel;
import model.Element;

public class PasteElementCommand implements CommandInterface {
	private DiagramModel model;
	private Element element;
	public PasteElementCommand(DiagramModel model, Element element) {
		this.model = model;
		this.element = element;
	}
	
	@Override
	public void execute(String[] args) {
		model.addElement(element);		
	}

	@Override
	public void unexecute() {
		model.removeElement(element);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
