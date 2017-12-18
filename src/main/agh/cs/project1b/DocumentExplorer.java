package agh.cs.project1b;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.cli.CommandLine;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DocumentExplorer {
    private String content;
    public Levels childLevel;
    private HashMap<Key,SimpleDocElement> children;
    private HashMap<Key,SimpleDocElement> articles;
    private String lineSeparator;

    DocumentExplorer(DocumentRoot document){
        this.childLevel=document.getChildLevel();
        this.content=document.getContent();
        this.children=document.getChildren();
        this.articles=document.getArticles();
        this.lineSeparator= System.getProperty("line.separator");
    }


    //private void printTableOfContents(){
    private String printTableOfContents(){
        //System.out.println(this.childLevel.toString());
        String str="SPIS TRESCI"+lineSeparator;
        //System.out.println("SPIS TRESCI");
        if(this.childLevel==Levels.DZIAL)
            for(SimpleDocElement section : this.children.values()){
                //printTableOfSection(section.getKey().getId());
                str=str+printTableOfSection(section.getKey().getId());
            }
        else
            for (SimpleDocElement section : this.children.values()) {
                //System.out.println("* " + section.toString());
                str=str+"* " + section.toString()+lineSeparator;
                for (SimpleDocElement subSection : section.children.values())
                    if (subSection.getKey().getLevel() == Levels.TYTUL)
                        //System.out.println(" - " + subSection.getKey().toString());
                        str=str+" - " + subSection.getKey().toString()+lineSeparator;
            }
            return str;
    }

    //private void printTableOfSection(String id) throws IllegalArgumentException{
    private String printTableOfSection(String id) throws IllegalArgumentException{
        if(!(this.childLevel==Levels.DZIAL))
            throw new IllegalArgumentException("Incorrect argument. Document has no dzial's");
        SimpleDocElement section = this.children.get(new Key(Levels.DZIAL,id));
        //System.out.println(section.toString());
        String str=section.toString()+lineSeparator;
        for(SimpleDocElement subSection : section.children.values()){
            if(subSection.getKey().getLevel()==Levels.ROZDZIAL) str=str+" - "+subSection.toString()+lineSeparator;
                //System.out.println(" - "+subSection.toString());
        }
        return str;
    }

    //private void printRange(String firstId, String lastId) throws IllegalArgumentException{
    private String printRange(String firstId, String lastId) throws IllegalArgumentException{
        String str="";
        List<SimpleDocElement> articlesCopy = new ArrayList<>(articles.values());
        Key firstKey = new Key(Levels.ART,firstId);
        Key lastKey = new Key(Levels.ART,lastId);
        if(!this.articles.containsKey(firstKey))
            throw new IllegalArgumentException("Incorrect argument. Art. "+firstId+" does not exist");
        if(!this.articles.containsKey(lastKey))
            throw new IllegalArgumentException("Incorrect argument. Art. "+lastId+" does not exist");
        if(articlesCopy.indexOf(this.articles.get(firstKey))
                > articlesCopy.indexOf(this.articles.get(lastKey)))
            throw new IllegalArgumentException("Incorrect argument. Wrong range");

        Iterator<SimpleDocElement> iterator = articlesCopy.listIterator(articlesCopy.indexOf(this.articles.get(firstKey)));
        SimpleDocElement article;
        while(iterator.hasNext()){
            article=iterator.next();
            if(articlesCopy.indexOf(article)<=articlesCopy.indexOf(this.articles.get(lastKey))){
                //System.out.println(article.toString());
                str=str+article.toString()+lineSeparator;
                str=str+article.printSubTree(" ","  ",lineSeparator);
                //article.printSubTree(" ","  ");
            }
            else break;
        }
        return str;
    }

    //public void explore(CommandLine cmd) throws IllegalArgumentException{
    public String explore(CommandLine cmd) throws IllegalArgumentException{
        if (cmd.hasOption("r")) {
            String[] range = cmd.getOptionValues("r");
            //printRange(range[0],range[1]);
            return printRange(range[0],range[1]);
        }
        if (cmd.hasOption("t")) {
            String dzial = cmd.getOptionValue("t","");
            if (dzial.isEmpty()) return printTableOfContents();
            else {
                //printTableOfSection(dzial);
                return printTableOfSection(dzial);
            }
        }
        if(cmd.hasOption("s")){
            String[] path = cmd.getOptionValues("s");
            List<Key> keys = new CMDParserToolKit().convertToKeys(path);

            if(!this.articles.containsKey(keys.get(0)))
                throw new IllegalArgumentException("Incorrect argument. No such element exists");
            SimpleDocElement article = this.articles.get(keys.get(0));

            if(keys.size()==1) {
                System.out.println(article.toString());
                //article.printSubTree(" "," ",lineSeparator);
                return article.printSubTree(" "," ",lineSeparator);
            } else {
                //article.printChildFromPath(keys.subList(1,keys.size()));
                return article.printChildFromPath(keys.subList(1,keys.size()),lineSeparator);
            }
        }
        return "";
    }

}
