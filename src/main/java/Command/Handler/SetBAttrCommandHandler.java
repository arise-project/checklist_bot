package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.SetBAttrCommand;
import Command.StorageCommandType;
import Domain.NodeAttribute;
import Repository.Interface.IStorageRepository;

public class SetBAttrCommandHandler implements IStorageCommandHandler<SetBAttrCommand> {
    private final IStorageRepository storage;

    public SetBAttrCommandHandler(IStorageRepository storage){
        this.storage = storage;
    }

    @Override
    public void handle(SetBAttrCommand resetAttrCommand) {
        if(storage.getRoot() != null) {
            NodeAttribute a = new NodeAttribute();
            a.setName(resetAttrCommand.getName());
            a.setBValue(true);
            storage.addAttribute(resetAttrCommand.getName(), a);
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.SetBAttr;
    }
}
