package Domain.Everynote;

public class ENote {
    private final String content;
    private final int length;
    private final byte[] hash;
    private final long created;
    private final String guid;
    private final String notebookGuid;
    private final String title;
    private final long updated;
    private final int sequenceNum;
    private final String errorMessage;
    private final boolean hasError;

    public ENote(String guid, String notebookGuid, String errorMessage)
    {
        hasError = true;
        this.errorMessage = errorMessage;
        this.content = null;
        this.length = -1;
        this.hash = null;
        this.created = -1;
        this.guid = guid;
        this.notebookGuid = notebookGuid;
        this.title = null;
        this.updated = -1;
        this.sequenceNum = -1;
    }
    public ENote(String content, int length, byte[] hash, long created, String guid, String notebookGuid, String title, long updated, int sequenceNum){

        this.content = content;
        this.length = length;
        this.hash = hash;
        this.created = created;
        this.guid = guid;
        this.notebookGuid = notebookGuid;
        this.title = title;
        this.updated = updated;
        this.sequenceNum = sequenceNum;
        errorMessage = null;
        hasError = false;
    }

    public String getContent() {
        return content;
    }

    public int getLength() {
        return length;
    }

    public byte[] getHash() {
        return hash;
    }

    public long getCreated() {
        return created;
    }

    public String getGuid() {
        return guid;
    }

    public String getNotebookGuid() {
        return notebookGuid;
    }

    public String getTitle() {
        return title;
    }

    public long getUpdated() {
        return updated;
    }

    public int getSequenceNum() {
        return sequenceNum;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isHasError() {
        return hasError;
    }
}
