package Service.Interface;

import Domain.Everynote.ENote;
import Domain.Everynote.ENotebook;

import java.util.ArrayList;

public interface IEverynoteService {
    void EnableProduction();
    boolean auth();
    ArrayList<ENotebook> listNotebooks();
    ArrayList<ENote> listAllNotes();
    ENote searchNotes(String title);
}
