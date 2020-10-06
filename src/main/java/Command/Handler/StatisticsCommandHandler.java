package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.StatisticsCommand;
import Command.StorageCommandType;
import Repository.Interface.IStorageRepository;

public class StatisticsCommandHandler implements IStorageCommandHandler<StatisticsCommand> {
    private final IStorageRepository storage;

    public StatisticsCommandHandler(IStorageRepository storage){
        this.storage = storage;
    }

    @Override
    public void handle(StatisticsCommand statisticsCommand) {
        System.out.println("statistics: " + storage.getStorageFile());
        if(storage.getRoot() != null) {
            System.out.println("Tree size: " + storage.getRoot().getSize());
        }
        else {
            System.out.println("Storage is EMPTY");
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.Statistics;
    }
}
