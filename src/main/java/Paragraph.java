import java.util.ArrayList;

public class Paragraph extends Node {
	public Paragraph(String name){
		Key = name.hashCode();
		Name = name;
		Nodes = new ArrayList<>();
	}

	public ArrayList<Node> Nodes;
}