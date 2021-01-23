package Command.Handler;

import Command.Handler.Interface.IStorageCommandHandler;
import Command.ListAllENotesCommand;
import Command.ListENotebooksCommand;
import Command.StorageCommandType;
import Domain.Everynote.ENote;
import Domain.Everynote.ENotebook;
import Service.Interface.IEverynoteService;
import com.google.inject.Inject;

import java.util.*;

public class ListAllENotesCommandHandler implements IStorageCommandHandler<ListAllENotesCommand> {
    private final IEverynoteService everynoteService;

    @Inject
    public ListAllENotesCommandHandler(IEverynoteService everynoteService){
        this.everynoteService = everynoteService;
    }

    @Override
    public void handle(ListAllENotesCommand listAllENotesCommand) {
        System.out.println("notes:");
        ArrayList<ENotebook> notebooks = everynoteService.listNotebooks();
        ArrayList<ENote> notes = everynoteService.listAllNotes();
        notes.sort(Comparator.comparing(ENote::getUpdated, Collections.reverseOrder()));
        for (ENote note : notes)
        {
            System.out.println("=========");
            System.out.println(note.getTitle());
            System.out.println("updated: "+new Date(note.getUpdated()).toString());
            System.out.println("created: "+new Date(note.getCreated()).toString());
            for(ENotebook notebook : notebooks)
            {
                if(notebook.getGuid() == note.getNotebookGuid())
                {
                    System.out.println(notebook.getName() + " notebook");
                }
            }
            System.out.println("=========");
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ListAllENotes;
    }
}
