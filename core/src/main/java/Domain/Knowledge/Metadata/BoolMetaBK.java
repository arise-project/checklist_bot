package Domain.Knowledge.Metadata;

public class BoolMetaBK extends BaseMetaKB{
    public BoolMetaBK() {
        super(MetaKBType.BoolMeta);
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
