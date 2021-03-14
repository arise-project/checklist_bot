package Domain.Knowledge;

import Domain.Knowledge.Filesystem.FileKB;
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
public class KB {

    private FileKB Workspace;

    public KB() {
        Files = new ArrayList<>();
        Meta = new ArrayList<>();
    }

    public FileKB getWorkspace(){
        return Workspace;
    }

    public void setWorkspace(FileKB workspace){
        Workspace = workspace;
    }

    private final ArrayList<FileKB> Files;

    public ArrayList<FileKB> getFiles(){
        return Files;
    }

    private final ArrayList<BaseMetaKB> Meta;

    public ArrayList<BaseMetaKB> getMeta(){
        return Meta;
    }
}
