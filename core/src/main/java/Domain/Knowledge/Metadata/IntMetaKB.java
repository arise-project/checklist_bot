package Domain.Knowledge.Metadata;

public class IntMetaKB extends BaseMetaKB{
    public IntMetaKB() {
        super(MetaKBType.IntMeta);
    }

    private int Value;

    public int getValue(){
        return Value;
    }

    public void setValue(int value){
        Value = value;
    }

    @Override
    public String getValueString() {
        return String.valueOf(Value);
    }
}
