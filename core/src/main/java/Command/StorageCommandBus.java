package Command;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.Interface.IStorageCommand;
import Command.Interface.IStorageCommandBus;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

public class StorageCommandBus implements IStorageCommandBus {
    private final Map<StorageCommandType, IStorageCommandHandler<IStorageCommand>> dictionary = new HashMap<>();

    @Inject
    public StorageCommandBus(
            IStorageCommandHandler<OpenStorageCommand> openStorageCommandHandler,
            IStorageCommandHandler<ReadTextFileCommand> readTextFileCommandHandler,
            IStorageCommandHandler<ResetAttrCommand> resetAttrCommandHandler,
            IStorageCommandHandler<SaveStorageCommand> saveStorageCommandHandler,
            IStorageCommandHandler<SetBAttrCommand> setBAttrCommandHandler,
            IStorageCommandHandler<StatisticsCommand> statisticsCommandHandler,
            IStorageCommandHandler<ConnectEverynoteCommand> connectEverynoteCommandHandler){
        RegisterCommandHandler(openStorageCommandHandler);
        RegisterCommandHandler(readTextFileCommandHandler);
        RegisterCommandHandler(resetAttrCommandHandler);
        RegisterCommandHandler(saveStorageCommandHandler);
        RegisterCommandHandler(setBAttrCommandHandler);
        RegisterCommandHandler(statisticsCommandHandler);
        RegisterCommandHandler(connectEverynoteCommandHandler);
    }

    public <TCommand extends IStorageCommand> void Dispatch(TCommand command)
    {
        if (!dictionary.containsKey(command.getType())){
            throw new IllegalStateException();
        }

        dictionary.get(command.getType()).handle(command);
    }

    private <TCommand extends IStorageCommand> void RegisterCommandHandler(IStorageCommandHandler<TCommand> handler)
    {
        if (dictionary.containsKey(handler.getType()))
            throw new IllegalStateException();

        dictionary.put(handler.getType(), (IStorageCommandHandler<IStorageCommand>) handler);
    }
}
