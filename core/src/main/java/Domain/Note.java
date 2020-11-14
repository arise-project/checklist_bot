package Domain;

public class Note extends Node {
	private String Text;

	public String getText(){
		return Text;
	}

	public void setText(String text){
		Text = text;
	}

	private int [] TextSignature;

	public int [] getTextSignature(){
		return TextSignature;
	}

	public void setTextSignature(int [] textSignature){
		TextSignature = textSignature;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}

		if (getClass() != obj.getClass()){
			return false;
		}

		Note other = (Note)obj;
	
		return getName().equals(other.getName()) && Text.equals(other.getText());
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		if(getName() != null){
			result = 31 * result + getName().hashCode();
		}

		if(Text != null){
			result = 31 * result + Text.hashCode();
		}
		
		return result;
	}
}