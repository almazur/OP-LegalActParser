package agh.cs.project1b;

import org.apache.commons.cli.CommandLine;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractRoot extends AbstractDocElement {
    protected HashMap<Key,SimpleDocElement> articles;

    AbstractRoot() {
        super();
        this.articles=new LinkedHashMap<>();
    }

    public void addArticle(SimpleDocElement article) {
        this.articles.put(article.getKey(), article);
    }

    public void printTableOfContents(){
        System.out.println("SPIS TRESCI");
        for(SimpleDocElement section : this.children.values()){
            System.out.println("* "+section.toString());
            for(SimpleDocElement subSection : section.children.values()) {
                if(subSection.getKey().getLevel()==Levels.ROZDZIAL) {
                    System.out.println(" -  " + subSection.toString());
                    for(SimpleDocElement subSubSection : section.children.values()) {
                        if(subSubSection.getKey().getLevel()==Levels.TYTUL)
                            System.out.println("  " + subSubSection.getKey().toString());
                    }
                }
                else if(subSection.getKey().getLevel()==Levels.TYTUL)
                    System.out.println(" - " + subSection.getKey().toString());
            }
        }
    }

    public void printRange(String firstId, String lastId) throws IllegalArgumentException{
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
                System.out.println(article.toString());
                article.printSubTree(" ","  ");
            }
            else break;
        }
    }

    //protected abstract void printTableOfSection(String id);

    /*public abstract void printTree(); /*{
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

    public void explore(CommandLine cmd) throws IllegalArgumentException{
        if (cmd.hasOption("r")) {
            String[] range = cmd.getOptionValues("r");
            printRange(range[0],range[1]);
        }
        if (cmd.hasOption("t")) {
            String dzial = cmd.getOptionValue("t","");
            if (dzial.isEmpty()) printTableOfContents();
            else System.out.println("dzial");//printTableOfSection(dzial);
        }
        if(cmd.hasOption("s")){
            String[] path = cmd.getOptionValues("s");
            if(path.length % 2 == 1) throw new IllegalArgumentException("Incorrect path");

            List<Key> keys = new ArrayList<>();
            List<String> pathList = Arrays.asList(path);
            pathList = pathList.stream().map(str->str.replaceAll("[).,]*","")).collect(Collectors.toList());

            Iterator<String> iterator = pathList.listIterator();
            while(iterator.hasNext()){
                //System.out.println("START");
                String levelStr = iterator.next();
                String id = iterator.next();
                if(!levelStr.matches("art|ust|pkt|lit")) throw new IllegalArgumentException("Incorrect path");
                switch(levelStr) {
                    case "art": {
                        keys.add(new Key(Levels.ART,id));
                        break;
                    }
                    case "ust": {
                        keys.add(new Key(Levels.UST,id));
                        break;
                    }
                    case "pkt": {
                        keys.add(new Key(Levels.PKT,id));
                        break;
                    }
                    default : {
                        keys.add(new Key(Levels.LIT,id));
                        break;
                    }
                }
            }
            if(!this.articles.containsKey(keys.get(0))) throw new IllegalArgumentException("Incorrect argument. No such element exists");
            SimpleDocElement article = this.articles.get(keys.get(0));

            if(keys.size()==1) {
                System.out.println(article.toString());
                article.printSubTree(" "," ");
            } else {
                article.printChild(keys.subList(1,keys.size()));
            }
        }
    }
}
