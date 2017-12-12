package agh.cs.project1b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UokikRoot extends AbstractRoot{//AbstractDocElement {
    private HashMap<Key,SimpleDocElement> articles;

    UokikRoot() {
        super();
        this.articles=new LinkedHashMap<>();
    }

    public void addArticle(SimpleDocElement article) {
        this.articles.put(article.getKey(), article);
    }

    public void printTableOfContents() throws NoSuchFieldException {
        System.out.println("SPIS TRESCI");
        if(this.childLevel == Levels.ROZDZIAL){
            for(SimpleDocElement section : this.children.values()){
                System.out.println("* "+section.toString());
                if(section.childLevel==Levels.TYTUL){
                    for(SimpleDocElement title : section.children.values()) {
                        System.out.println("   " + title.getKey().toString());
                    }
                }
            }
        }
        else {
            for(Key key : this.children.keySet()){
                System.out.print("* ");
                printTableOfSection(key.getId());
            }
        }
    }

    private void printTableOfSection(String id){
        SimpleDocElement section = this.children.get(new Key(Levels.DZIAL,id));
        System.out.println(section.toString());
        if(section.childLevel==Levels.ROZDZIAL) section.printChildren("    - ");
    }

    public void printTree() {
        System.out.println(this.content);
        for (SimpleDocElement section : this.children.values()) {
            System.out.println(section.toString());
            if (section.childLevel==Levels.ROZDZIAL) {
                for (SimpleDocElement chapter : section.children.values()) {
                    System.out.println("  " + chapter.toString());
                    section.printSubTree("  ","  ");
                }
            } else {
                section.printSubTree("  ","  ");
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
                throw new IllegalArgumentException("KonstRoot has no DZIAŁ's");
            }
            printTableOfSection((String)args.get(1));
            return;
        }
        if(args.size()==3) {
            Levels level = (Levels) args.get(1);
            System.out.println(getChild(level, (String) args.get(2)));
            getChild(level, (String) args.get(2)).printSubTree("","   ");
        }
    }
}
