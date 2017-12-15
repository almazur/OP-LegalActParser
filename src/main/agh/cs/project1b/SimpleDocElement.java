package agh.cs.project1b;

import java.util.List;

public class SimpleDocElement extends AbstractDocElement{
    private Key key;


    SimpleDocElement(Key key){
        super();
        this.key = key;
    }

    public Key getKey(){
        return this.key;
    }

    public void printChildFromPath(List<Key> keys) throws IllegalArgumentException{
        Key key=keys.get(0);
        if(this.children.containsKey(key)){
            SimpleDocElement child=this.children.get(key);
            if(keys.size()==1){
                System.out.println(child.toString());
                child.printSubTree(" "," ");
            } else child.printChildFromPath(keys.subList(1,keys.size()));
        } else throw new IllegalArgumentException("Incorrect argument. No such element exists");
    }

    public String toString(){
        return this.key.toString() + " " + this.content;
    }
}
