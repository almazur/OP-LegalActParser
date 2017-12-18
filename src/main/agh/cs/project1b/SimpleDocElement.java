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

    //public void printChildFromPath(List<Key> keys) throws IllegalArgumentException{
    public String printChildFromPath(List<Key> keys,String lineSeparator) throws IllegalArgumentException{
        String str="";
        Key key=keys.get(0);
        if(this.children.containsKey(key)){
            SimpleDocElement child=this.children.get(key);
            if(keys.size()==1){
                //System.out.println(child.toString());
                str=str+child.toString()+lineSeparator;
                //child.printSubTree(" "," ");
                str=str+child.printSubTree(" "," ",lineSeparator);
            } else str=str+child.printChildFromPath(keys.subList(1,keys.size()),lineSeparator);
            //child.printChildFromPath(keys.subList(1,keys.size()),lineSeparator);
        } else throw new IllegalArgumentException("Incorrect argument. No such element exists");
        return str;
    }

    public String toString(){
        return this.key.toString() + " " + this.content;
    }
}
