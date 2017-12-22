package agh.cs.project1b;

// SimpleDocElement represents document element i.e. dzial, rozdzial, artykul etc.
public class SimpleDocElement extends AbstractDocElement{
    private Key key;

    SimpleDocElement(Key key){
        super();
        this.key = key;
    }

    public Key getKey(){
        return this.key;
    }

    public String toString(){
        if(this.content.isEmpty()) return this.key.toString();
        Level level = this.getKey().getLevel();
        if(level==Level.DZIAL || level==Level.ROZDZIAL || level==Level.ART)
            return this.key.toString() + System.getProperty("line.separator") + this.content;
        return this.key.toString() + " " + this.content;
    }
}
