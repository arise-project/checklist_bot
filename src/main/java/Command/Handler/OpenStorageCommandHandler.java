package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.OpenStorageCommand;
import Command.StorageCommandType;
import Repository.Interface.IStorageRepository;

public class OpenStorageCommandHandler implements IStorageCommandHandler<OpenStorageCommand> {
    private final IStorageRepository storage;

    public OpenStorageCommandHandler(IStorageRepository storage){
        this.storage = storage;
    }

    @Override
    public void handle(OpenStorageCommand openStorageCommand) {
        storage.open(openStorageCommand.getFileName());
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.OpenStorage;
    }
}
