package Domain.Knowledge.Filesystem;

import Domain.Knowledge.Metadata.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type( value = BoolMetaBK.class, name = "boolMeta" ),
        @JsonSubTypes.Type( value = IntMetaKB.class, name = "intMeta" ),
        @JsonSubTypes.Type( value = StringMetaKB.class, name = "stringMeta" ),
        @JsonSubTypes.Type( value = RefMetaKB.class, name = "refMeta" )
})
public class FileKB {

    private String Path;

    public FileKB() {
        Meta = new ArrayList<>();
    }

    public String getPath(){
        return Path;
    }

    public void setPath(String path){
        Path = path;
    }

    private String Name;

    public String getName(){
        return Name;
    }

    public void setName(String name){
        Name = name;
    }

    private final ArrayList<BaseMetaKB> Meta;

    public ArrayList<BaseMetaKB> getMeta(){
        return Meta;
    }
}
