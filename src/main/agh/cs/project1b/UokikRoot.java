package agh.cs.project1b;

import org.apache.commons.cli.CommandLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UokikRoot extends AbstractRoot{//AbstractDocElement {
    //private HashMap<Key,SimpleDocElement> articles;

    UokikRoot() {
        super();
        //this.articles=new LinkedHashMap<>();
    }

    /*public void addArticle(SimpleDocElement article) {
        this.articles.put(article.getKey(), article);
    }*/

    /*public void printTableOfContents(){// throws NoSuchFieldException {
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
    }*/

    /*private void printTableOfSection(String id){
        SimpleDocElement section = this.children.get(new Key(Levels.DZIAL,id));
        System.out.println(section.toString());
        if(section.childLevel==Levels.ROZDZIAL) section.printChildren("    - ");
    }*/

    /*public void printTree() {
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

    //public void explore(ArrayList<Object> args) throws NoSuchFieldException, IllegalArgumentException{
    /*public void explore(CommandLine cmd) throws IllegalArgumentException {
        super.explore(cmd);
        if (cmd.hasOption("t")) {
            String dzial = cmd.getOptionValue("t","");
            if (dzial.isEmpty()) printTableOfContents();
            else printTableOfSection(dzial);
        }
        /*if (cmd.hasOption("r")) {
            String[] range = cmd.getOptionValues("r");
            //if(range.length!=2) throw new IllegalArgumentException("Wrong number of arguments");
            printRange(range[0],range[1]);
        }
    }*/
}
