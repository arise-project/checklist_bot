package Command.Handler;

import Algorithm.Interface.ITreeMerger;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.ReadTextFileCommand;
import Command.StorageCommandType;
import Domain.Root;
import Repository.Interface.IStorageRepository;
import Service.Interface.IMergeService;
import Service.Interface.ITextService;
import com.google.inject.Inject;

public class ReadTextFileCommandHandler implements IStorageCommandHandler<ReadTextFileCommand> {
    private final IStorageRepository storage;
    private final ITextService text;
    private final ITreeMerger merger;
    private final IMergeService mergeService;

    @Inject
    public ReadTextFileCommandHandler(IStorageRepository storage, ITextService text, ITreeMerger merger, IMergeService mergeService){
        this.storage = storage;
        this.text = text;
        this.merger = merger;
        this.mergeService = mergeService;
    }

    @Override
    public void handle(ReadTextFileCommand readTextFileCommand) {
        readTextFileCommand.getRoot().setName(readTextFileCommand.getName());
        Root newRoot = new Root();
        text.parseTextFile(newRoot, readTextFileCommand.getFileName());
        if(readTextFileCommand.getRoot().getNodes().size() > 0)
        {
            mergeService.printDiff(merger.getDifference(readTextFileCommand.getRoot(), newRoot));
            readTextFileCommand.setRoot(merger.merge(readTextFileCommand.getRoot(), newRoot));
        }
        else{
            readTextFileCommand.setRoot(newRoot);
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ReadTextFile;
    }
}