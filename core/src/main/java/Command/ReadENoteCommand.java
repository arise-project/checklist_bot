package Command;

import Command.Interface.IStorageCommand;
import Domain.Root;

public class ReadENoteCommand implements IStorageCommand {
    private String name;

    public String getName(){
        return name;
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
        return StorageCommandType.ReadENote;
    }

    @Override
    public void setAttributes(String[] attributes) {
        name = attributes[0];
    }
}
