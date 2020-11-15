package Command;

import Command.Interface.IStorageCommand;
import Domain.Root;

public class OpenStorageCommand implements IStorageCommand {
    private String fileName;

    public String getFileName(){
        return fileName;
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
        return StorageCommandType.OpenStorage;
    }

    @Override
    public void setAttributes(String[] attributes) {
        fileName = attributes[0];
    }
}
