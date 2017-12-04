package agh.cs.project1b;

import sun.java2d.pipe.SpanShapeRenderer;

import java.util.*;

public abstract class AbstractDocElement {
    protected HashMap<Key,SimpleDocElement> children;
    protected Levels childLevel;
    protected String content= "";


    AbstractDocElement(){
        this.children= new LinkedHashMap<>();
    }

    public void addContent(String content){
        if(this.content.isEmpty()) this.content=content;
        else this.content= dehyphenContent(this.content) + content;
    }

    public String dehyphenContent(String content){
        if(content.endsWith("-")) return content.replaceFirst("-$","");
        else return content+" ";
    }

    protected SimpleDocElement getLastChild(){
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

    public Boolean findChild(Levels level, String id) throws NoSuchFieldException {
        if(!this.children.isEmpty()){
            if(this.childLevel==level) {
                //SimpleDocElement child = iterator.next();
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

    /*public Boolean getChild(Levels level, String id) throws NoSuchFieldException {
        if(!this.children.isEmpty()){
            Iterator<SimpleDocElement> iterator = this.children.listIterator();
            //SimpleDocElement child = iterator.next();

            if(this.getChildLevel()==level) {
                return !child.getKey().getId().equals(id);
            }
            Boolean foundElem=false;
            while(iterator.hasNext() && !foundElem){
                SimpleDocElement child = iterator.next();
                foundElem = child.findChild(level,id);
            }
            return foundElem;
        }
        else throw new NoSuchFieldException("This element has no substructure");
    }*/

    /*public Levels getChildLevel() throws NoSuchFieldException{
        if(this.children.isEmpty()) throw new NoSuchFieldException("This document element has no substructure");
        else return this.childLevel;
    }*/

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
