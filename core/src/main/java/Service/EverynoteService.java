package Service;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.TException;

import java.util.Iterator;
import java.util.List;

public class EverynoteService implements Service.Interface.IEverynoteService {
    private UserStoreClient userStore;
    private NoteStoreClient noteStore;
    private static String Token;

    public void setToken(String token)
    {
        Token = token;
    }

    @Override
    public void auth() throws TException, EDAMSystemException, EDAMUserException {
        EvernoteAuth evernoteAuth = new EvernoteAuth(EvernoteService.SANDBOX, Token);
        ClientFactory factory = new ClientFactory(evernoteAuth);
        userStore = factory.createUserStoreClient();

        boolean versionOk = userStore.checkVersion("Evernote EDAMDemo (Java)",
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
        if (!versionOk) {
            System.err.println("Incompatible Evernote client protocol version");
            System.exit(1);
        }

        // Set up the NoteStore client
        noteStore = factory.createNoteStoreClient();
    }

    @Override
    public void listNotes() throws Exception {
        // List the notes in the user's account
        System.out.println("Listing notes:");

        // First, get a list of all notebooks
        List<Notebook> notebooks = noteStore.listNotebooks();

        for (Notebook notebook : notebooks) {
            System.out.println("Notebook: " + notebook.getName());

            // Next, search for the first 100 notes in this notebook, ordering
            // by creation date
            NoteFilter filter = new NoteFilter();
            filter.setNotebookGuid(notebook.getGuid());
            filter.setOrder(NoteSortOrder.CREATED.getValue());
            filter.setAscending(true);

            NoteList noteList = noteStore.findNotes(filter, 0, 100);
            List<Note> notes = noteList.getNotes();
            for (Note note : notes) {
                System.out.println(" * " + note.getTitle());
                Note noteDetails = noteStore.getNote(note.getGuid(), true, true, true, true);
                System.out.println("content " + new TikaService().extract(noteDetails.getContent()));
            }
        }
        System.out.println();
    }

    /**
     * Search a user's notes and display the results.
     */
    @Override
    public void searchNotes() throws Exception {
        // Searches are formatted according to the Evernote search grammar.
        // Learn more at
        // https://dev.evernote.com/doc/articles/search_grammar.php
        // http://dev.evernote.com/documentation/cloud/chapters/Searching_notes.php

        // In this example, we search for notes that have the term "EDAMDemo" in
        // the title.
        // This should return the sample note that we created in this demo app.
        String query = "intitle:test1";

        // To search for notes with a specific tag, we could do something like
        // this:
        // String query = "tag:tagname";

        // To search for all notes with the word "elephant" anywhere in them:
        // String query = "elephant";

        NoteFilter filter = new NoteFilter();
        filter.setWords(query);
        filter.setOrder(NoteSortOrder.UPDATED.getValue());
        filter.setAscending(false);

        // Find the first 50 notes matching the search
        System.out.println("Searching for notes matching query: " + query);
        NoteList notes = noteStore.findNotes(filter, 0, 50);
        System.out.println("Found " + notes.getTotalNotes() + " matching notes");

        Iterator<Note> iter = notes.getNotesIterator();
        while (iter.hasNext()) {
            Note note = iter.next();
            System.out.println("Note: " + note.getTitle());

            // Note objects returned by findNotes() only contain note attributes
            // such as title, GUID, creation date, update date, etc. The note
            // content
            // and binary resource data are omitted, although resource metadata
            // is included.
            // To get the note content and/or binary resources, call getNote()
            // using the note's GUID.
            Note fullNote = noteStore.getNote(note.getGuid(), true, true, false,
                    false);
            System.out.println("Note contains " + fullNote.getResourcesSize()
                    + " resources");
            System.out.println();
        }
    }
}
