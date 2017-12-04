package agh.cs.project1b;

import java.util.ArrayList;
import java.util.LinkedList;

public class Document extends AbstractDocElement {

    Document() {
        super();
    }

    public void printTableOfContents() throws NoSuchFieldException {
        System.out.println("SPIS TRESCI");
        if(this.childLevel == Levels.ROZDZIAL) this.printChildren("* ");
        else {
            for(Key key : this.children.keySet()){
                System.out.print("* ");
                printSectionContent(key.getId());
            }
        }
    }

    public void printSectionContent(String id){
        SimpleDocElement section = this.children.get(new Key(Levels.DZIAL,id));
        System.out.println(section.toString());
        if(section.childLevel==Levels.ROZDZIAL) section.printChildren("    - ");
    }

    public void printTree(){
        if(!this.children.isEmpty()){
            System.out.println(this.content + " " + " zawiera: ");
            printChildren("");
            for(SimpleDocElement child : this.children.values()){
                child.printSubTree("","  ");
            }
        }
    }

    public void explore(ArrayList<Object> args) throws NoSuchFieldException, IllegalArgumentException{
        Boolean printContentMode = (Boolean) args.get(0);

        if(!printContentMode) {
            if (args.size()==1) { //args: [false]
                printTableOfContents();
                return;
            }
            if(this.childLevel != Levels.DZIAL) { //args: [false,">>id of DZIAL<<"]
                throw new IllegalArgumentException("Document has no DZIAŁ's");
            }
            printSectionContent((String)args.get(1));
            return;
        }
        if(args.size()==3) {
            Levels level = (Levels) args.get(1);
            System.out.println(getChild(level, (String) args.get(2)));
            getChild(level, (String) args.get(2)).printSubTree("","   ");
        }
    }
}
