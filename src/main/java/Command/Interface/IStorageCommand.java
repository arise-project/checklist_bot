package Command.Interface;

import Command.StorageCommandType;

public interface IStorageCommand {
    StorageCommandType getType();
    void setAttributes(String [] attributes);
}
