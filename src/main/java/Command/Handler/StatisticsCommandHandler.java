package Command.Handler;

import Algorithm.Interface.ITreeWalker;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.StatisticsCommand;
import Command.StorageCommandType;
import Repository.Interface.IStorageRepository;
import com.google.inject.Inject;

public class StatisticsCommandHandler implements IStorageCommandHandler<StatisticsCommand> {
    private final IStorageRepository storage;
    private final ITreeWalker walker;

    @Inject
    public StatisticsCommandHandler(IStorageRepository storage, ITreeWalker walker){
        this.storage = storage;
        this.walker = walker;
    }

    @Override
    public void handle(StatisticsCommand statisticsCommand) {
        System.out.println("statistics: " + storage.getStorageFile());
        if(storage.getRoot() != null) {
            System.out.println("Tree size: " + walker.getInBreadth(storage.getRoot()).size());
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
