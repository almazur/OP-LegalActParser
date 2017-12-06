package agh.cs.project1b;

import sun.java2d.pipe.SpanShapeRenderer;

import java.util.*;

public abstract class AbstractDocElement {
    protected HashMap<Key,SimpleDocElement> children;
    protected Levels childLevel;
    protected String content;


    AbstractDocElement(){
        this.children= new LinkedHashMap<>();
        this.content="";
    }


    public void addContent(String content){
        if(this.content.isEmpty()) this.content=content;
        else this.content= dehyphenContent(this.content) + content;
    }

    private String dehyphenContent(String content){
        if(content.endsWith("-")) return content.replaceFirst("-$","");
        else return content+" ";
    }

    private SimpleDocElement getLastChild(){
        if(this.children.isEmpty()) return null;
        else{
            List<SimpleDocElement> childrenCopy = new LinkedList<>(this.children.values());
            return childrenCopy.get(childrenCopy.size()-1);
        }
    }

    public void addChild(SimpleDocElement child){
        if(this.children.isEmpty() || this.childLevel.equals(child.key.getLevel())){
            this.children.put(child.getKey(),child);
            this.childLevel=child.getKey().getLevel();
        } else{
            SimpleDocElement lastElem=getLastChild();
            lastElem.addChild(child);
        }
    }

    protected Boolean findChild(Levels level, String id) throws NoSuchFieldException {
        if(!this.children.isEmpty()){
            if(this.childLevel==level) {
                return this.children.containsKey(new Key(level,id));
            }else {
                Iterator<SimpleDocElement> iterator = new LinkedList<>(this.children.values()).listIterator();
                SimpleDocElement child = iterator.next();
                Boolean foundElem = false;
                while (iterator.hasNext() && !foundElem) {
                    foundElem = child.findChild(level, id);
                    child = iterator.next();
                }
                return foundElem || child.findChild(level, id);
            }
        } else return false;
    }

    public SimpleDocElement getChild(Levels level, String id) throws NoSuchFieldException {
        if(findChild(level,id)){
            if(this.childLevel==level) {
                return this.children.get(new Key(level,id));
            }else {
                Iterator<SimpleDocElement> iterator = new LinkedList<>(this.children.values()).listIterator();
                SimpleDocElement child = iterator.next();
                while (iterator.hasNext() && !child.findChild(level,id)) {
                    child = iterator.next();
                }
                return child.getChild(level, id);
            }
        }
        else throw new NoSuchFieldException("No such element exists");
    }

    public void printChildren(String prefix){
        if(!this.children.isEmpty()){
            for(AbstractDocElement child : this.children.values()){
                System.out.println(prefix+child.toString());
            }
        }
    }

    public String toString(){
        return this.content;
    }
}
