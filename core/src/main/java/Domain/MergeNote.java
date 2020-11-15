package Domain;

public class MergeNote {

    private Note note;

    public Note getNote(){
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    private Note oldNote;

    public Note getOldNote(){
        return oldNote;
    }

    public void setOldNote(Note note) {
        this.oldNote = oldNote;
    }

    private MergeType mergeType;

    public MergeType getMergeType(){
        return mergeType;
    }

    public void setMergeType(MergeType type){
        mergeType = type;
    }
}
