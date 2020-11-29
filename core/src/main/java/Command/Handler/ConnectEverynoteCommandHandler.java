package Command.Handler;

import Command.ConnectEverynoteCommand;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.StorageCommandType;
import Service.Interface.IEverynoteService;
import com.google.inject.Inject;

public class ConnectEverynoteCommandHandler implements IStorageCommandHandler<ConnectEverynoteCommand> {
    private final IEverynoteService everynoteService;

    @Inject
    public ConnectEverynoteCommandHandler(IEverynoteService everynoteService){
        this.everynoteService = everynoteService;
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ConnectEverynote;
    }

    @Override
    public void handle(ConnectEverynoteCommand connectEverynoteCommand) {
        if(!everynoteService.auth()){
            System.out.println("ERROR: Can not connect");
        }
        else{
            System.out.println("DONE");
        }
    }
}
