package Command.Interface;

import Command.StorageCommandType;
import Domain.Root;

public interface IStorageCommand {
    StorageCommandType getType();
    void setAttributes(String [] attributes);
    Root getRoot();
    void setRoot(Root root);
}
