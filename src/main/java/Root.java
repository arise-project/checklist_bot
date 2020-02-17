import java.util.ArrayList;

public class Root extends Node {
	public Root(String name){
		Key = name.hashCode();
		Name = name;
		Nodes = new ArrayList<>();
	}

	public ArrayList<Node> Nodes;
}