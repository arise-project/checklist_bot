package Command;

import Command.Interface.IStorageCommand;

public class SaveStorageCommand implements IStorageCommand {
    private String fileName;

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.SaveStorage;
    }

    @Override
    public void setAttributes(String[] attributes) {
        fileName = attributes[0];
    }
}
