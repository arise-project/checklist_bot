package Service.Interface;

import Domain.Everynote.ENote;
import Domain.Root;

public interface ITextService {
    void parseTextFile(Root root, String fileName);

    void parseENote(Root root, ENote note);
}
