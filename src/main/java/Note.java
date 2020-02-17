public class Note extends Node {
	public String Name;
	public String Text;

	public Note(String name, String text){
		Key = name.hashCode() ^ text.hashCode();
		Name = name;
		Text = text;
	}
}