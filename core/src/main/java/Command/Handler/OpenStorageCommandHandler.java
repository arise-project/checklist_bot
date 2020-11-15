package Command.Handler;

import Algorithm.Interface.ITreeMerger;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.OpenStorageCommand;
import Command.StorageCommandType;
import Domain.MergeNote;
import Domain.Root;
import Repository.Interface.IStorageRepository;
import Service.Interface.IMergeService;
import com.google.inject.Inject;

import java.util.ArrayList;

public class OpenStorageCommandHandler implements IStorageCommandHandler<OpenStorageCommand> {
    private final IStorageRepository storage;
    private final ITreeMerger merger;
    private final IMergeService mergeService;

    @Inject
    public OpenStorageCommandHandler(IStorageRepository storage, ITreeMerger merger, IMergeService mergeService){

        this.storage = storage;
        this.merger = merger;
        this.mergeService = mergeService;
    }

    @Override
    public void handle(OpenStorageCommand openStorageCommand) {
        Root newRoot = storage.open(openStorageCommand.getFileName());
        if(openStorageCommand.getRoot().getNodes().size() > 0)
        {
            mergeService.printDiff(merger.getDifference(openStorageCommand.getRoot(), newRoot));
        }
        openStorageCommand.setRoot(merger.merge(openStorageCommand.getRoot(), newRoot));
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.OpenStorage;
    }
}
