package Service.Interface;

import Domain.Drive.GFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public interface IDriveService {
    void list() throws IOException, GeneralSecurityException;
    ArrayList<GFile> search(String pattern) throws IOException, GeneralSecurityException;
    String read(GFile file) throws IOException, GeneralSecurityException;
}
