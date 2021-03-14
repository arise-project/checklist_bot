package Command;

import Command.Handler.EverynoteProductionCommandHandler;
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
            IStorageCommandHandler<StatisticsCommand> statisticsCommandHandler,
            IStorageCommandHandler<ConnectEverynoteCommand> connectEverynoteCommandHandler,
            IStorageCommandHandler<EverynoteProductionCommand> everynoteProductionCommand,
            IStorageCommandHandler<ListAllENotesCommand> listAllENotesCommand,
            IStorageCommandHandler<ListENotebooksCommand> listENotebooksCommand,
            IStorageCommandHandler<ListENotesForNotebookCommand> listENotesForNotebookCommand,
            IStorageCommandHandler<ReadENoteCommand> readENoteCommand){
        RegisterCommandHandler(openStorageCommandHandler);
        RegisterCommandHandler(readTextFileCommandHandler);
        RegisterCommandHandler(resetAttrCommandHandler);
        RegisterCommandHandler(saveStorageCommandHandler);
        RegisterCommandHandler(statisticsCommandHandler);
        RegisterCommandHandler(connectEverynoteCommandHandler);
        RegisterCommandHandler(everynoteProductionCommand);
        RegisterCommandHandler(listAllENotesCommand);
        RegisterCommandHandler(listENotebooksCommand);
        RegisterCommandHandler(listENotesForNotebookCommand);
        RegisterCommandHandler(readENoteCommand);
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
