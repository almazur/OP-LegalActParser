package agh.cs.project1b;

import java.util.ArrayList;

// TOC stands for Table Of Contents
public class TOCGetter {
    private String lineSeparator;
    private String dots;
    private String indentation;

    TOCGetter(){
        this.lineSeparator= System.getProperty("line.separator");
        this.dots="..................................................................................";
        this.indentation = "        "; // indentation is used to make printed text clearer
    }

    public String getTableOfContents(DocumentRoot document){
        StringBuilder str = new StringBuilder("SPIS TRESCI" + lineSeparator);
        for (SimpleDocElement section : document.getChildren().values())
            str.append(getTableOfSection(section));
        return str.toString();
    }

    public String getTableOfSection(SimpleDocElement elem){
        if(elem.getKey().getLevel().lessThen(Level.PODTYTUL)) return "";

        Integer rank=elem.getKey().getLevel().getRank();
        String brackets = getBracketsWithRange(elem);
        String result = this.indentation.substring(rank) // adjusting indentation
                +elem.getKey().toString()
                // adjusting number of dots:
                +this.dots.substring(8-rank+elem.getKey().toString().length(),this.dots.length()-brackets.length())
                +brackets
                +this.lineSeparator;
        if(elem.getKey().getLevel()==Level.PODTYTUL) return result;

        StringBuilder str= new StringBuilder("");
        for(SimpleDocElement child : elem.children.values()) str.append(getTableOfSection(child));
        return result+this.indentation.substring(rank) // adjusting indentation
                    +elem.getContent()
                    +this.lineSeparator
                    +str.toString();
    }

    private String getBracketsWithRange(SimpleDocElement elem){
        String firstId = getFirstArticleID(elem);
        String lastId = getLastArticleID(elem);
        if(firstId.equals(lastId)) return "(art. "+firstId+")";
        return "(art. "+firstId+"-"+lastId+")";
    }

    private String getFirstArticleID(SimpleDocElement elem){
        if(elem.getKey().getLevel()==Level.ART) return elem.getKey().getId();
        return getFirstArticleID(new ArrayList<>(elem.getChildren().values()).get(0));
    }

    private String getLastArticleID(SimpleDocElement elem){
        if(elem.getKey().getLevel()==Level.ART) return elem.getKey().getId();
        return getLastArticleID(elem.getLastChild());
    }
}
