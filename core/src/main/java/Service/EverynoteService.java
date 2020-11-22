package Service;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;
import com.evernote.thrift.transport.TTransportException;

public class Everynote {

    public void execute() throws TException, EDAMSystemException, EDAMUserException {
        EvernoteAuth evernoteAuth = new EvernoteAuth(EvernoteService.SANDBOX, "S=s1:U=9630a:E=17d4759d4cc:C=175efa8a7a8:P=1cd:A=en-devtoken:V=2:H=844051c6f2be533c8fb7d423005e560e");
        ClientFactory factory = new ClientFactory(evernoteAuth);
        UserStoreClient userStore = factory.createUserStoreClient();

        boolean versionOk = userStore.checkVersion("Evernote EDAMDemo (Java)",
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
        if (!versionOk) {
            System.err.println("Incompatible Evernote client protocol version");
            System.exit(1);
        }

        // Set up the NoteStore client
        NoteStoreClient noteStore = factory.createNoteStoreClient();
    }
}
