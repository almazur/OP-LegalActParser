package agh.cs.project1b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class AbstractRoot extends AbstractDocElement {
    protected HashMap<Key,SimpleDocElement> articles;

    AbstractRoot() {
        super();
        this.articles=new LinkedHashMap<>();
    }

    public void addArticle(SimpleDocElement article) {
        this.articles.put(article.getKey(), article);
    }

    public abstract void printTableOfContents() throws NoSuchFieldException;

    //protected abstract void printTableOfSection(String id);

    public abstract void printTree(); /*{
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
    }*/

    public abstract void explore(ArrayList<Object> args) throws NoSuchFieldException, IllegalArgumentException;/*{
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
    }*/
}
