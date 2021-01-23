package Command;

import Command.Interface.IStorageCommand;
import Domain.Root;

public class ListENotebooksCommand implements IStorageCommand {

    private Root root;

    public Root getRoot(){
        return root;
    }

    public void setRoot(Root root){
        this.root = root;
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ListENotebooks;
    }

    @Override
    public void setAttributes(String[] attributes) {

    }
}
