package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.ListAllENotesCommand;
import Command.ListENotesForNotebookCommand;
import Command.StorageCommandType;
import Domain.Everynote.ENote;
import Domain.Everynote.ENotebook;
import Service.Interface.IEverynoteService;
import com.google.inject.Inject;

import java.util.ArrayList;

public class ListENotesCommandHandler implements IStorageCommandHandler<ListENotesForNotebookCommand> {
    private final IEverynoteService everynoteService;

    @Inject
    public ListENotesCommandHandler(IEverynoteService everynoteService){
        this.everynoteService = everynoteService;
    }

    @Override
    public void handle(ListENotesForNotebookCommand listENotesForNotebookCommand) {
        System.out.println("list everynote notes");
        ArrayList<ENotebook> notebooks = everynoteService.listNotebooks();
        ENotebook notebook = null;
        for(var n : notebooks) {
            if(n.getName() == listENotesForNotebookCommand.getName())
            {
                notebook = new ENotebook(n.getGuid(), n.getName());
            }
        }
        if(notebook == null) {
            System.out.println("NOT FOUND");
            return;
        }

        ArrayList<ENote> notes = everynoteService.listNotes(notebook);
        for (ENote note : notes)
        {
            System.out.println("=========");
            System.out.println(note.getTitle());
            System.out.println(note.getUpdated());
            System.out.println(note.getCreated());
            System.out.println(notebook.getName() + " notebook");
            System.out.println("=========");
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ListAllENotesForNotebook;
    }
}
