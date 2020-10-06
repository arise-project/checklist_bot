package Command;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.Interface.IStorageCommand;

import java.util.HashMap;
import java.util.Map;

public class StrageCommandBus {
    private final Map<StorageCommandType, IStorageCommandHandler<IStorageCommand>> dictionary = new HashMap<>();

    public <TCommand extends IStorageCommand> void RegisterCommandHandler(IStorageCommandHandler<TCommand> handler)
    {
        if (dictionary.containsKey(handler.getType()))
            throw new IllegalStateException();

        dictionary.put(handler.getType(), (IStorageCommandHandler<IStorageCommand>) handler);
    }

    public <TCommand extends IStorageCommand> void Dispatch(TCommand command)
    {
        if (!dictionary.containsKey(command.getType())){
            throw new IllegalStateException();
        }

        dictionary.get(command.getType()).handle(command);
    }
}
