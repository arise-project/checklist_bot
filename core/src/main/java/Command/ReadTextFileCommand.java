package Command;

import Command.Interface.IStorageCommand;
import Domain.Root;

public class ReadTextFileCommand implements IStorageCommand {
    private String fileName;

    public String getFileName(){
        return fileName;
    }

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
        return StorageCommandType.ReadTextFile;
    }

    @Override
    public void setAttributes(String[] attributes) {
        name = attributes[0];
        fileName = attributes[1];
    }
}
