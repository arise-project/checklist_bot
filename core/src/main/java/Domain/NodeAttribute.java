package Domain;

public class NodeAttribute {
	private String Name;

	public String getName(){
		return Name;
	}

	public void setName(String name){
		Name = name;
	}

	private Boolean BValue;
	
	public Boolean getBValue(){
		return BValue;
	}
	
	public void setBValue(Boolean bval){
		BValue = bval;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}

		if (getClass() != obj.getClass()){
			return false;
		}

		NodeAttribute other = (NodeAttribute)obj;

		return Name.equals(other.getName());
	}

	@Override
	public int hashCode() {
		return Name.hashCode();
	}
}