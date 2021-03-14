package Domain;

import java.util.ArrayList;

import Domain.Knowledge.Metadata.BaseMetaKB;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
		property = "type"
)
@JsonSubTypes({
	@JsonSubTypes.Type( value = Note.class, name = "note" ),
	@JsonSubTypes.Type( value = Paragraph.class, name = "paragraph" )
})
public abstract class Node {
	private String Name;

	public String getName(){
		return Name;
	}

	public void setName(String name){
		Name = name;
	}

	public Node(){
		Meta = new ArrayList<>();
	}

	private final ArrayList<BaseMetaKB> Meta;

	public ArrayList<BaseMetaKB> getMeta(){
		return Meta;
	}

	private boolean Deleted;

	public boolean getleted(){
		return Deleted;
	}

	public void setDeleted(boolean deleted){
		Deleted = deleted;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}

		if (getClass() != obj.getClass()){
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