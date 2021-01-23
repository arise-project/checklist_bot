package Command.Handler;

import Command.EverynoteProductionCommand;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.StorageCommandType;
import Domain.Everynote.ENote;
import Domain.Everynote.ENotebook;
import Service.Interface.IEverynoteService;
import com.google.inject.Inject;

import java.util.ArrayList;

public class EverynoteProductionCommandHandler implements IStorageCommandHandler<EverynoteProductionCommand> {
    private final IEverynoteService everynoteService;

    @Inject
    public EverynoteProductionCommandHandler(IEverynoteService everynoteService){
        this.everynoteService = everynoteService;
    }

    @Override
    public void handle(EverynoteProductionCommand everynoteProductionCommand) {
        System.out.println("Change everynote to production");
        everynoteService.enableProduction();
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.EverynoteProduction;
    }
}
