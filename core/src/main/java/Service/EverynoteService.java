package Service;

import Domain.Everynote.ENote;
import Domain.Everynote.ENotebook;
import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.*;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.TException;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static java.lang.System.*;

public class EverynoteService implements Service.Interface.IEverynoteService {
    private NoteStoreClient noteStore;
    //https://dev.evernote.com/get-token/
    private static String Token = getenv("everynote");

    private boolean isSandbox = true;

    public void enableProduction()
    {
        isSandbox = false;
    }

    @Override
    public boolean isProduction() {
        return !isSandbox;
    }

    @Override
    public boolean auth() {
        try
        {
            EvernoteAuth evernoteAuth = new EvernoteAuth(
                    isSandbox? EvernoteService.SANDBOX : EvernoteService.PRODUCTION,
                    Token);
            ClientFactory factory = new ClientFactory(evernoteAuth);
            UserStoreClient userStore = factory.createUserStoreClient();

            boolean versionOk = userStore.checkVersion("checklist",
                    com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
                    com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
            if (!versionOk) {
                err.println("Incompatible Evernote client protocol version");
                return false;
            }

            // Set up the NoteStore client
            //EDAMSystemException(errorCode:INVALID_AUTH, message:authenticationToken)
            noteStore = factory.createNoteStoreClient();
            return true;
        }
        catch(TException | EDAMSystemException | EDAMUserException e)
        {
            err.println(e.getMessage());
        }

        return false;
    }

    @Override
    public ArrayList<ENotebook> listNotebooks() {
        // First, get a list of all notebooks
        List<Notebook> notebooks;
        try {
            notebooks = noteStore.listNotebooks();
        } catch (TException | EDAMUserException | EDAMSystemException e) {
            err.println(e.getMessage());
            return null;
        }

        ArrayList<ENotebook> result = new ArrayList<>();
        for (Notebook notebook : notebooks) {
            result.add(new ENotebook(notebook.getGuid(), notebook.getName()));
        }
        return  result;
    }

    @Override
    public ArrayList<ENote> listAllNotes() {
        // First, get a list of all notebooks
        List<Notebook> notebooks;
        try{
            notebooks = noteStore.listNotebooks();
        } catch (TException | EDAMUserException | EDAMSystemException e) {
            err.println(e.getMessage());
            return null;
        }
        ArrayList<ENote> result = new ArrayList<>();
        for (Notebook notebook : notebooks) {
            out.println("Notebook: " + notebook.getName());

            // Next, search for the first 100 notes in this notebook, ordering
            // by creation date
            NoteFilter filter = new NoteFilter();
            filter.setNotebookGuid(notebook.getGuid());
            filter.setOrder(NoteSortOrder.UPDATED.getValue());
            filter.setAscending(false);

            NoteList noteList;
            try{
                noteList = noteStore.findNotes(filter, 0, 100);
            } catch (EDAMSystemException | EDAMNotFoundException | EDAMUserException | TException e) {
                err.println(e.getMessage());
                return null;
            }

            List<Note> notes = noteList.getNotes();

            for (Note note : notes) {
                out.println(" * " + note.getTitle());
                Note noteDetails = null;
                ENote info = null;
                try {
                    noteDetails = noteStore.getNote(note.getGuid(), true, true, true, true);
                } catch (EDAMSystemException | EDAMNotFoundException | EDAMUserException | TException e) {
                    err.println(e.getMessage());
                    info = new ENote(note.getGuid(), note.getNotebookGuid(),e.getMessage());
                }
                if(noteDetails != null){
                    int length = noteDetails.getContentLength();
                    byte[] hash = noteDetails.getContentHash();
                    long created = noteDetails.getCreated();
                    String guid = noteDetails.getGuid();
                    String notebookGuid = noteDetails.getNotebookGuid();
                    String title = noteDetails.getTitle();
                    long updated = noteDetails.getUpdated();
                    int sequenceNum = noteDetails.getUpdateSequenceNum();
                    String content;
                    try{
                        content = new TikaService().extract(noteDetails.getContent());
                    } catch (SAXException | TikaException | IOException e) {
                        err.println(e.getMessage());
                        content = noteDetails.getContent();
                    }
                    info = new ENote(
                            content,
                            length,
                            hash,
                            created,
                            guid,
                            notebookGuid,
                            title,
                            updated,
                            sequenceNum);
                }

                if(info != null) {
                    result.add(info);
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<ENote> listNotes(ENotebook parent) {
        List<Notebook> notebooks;
        try{
            notebooks = noteStore.listNotebooks();
        } catch (TException | EDAMUserException | EDAMSystemException e) {
            err.println(e.getMessage());
            return null;
        }
        ArrayList<ENote> result = new ArrayList<>();
        Optional<Notebook> notebook = notebooks.stream().filter(n -> n.getName().trim().equalsIgnoreCase(parent.getName().trim())).findFirst();

        if(notebook.isEmpty()) {
            out.println("NOT FOUND");
            return result;
        }

        out.println("Notebook: " + notebook.get().getName());

        // Next, search for the first 100 notes in this notebook, ordering
        // by creation date
        NoteFilter filter = new NoteFilter();
        filter.setNotebookGuid(notebook.get().getGuid());
        filter.setOrder(NoteSortOrder.UPDATED.getValue());
        filter.setAscending(false);

        NoteList noteList;
        try{
            noteList = noteStore.findNotes(filter, 0, 100);
        } catch (EDAMSystemException | EDAMNotFoundException | EDAMUserException | TException e) {
            err.println(e.getMessage());
            return null;
        }

        List<Note> notes = noteList.getNotes();

        for (Note note : notes) {
            out.println(" * " + note.getTitle());
            Note noteDetails = null;
            ENote info = null;
            try {
                noteDetails = noteStore.getNote(note.getGuid(), true, true, true, true);
            } catch (EDAMSystemException | EDAMNotFoundException | EDAMUserException | TException e) {
                err.println(e.getMessage());
                info = new ENote(note.getGuid(), note.getNotebookGuid(),e.getMessage());
            }
            if(noteDetails != null){
                int length = noteDetails.getContentLength();
                byte[] hash = noteDetails.getContentHash();
                long created = noteDetails.getCreated();
                String guid = noteDetails.getGuid();
                String notebookGuid = noteDetails.getNotebookGuid();
                String title = noteDetails.getTitle();
                long updated = noteDetails.getUpdated();
                int sequenceNum = noteDetails.getUpdateSequenceNum();
                String content;
                try{
                    content = new TikaService().extract(noteDetails.getContent());
                } catch (SAXException | TikaException | IOException e) {
                    err.println(e.getMessage());
                    content = noteDetails.getContent();
                }
                info = new ENote(
                        content,
                        length,
                        hash,
                        created,
                        guid,
                        notebookGuid,
                        title,
                        updated,
                        sequenceNum);
            }

            if(info != null) {
                result.add(info);
            }
        }
        return result;
    }

    @Override
    public ENote fillNote(ENote note){
        Note noteDetails;
        try{
            noteDetails = noteStore.getNote(note.getGuid(), true, true, true, true);
        } catch (EDAMSystemException | EDAMNotFoundException | EDAMUserException | TException e) {
            err.println(e.getMessage());
            return new ENote(note.getGuid(), note.getNotebookGuid(),e.getMessage());
        }

        int length = noteDetails.getContentLength();
        byte[] hash = noteDetails.getContentHash();
        long created = noteDetails.getCreated();
        String guid = noteDetails.getGuid();
        String notebookGuid = noteDetails.getNotebookGuid();
        String noteTitle = noteDetails.getTitle();
        long updated = noteDetails.getUpdated();
        int sequenceNum = noteDetails.getUpdateSequenceNum();
        String content;
        try{
            content = new TikaService().extract(noteDetails.getContent());
        } catch (SAXException | TikaException | IOException e) {
            err.println(e.getMessage());
            content = noteDetails.getContent();
        }

        return new ENote(
                content,
                length,
                hash,
                created,
                guid,
                notebookGuid,
                noteTitle,
                updated,
                sequenceNum);
    }
    /**
     * Search a user's notes and display the results.
     */
    @Override
    public ENote searchNote(String title) {
        // Searches are formatted according to the Evernote search grammar.
        // Learn more at
        // https://dev.evernote.com/doc/articles/search_grammar.php
        // http://dev.evernote.com/documentation/cloud/chapters/Searching_notes.php

        // In this example, we search for notes that have the term "EDAMDemo" in
        // the title.
        // This should return the sample note that we created in this demo app.
        String query = "intitle:"+title;

        // To search for notes with a specific tag, we could do something like
        // this:
        // String query = "tag:tagname";

        // To search for all notes with the word "elephant" anywhere in them:
        // String query = "elephant";

        NoteFilter filter = new NoteFilter();
        //todo: https://dev.evernote.com/doc/articles/searching_notes.php
        filter.setWords(query);
        filter.setOrder(NoteSortOrder.UPDATED.getValue());
        filter.setAscending(false);
        NotesMetadataResultSpec spec = new NotesMetadataResultSpec();
        spec.setIncludeTitle(true);
        NotesMetadataList notes;
        try{
            notes = noteStore.findNotesMetadata(filter, 0, 1, spec);
        } catch (EDAMSystemException | EDAMNotFoundException | EDAMUserException | TException e) {
            err.println(e.getMessage());
            return null;
        }
        out.println(notes.getNotesSize());
        Iterator<NoteMetadata> iter = notes.getNotesIterator();
        while (iter.hasNext()) {
            NoteMetadata note = iter.next();
            Note noteDetails;
            try{
                noteDetails = noteStore.getNote(note.getGuid(), true, true, true, true);
            } catch (EDAMSystemException | EDAMNotFoundException | EDAMUserException | TException e) {
                err.println(e.getMessage());
                return new ENote(note.getGuid(), note.getNotebookGuid(),e.getMessage());
            }

            int length = noteDetails.getContentLength();
            byte[] hash = noteDetails.getContentHash();
            long created = noteDetails.getCreated();
            String guid = noteDetails.getGuid();
            String notebookGuid = noteDetails.getNotebookGuid();
            String noteTitle = noteDetails.getTitle();
            long updated = noteDetails.getUpdated();
            int sequenceNum = noteDetails.getUpdateSequenceNum();
            String content;
            try{
                content = new TikaService().extract(noteDetails.getContent());
            } catch (SAXException | TikaException | IOException e) {
                err.println(e.getMessage());
                content = noteDetails.getContent();
            }

            return new ENote(
                    content,
                    length,
                    hash,
                    created,
                    guid,
                    notebookGuid,
                    noteTitle,
                    updated,
                    sequenceNum);
        }

        return null;
    }
}
