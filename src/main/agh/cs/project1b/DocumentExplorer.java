package agh.cs.project1b;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.cli.CommandLine;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DocumentExplorer {
    /*private String content;
    private Levels childLevel;
    private HashMap<Key,SimpleDocElement> children;
    private HashMap<Key,SimpleDocElement> articles;*/
    private DocumentRoot document;
    private String lineSeparator;

    DocumentExplorer(DocumentRoot document){
        /*this.childLevel=document.getChildLevel();
        this.content=document.getContent();
        this.children=document.getChildren();
        this.articles=document.getArticles();*/
        this.document=document;
        this.lineSeparator= System.getProperty("line.separator");
    }

    private String getTableOfContents(){
        StringBuilder str = new StringBuilder("SPIS TRESCI" + lineSeparator);
        for (SimpleDocElement section : this.document.getChildren().values())
            str.append(getTableOfSection(section));
        return str.toString();
    }

    private String getTableOfSection(SimpleDocElement elem){
        //StringBuilder str= new StringBuilder("");
        //System.out.println("ELEM: " + elem.toString());
        if(elem.getKey().getLevel().lessThen(Levels.TYTUL)){
            //System.out.println("RETURN");
            //System.out.println(elem.toString()+lineSeparator);
            return "";//elem.toString()+lineSeparator;
        } else {
            //System.out.println("INSIDE IF, CHILD LEVEL: "+elem.getChildLevel().toString());
            StringBuilder str= new StringBuilder("");
            for(SimpleDocElement child : elem.children.values()){//this.children.values()){
                /*if(child.getKey().getLevel()!=Levels.ART)*/ str.append(getTableOfSection(child));
            }
            //System.out.println(elem.toString()+lineSeparator+str.toString());
            return elem.toString()+lineSeparator+str.toString();
        }
        //return str.toString();
    }


    private String printTableOfContents(){
        StringBuilder str= new StringBuilder("SPIS TRESCI" + lineSeparator);
        if(this.document.childLevel==Levels.DZIAL)//if(this.childLevel==Levels.DZIAL)
            for(SimpleDocElement section : this.document.children.values()){//this.children.values()){
                str.append(printTableOfSection(section.getKey().getId()));
            }
        else
            for (SimpleDocElement section : this.document.children.values()) {//this.children.values()) {
                str.append("* ").append(section.toString()).append(lineSeparator);
                for (SimpleDocElement subSection : section.children.values())
                    if (subSection.getKey().getLevel() == Levels.TYTUL)
                        str.append(" - ").append(subSection.getKey().toString()).append(lineSeparator);
            }
            return str.toString();
    }

    private String printTableOfSection(String id) throws IllegalArgumentException{
        if(!(this.document.childLevel==Levels.DZIAL))//if(!(this.childLevel==Levels.DZIAL))
            throw new IllegalArgumentException("Incorrect argument. Document has no dzial's");
        SimpleDocElement section = this.document.children.get(new Key(Levels.DZIAL,id));//this.children.get(new Key(Levels.DZIAL,id));
        StringBuilder str= new StringBuilder(section.toString() + lineSeparator);
        for(SimpleDocElement subSection : section.children.values()){
            if(subSection.getKey().getLevel()==Levels.ROZDZIAL) str.append(" - ").append(subSection.toString()).append(lineSeparator);
        }
        return str.toString();
    }

    private String printRange(String firstId, String lastId) throws IllegalArgumentException{
        String str="";
        List<SimpleDocElement> articlesCopy = new ArrayList<>(this.document.getArticles().values());//new ArrayList<>(articles.values());
        Key firstKey = new Key(Levels.ART,firstId);
        Key lastKey = new Key(Levels.ART,lastId);
        if(!this.document.getArticles().containsKey(firstKey))//if(!this.articles.containsKey(firstKey))
            throw new IllegalArgumentException("Incorrect argument. Art. "+firstId+" does not exist");
        if(!this.document.getArticles().containsKey(lastKey))//if(!this.articles.containsKey(lastKey))
            throw new IllegalArgumentException("Incorrect argument. Art. "+lastId+" does not exist");
        if(articlesCopy.indexOf(this.document.getArticles().get(firstKey))//if(articlesCopy.indexOf(this.articles.get(firstKey))
                > articlesCopy.indexOf(this.document.getArticles().get(lastKey)))//> articlesCopy.indexOf(this.articles.get(lastKey)))
            throw new IllegalArgumentException("Incorrect argument. Wrong range");

        Iterator<SimpleDocElement> iterator = articlesCopy.listIterator(articlesCopy.indexOf(this.document.getArticles().get(firstKey)));
                //articlesCopy.listIterator(articlesCopy.indexOf(this.articles.get(firstKey)));
        SimpleDocElement article;
        while(iterator.hasNext()){
            article=iterator.next();
            if(articlesCopy.indexOf(article)<=articlesCopy.indexOf(this.document.getArticles().get(lastKey))){//this.articles.get(lastKey))){
                str=str+article.toString()+lineSeparator;
                str=str+article.printSubTree(" ","  ",lineSeparator);
            }
            else break;
        }
        return str;
    }

    public String explore(CommandLine cmd) throws IllegalArgumentException{
        if (cmd.hasOption("r")) {
            String[] range = cmd.getOptionValues("r");
            return printRange(range[0],range[1]);
        }
        if (cmd.hasOption("t")) {
            String dzial = cmd.getOptionValue("t","");
            if (dzial.isEmpty()){
                //String str = "SPIS TRESCI" + lineSeparator;
                return getTableOfContents();//str+getTableOfContents(document);//printTableOfContents();
            }
            else {
                return printTableOfSection(dzial);
            }
        }
        if(cmd.hasOption("s")){
            String[] path = cmd.getOptionValues("s");
            List<Key> keys = new CMDParserToolKit().convertToKeys(path);

            if(!this.document.getArticles().containsKey(keys.get(0)))//if(!this.articles.containsKey(keys.get(0)))
                throw new IllegalArgumentException("Incorrect argument. No such element exists");
            SimpleDocElement article = this.document.getArticles().get(keys.get(0));//this.articles.get(keys.get(0));

            if(keys.size()==1) {
                System.out.println(article.toString());
                return article.printSubTree(" "," ",lineSeparator);
            } else {
                return article.printChildFromPath(keys.subList(1,keys.size()),lineSeparator);
            }
        }
        return "";
    }

}
