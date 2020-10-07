package Command;

import Command.Interface.IStorageCommand;

public class OpenStorageCommand implements IStorageCommand {
    private String fileName;

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
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
