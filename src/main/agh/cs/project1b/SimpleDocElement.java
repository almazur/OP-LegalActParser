package agh.cs.project1b;

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

    public String toString(){
        return this.key.toString() + " " + this.content;
    }
}
