import java.util.ArrayList;

public class Node {
	protected String Name;

	public String getName(){
		return Name;
	}

	public void setName(String name){
		Name = name;
	}

	protected ArrayList<NodeAttribute> Attributes;
		
	public Node(){
		Attributes = new ArrayList<>();
	}
	
	public ArrayList<NodeAttribute> getAttributes(){
		return Attributes;
	}
	
	public void setAttributes(ArrayList<NodeAttribute> attributes){
		Attributes = attributes;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}

		Node other = (Node)obj;

		return Name.equals(other.getName());
	}

	@Override
	public int hashCode() {
		return Name.hashCode();
	}
}