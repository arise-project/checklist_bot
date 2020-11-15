package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.SetBAttrCommand;
import Command.StorageCommandType;
import Domain.NodeAttribute;
import Repository.Interface.IStorageRepository;
import com.google.inject.Inject;

public class SetBAttrCommandHandler implements IStorageCommandHandler<SetBAttrCommand> {
    private final IStorageRepository storage;

    @Inject
    public SetBAttrCommandHandler(IStorageRepository storage){
        this.storage = storage;
    }

    @Override
    public void handle(SetBAttrCommand setBAttrCommand) {
        if(setBAttrCommand.getRoot() != null) {
            NodeAttribute a = new NodeAttribute();
            a.setName(setBAttrCommand.getName());
            a.setBValue(true);
            storage.addAttribute(setBAttrCommand.getNodeName(), a, setBAttrCommand.getRoot());
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.SetBAttr;
    }
}
