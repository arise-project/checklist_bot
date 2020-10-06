package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.SaveStorageCommand;
import Command.StorageCommandType;
import Repository.Interface.IStorageRepository;

public class SaveStorageCommandHandler implements IStorageCommandHandler<SaveStorageCommand> {
    private final IStorageRepository storage;

    public SaveStorageCommandHandler(IStorageRepository storage){
        this.storage = storage;
    }

    @Override
    public void handle(SaveStorageCommand saveStorageCommand) {
        if(storage.getRoot() != null) {
            storage.save(saveStorageCommand.getFileName());
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.SaveStorage;
    }
}
