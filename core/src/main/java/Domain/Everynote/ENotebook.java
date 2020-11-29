package Domain.Everynote;

public class ENotebook {
    private String guid;
    private String name;

    public ENotebook(String guid, String name){
        this.guid = guid;
        this.name = name;
    }

    public String getGuid(){
        return guid;
    }

    public String getName(){
        return name;
    }
}
