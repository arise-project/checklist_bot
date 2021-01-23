package Service.Interface;

import Domain.Everynote.ENote;
import Domain.Everynote.ENotebook;

import java.util.ArrayList;

public interface IEverynoteService {
    void enableProduction();
    boolean isProduction();
    boolean auth();
    ArrayList<ENotebook> listNotebooks();
    ArrayList<ENote> listAllNotes();

    ArrayList<ENote> listNotes(ENotebook parent);

    ENote searchNote(String title);
}
