package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.ResetAttrCommand;
import Command.SaveStorageCommand;
import Command.StorageCommandType;
import Repository.Interface.IStorageRepository;
import com.google.inject.Inject;

public class ResetAttrCommandHandler implements IStorageCommandHandler<ResetAttrCommand> {
    private final IStorageRepository storage;

    @Inject
    public ResetAttrCommandHandler(IStorageRepository storage){
        this.storage = storage;
    }

    @Override
    public void handle(ResetAttrCommand resetAttrCommand) {
        //TODO
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ResetAttr;
    }
}
