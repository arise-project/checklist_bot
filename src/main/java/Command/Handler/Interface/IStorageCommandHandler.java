package Command.Handler.Interface;

public interface ICommandHandler<TCommand> {
    void Handle(TCommand command);
}
