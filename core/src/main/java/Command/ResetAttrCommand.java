package Command;

import Command.Interface.IStorageCommand;
import Domain.Root;

public class ResetAttrCommand implements IStorageCommand {
    private String name;

    public String getName(){
        return name;
    }

    private String nodeName;

    public String getNodeName(){
        return nodeName;
    }

    private Root root;

    public Root getRoot(){
        return root;
    }

    public void setRoot(Root root){
        this.root = root;
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
