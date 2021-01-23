package Command.Handler;

import Algorithm.Interface.ITreeMerger;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.ReadENoteCommand;
import Command.ReadTextFileCommand;
import Command.StorageCommandType;
import Domain.Everynote.ENote;
import Domain.Root;
import Repository.Interface.IStorageRepository;
import Service.Interface.IEverynoteService;
import Service.Interface.IMergeService;
import Service.Interface.ITextService;
import com.google.inject.Inject;

public class ReadENoteCommandHandler implements IStorageCommandHandler<ReadENoteCommand> {
    private final IStorageRepository storage;
    private final ITextService text;
    private final ITreeMerger merger;
    private final IMergeService mergeService;
    private final IEverynoteService everynoteService;

    @Inject
    public ReadENoteCommandHandler(
            IStorageRepository storage,
            ITextService text,
            ITreeMerger merger,
            IMergeService mergeService,
            IEverynoteService everynoteService){
        this.storage = storage;
        this.text = text;
        this.merger = merger;
        this.mergeService = mergeService;
        this.everynoteService = everynoteService;
    }

    @Override
    public void handle(ReadENoteCommand readENoteCommand) {
        readENoteCommand.getRoot().setName(readENoteCommand.getName());
        Root newRoot = new Root();
        newRoot.setName(readENoteCommand.getName());
        ENote note = everynoteService.searchNotes(readENoteCommand.getName());
        text.parseENote(newRoot, note);
        if(readENoteCommand.getRoot().getNodes().size() > 0)
        {
            mergeService.printDiff(merger.getDifference(readENoteCommand.getRoot(), newRoot));
            readENoteCommand.setRoot(merger.merge(readENoteCommand.getRoot(), newRoot));
        }
        else{
            readENoteCommand.setRoot(newRoot);
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ReadTextFile;
    }
}