package Domain.Knowledge.Metadata;

public abstract class BaseMetaKB {
    protected final MetaKBType Type;

    protected BaseMetaKB(MetaKBType type) {
        Type = type;
    }

    public MetaKBType getType(){
        return Type;
    }

    private String Name;

    public String getName(){
        return Name;
    }

    public void setName(String name){
        Name = name;
    }

    public abstract String getValueString();
}
