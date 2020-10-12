package Command;

import Command.Interface.IStorageCommand;

public class SetBAttrCommand implements IStorageCommand {
    private String name;

    public String getName(){
        return name;
    }

    private String nodeName;

    public String getNodeName(){
        return nodeName;
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.SetBAttr;
    }

    @Override
    public void setAttributes(String[] attributes)
    {
        nodeName = attributes[0];
        name = attributes[1];
    }
}
