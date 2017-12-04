package agh.cs.project1b;

public class SimpleDocElement extends AbstractDocElement{
    //protected List<SimpleDocElement> children;//=null;
    protected Key key; //key representing SimpleDocElem


    SimpleDocElement(Key key){
        super();
        this.key = key;
    }

    public Key getKey(){
        return this.key;
    }

    public void printSubTree(){
        if(!this.children.isEmpty()){
            System.out.println("\n"+this.key.toString() + " " + this.content + " " + " zawiera: ");
            printChildren("");
            for(SimpleDocElement child : this.children.values()){
                child.printSubTree();
            }
        }
    }

    public String toString(){
        return this.key.toString() + " " + this.content;
    }
}
