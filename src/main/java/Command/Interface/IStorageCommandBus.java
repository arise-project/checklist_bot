package Command.Interface;

public interface IStorageCommandBus {
    <TCommand extends IStorageCommand> void Dispatch(TCommand command);
}
