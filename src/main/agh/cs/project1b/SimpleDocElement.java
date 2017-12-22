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
        return this.key.toString() + " " + this.content;
    }
}
