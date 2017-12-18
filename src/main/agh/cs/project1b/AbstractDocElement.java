package agh.cs.project1b;

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

    protected SimpleDocElement getLastChild(){
        if(this.children.isEmpty()) return null;
        else{
            List<SimpleDocElement> childrenCopy = new LinkedList<>(this.children.values());
            return childrenCopy.get(childrenCopy.size()-1);
        }
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

    //protected void printSubTree(String initIndentation,String indentation){
    protected String printSubTree(String initIndentation,String indentation,String lineSeparator){
        String str="";
        if(!this.children.isEmpty()){
            for(SimpleDocElement child : this.children.values()){
                str=str+initIndentation+child.toString()+lineSeparator;
                //System.out.println(initIndentation+child.toString());
                str=str+child.printSubTree(initIndentation+indentation,indentation,lineSeparator);
            }
        }
        return str;
    }

    public String toString(){
        return this.content;
    }
}
