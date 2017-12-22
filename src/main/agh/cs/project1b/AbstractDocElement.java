package agh.cs.project1b;

import java.util.*;

public abstract class AbstractDocElement {
    protected HashMap<Key,SimpleDocElement> children;
    protected Level childLevel; //level of "highest" children
    protected String content;


    AbstractDocElement(){
        this.children= new LinkedHashMap<>();
        this.content="";
    }

    public void addChild(SimpleDocElement child){
        if(this.children.isEmpty() || !child.getKey().getLevel().lessThen(this.childLevel)){
            this.children.put(child.getKey(),child);
            this.childLevel=child.getKey().getLevel();
        } else{
            SimpleDocElement lastElem=getLastChild();
            lastElem.addChild(child);
        }
    }
    protected SimpleDocElement getLastChild(){
        List<SimpleDocElement> childrenCopy = new LinkedList<>(this.children.values());
        return childrenCopy.get(childrenCopy.size()-1);
    }

    public void setContent(String content){
        this.content=content;
    }

    public HashMap<Key, SimpleDocElement> getChildren() {
        return this.children;
    }
    public String getContent(){
        return this.content;
    }

    public String toString(){
        return this.content;
    }
}
