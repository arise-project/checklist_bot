package Domain.Knowledge.Metadata;

public class RefMetaKB extends BaseMetaKB {
    public RefMetaKB() {
        super(MetaKBType.RefMeta);
    }

    private String Path;

    public String getPath(){
        return Path;
    }

    public void setPath(String path){
        Path = path;
    }

    @Override
    public String getValueString() {
        return Path;
    }
}
