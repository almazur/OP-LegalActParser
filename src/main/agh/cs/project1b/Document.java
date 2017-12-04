package agh.cs.project1b;

public class Document extends AbstractDocElement {

    Document() {
        super();
    }

    public void printTableOfContents() throws NoSuchFieldException {
        System.out.println("SPIS TRESCI");
        if(this.childLevel == Levels.ROZDZIAL) this.printChildren("* ");
        else {
            for(SimpleDocElement child : this.children.values()){
                System.out.println("* "+child.toString());
                if(child.childLevel == Levels.ROZDZIAL) child.printChildren("   - ");
            }
        }
    }
    public void printTree(){
        if(!this.children.isEmpty()){
            System.out.println(this.content + " " + " zawiera: ");
            printChildren("");
            for(SimpleDocElement child : this.children.values()){
                child.printSubTree();
            }
        }
    }
}
