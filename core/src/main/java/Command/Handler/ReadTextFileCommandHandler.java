package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.ReadTextFileCommand;
import Command.StorageCommandType;
import Repository.Interface.IStorageRepository;
import Service.Interface.ITextService;
import com.google.inject.Inject;

public class ReadTextFileCommandHandler implements IStorageCommandHandler<ReadTextFileCommand> {
    private final IStorageRepository storage;
    private final ITextService text;

    @Inject
    public ReadTextFileCommandHandler(IStorageRepository storage, ITextService text){
        this.storage = storage;
        this.text = text;
    }

    @Override
    public void handle(ReadTextFileCommand readTextFileCommand) {
        storage.getRoot().setName(readTextFileCommand.getName());
        text.parseTextFile(storage.getRoot(), readTextFileCommand.getFileName());
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ReadTextFile;
    }
}