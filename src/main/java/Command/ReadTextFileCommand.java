package Command;

import Command.Interface.IStorageCommand;

public class ReadTextFileCommand implements IStorageCommand {
    private String fileName;

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
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
