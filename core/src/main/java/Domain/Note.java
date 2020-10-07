package Domain;

public class Note extends Node {
	protected String Text;

	public String getText(){
		return Text;
	}

	public void setText(String text){
		Text = text;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}

		Note other = (Note)obj;
	
		return Name.equals(other.getName()) && Text.equals(other.getText());
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		if(Name != null){
			result = 31 * result + Name.hashCode();
		}

		if(Text != null){
			result = 31 * result + Text.hashCode();
		}
		
		return result;
	}
}