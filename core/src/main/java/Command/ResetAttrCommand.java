package Command;

import Command.Interface.IStorageCommand;

public class ResetAttrCommand implements IStorageCommand {
    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    private String nodeName;

    public String getNodeName(){
        return nodeName;
    }

    public void setNodeName(String nodeName){
        this.nodeName = nodeName;
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ResetAttr;
    }

    @Override
    public void setAttributes(String[] attributes) {
        nodeName = attributes[0];
        name = attributes[1];
    }
}
