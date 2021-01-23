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
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

public class ListENotesCommandHandler implements IStorageCommandHandler<ListENotesForNotebookCommand> {
    private final IEverynoteService everynoteService;

    @Inject
    public ListENotesCommandHandler(IEverynoteService everynoteService){
        this.everynoteService = everynoteService;
    }

    @Override
    public void handle(ListENotesForNotebookCommand listENotesForNotebookCommand) {
        System.out.println("notes for " + listENotesForNotebookCommand.getName());
        ArrayList<ENotebook> notebooks = everynoteService.listNotebooks();
        Optional<ENotebook> notebook = notebooks.stream().filter(n -> n.getName().trim().equalsIgnoreCase(listENotesForNotebookCommand.getName().trim())).findFirst();

        if(notebook.isEmpty()) {
            System.out.println("NOT FOUND");
            return;
        }

        ArrayList<ENote> notes = everynoteService.listNotes(notebook.get());
        notes.sort(Comparator.comparing(ENote::getUpdated, Collections.reverseOrder()));
        for (ENote note : notes)
        {
            System.out.println("=========");
            System.out.println(note.getTitle());
            System.out.println(note.getUpdated());
            System.out.println(note.getCreated());
            System.out.println(notebook.get().getName() + " notebook");
            System.out.println("=========");
        }
    }

    @Override
    public StorageCommandType getType() {
        return StorageCommandType.ListAllENotesForNotebook;
    }
}
