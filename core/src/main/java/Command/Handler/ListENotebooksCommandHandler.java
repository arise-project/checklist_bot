package Command.Handler;

import Algorithm.Interface.ITreeWalker;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.ListENotebooksCommand;
import Command.StatisticsCommand;
import Command.StorageCommandType;
import Domain.Everynote.ENotebook;
import Repository.Interface.IStorageRepository;
import Service.Interface.IEverynoteService;
import com.google.inject.Inject;

import java.util.ArrayList;

public class ListENotebooksCommandHandler implements IStorageCommandHandler<ListENotebooksCommand> {
    private final IEverynoteService everynoteService;

    @Inject
    public ListENotebooksCommandHandler(IEverynoteService everynoteService){
        this.everynoteService = everynoteService;
    }

    @Override
    public void handle(ListENotebooksCommand listENotebooksCommand) {
        System.out.println("list everynote notebooks");
        ArrayList<ENotebook> notebooks = everynoteService.listNotebooks();
        for (ENotebook notebook : notebooks)
        {
            System.out.println(notebook.getName());
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ListENotebooks;
    }
}
