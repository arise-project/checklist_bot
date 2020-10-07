package Domain;

import java.util.ArrayList;

public class Paragraph extends Node {
	protected ArrayList<Node> Nodes;
	
	public Paragraph(){
		Nodes = new ArrayList<>();
	}

	public ArrayList<Node> getNodes(){
		return Nodes;
	}

	public void setNodes(ArrayList<Node> nodes){
		Nodes = nodes;
	}
}