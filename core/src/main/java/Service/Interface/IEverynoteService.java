package Service.Interface;

import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;

public interface IEverynoteService {
    void auth() throws TException, EDAMSystemException, EDAMUserException;

    void listNotes() throws Exception;

    void searchNotes() throws Exception;
}
