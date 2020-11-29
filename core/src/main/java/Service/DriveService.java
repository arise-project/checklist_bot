package Service;

import Domain.Drive.GFile;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DriveService implements Service.Interface.IDriveService {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(
            DriveScopes.DRIVE_METADATA_READONLY);

    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets;
        try (InputStream in = DriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH)) {
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        }

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Override
    public void list() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        Drive.Files.List request = service.files().list();
        System.out.println("Files:");
        do {
            // Print the names and IDs for up to 10 files.
            FileList result = request
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            request.setPageToken(result.getNextPageToken());
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                for (File file : files) {
                    System.out.printf("%s (%s)\n", file.getName(), file.getId());
                }
            }
        }
        while (request.getPageToken() != null &&
                request.getPageToken().length() > 0);

    }

    @Override
    public ArrayList<GFile> search(String pattern) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = getCredentials(HTTP_TRANSPORT);
        /* credential.refreshToken(); */
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        Drive.Files.List request = service.files().list();

        String searchQuery = "(name contains '"+pattern+"')";
        request.setQ(searchQuery);
        ArrayList<GFile> list = new ArrayList<>();

        System.out.println("Files:");
        do {
            FileList result = request
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            request.setPageToken(result.getNextPageToken());
            if (files == null || files.isEmpty()) {
                System.out.println("No files found: "+ searchQuery);
            } else {
                for (File file : files) {
                    var f = new GFile();
                    f.setId(file.getId());
                    f.setName(file.getName());
                    list.add(f);
                }
            }
        }
        while (request.getPageToken() != null &&
                request.getPageToken().length() > 0);
        return list;
    }

    @Override
    public String read(GFile gfile) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        service
                .files()
                .export(gfile.getId(), "application/vnd.google-apps.document")
                .executeMediaAndDownloadTo(outputStream);
        String content = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        outputStream.close();
        //com.google.api.client.http.HttpResponseException: 403 Forbidden
        //{
        // "error": {
        //  "errors": [
        //   {
        //    "domain": "global",
        //    "reason": "fileNotDownloadable",
        //    "message": "Only files with binary content can be downloaded. Use Export with Docs Editors files.",
        //    "locationType": "parameter",
        //    "location": "alt"
        //   }
        //  ],
        //  "code": 403,
        //  "message": "Only files with binary content can be downloaded. Use Export with Docs Editors files."
        // }
        //}

        //com.google.api.client.googleapis.json.GoogleJsonResponseException: 403 Forbidden
        //{
        //  "code" : 403,
        //  "errors" : [ {
        //    "domain" : "global",
        //    "message" : "Insufficient Permission: Request had insufficient authentication scopes.",
        //    "reason" : "insufficientPermissions"
        //  } ],
        //  "message" : "Insufficient Permission: Request had insufficient authentication scopes."
        //}

        //com.google.api.client.googleapis.json.GoogleJsonResponseException: 403 Forbidden
        //{
        //  "code" : 403,
        //  "errors" : [ {
        //    "domain" : "global",
        //    "message" : "Insufficient Permission: Request had insufficient authentication scopes.",
        //    "reason" : "insufficientPermissions"
        //  } ],
        //  "message" : "Insufficient Permission: Request had insufficient authentication scopes."
        //}
        return content;
    }
}