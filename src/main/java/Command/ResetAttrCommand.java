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

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ResetAttr;
    }

    @Override
    public void setAttributes(String[] attributes) {
        name = attributes[0];
    }
}
