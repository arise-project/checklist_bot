package Command.Handler.Interface;

import Command.StorageCommandType;

public interface IStorageCommandHandler<TCommand> {
    StorageCommandType getType();
    void handle(TCommand command);
}
