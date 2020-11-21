package Service.Interface;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IDriveService {
    void list() throws IOException, GeneralSecurityException;
}
