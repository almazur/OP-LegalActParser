package agh.cs.project1b;


public class SectionDocElement extends SimpleDocElement {
    private String firstId,lastId;

    SectionDocElement(Key key){
        super(key);
        this.firstId="";
        this.lastId="";
    }

    public void setLastId(String lastId){
        if(this.firstId.isEmpty()) this.firstId=lastId;
        this.lastId=lastId;
    }

    public String getFirstId(){
        return this.firstId;
    }

    public String getLastId(){
        return this.lastId;
    }
}
