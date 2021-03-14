package Domain.Knowledge.Metadata;

public class StringMetaKB extends BaseMetaKB{
    public StringMetaKB() {
        super(MetaKBType.StringMeta);
    }

    private Boolean Value;

    public Boolean getValue(){
        return Value;
    }

    public void setValue(Boolean value){
        Value = value;
    }

    @Override
    public String getValueString() {
        return Value.toString();
    }
}
