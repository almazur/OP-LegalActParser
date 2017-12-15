package agh.cs.project1b;

import java.util.List;
import java.util.NoSuchElementException;

public class SimpleDocElement extends AbstractDocElement{
    protected Key key;


    SimpleDocElement(Key key){
        super();
        this.key = key;
    }

    public Key getKey(){
        return this.key;
    }

    public void printSubTree(String initIndentation,String indentation){
        if(!this.children.isEmpty()){
            for(SimpleDocElement child : this.children.values()){
                System.out.println(initIndentation+child.toString());
                child.printSubTree(initIndentation+indentation,indentation);
            }
        }
    }

    public void printChild(List<Key> keys) throws IllegalArgumentException{
        Key key=keys.get(0);
        if(this.children.containsKey(key)){
            SimpleDocElement child=this.children.get(key);
            if(keys.size()==1){
                System.out.println(child.toString());
                child.printSubTree(" "," ");
            } else child.printChild(keys.subList(1,keys.size()));
        } else throw new IllegalArgumentException("Incorrect argument. No such element exists");
    }

    public String toString(){
        return this.key.toString() + " " + this.content;
    }
}
