public class Node {
	protected String Name;

	public String getName(){
		return Name;
	}

	public void setName(String name){
		Name = name;
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