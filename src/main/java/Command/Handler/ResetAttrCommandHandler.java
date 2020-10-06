package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.ResetAttrCommand;
import Command.StorageCommandType;
import Domain.NodeAttribute;
import Repository.Interface.IStorageRepository;

public class ResetAttrCommandHandler implements IStorageCommandHandler<ResetAttrCommand> {
    private final IStorageRepository storage;

    public ResetAttrCommandHandler(IStorageRepository storage){
        this.storage = storage;
    }

    @Override
    public void handle(ResetAttrCommand resetAttrCommand) {
        if(storage.getRoot() != null) {
            NodeAttribute a = new NodeAttribute();
            a.setName(resetAttrCommand.getName());
            a.setBValue(null);
            storage.addAttribute(resetAttrCommand.getName(), a);
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ResetAttr;
    }
}
