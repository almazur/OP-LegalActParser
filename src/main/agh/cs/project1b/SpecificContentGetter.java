package agh.cs.project1b;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpecificContentGetter {
    private String lineSeparator;
    private String indentation; // indentation is used to make printed text clearer

    public SpecificContentGetter() {
        this.lineSeparator= System.getProperty("line.separator");
        this.indentation = "        ";
    }

    public String getRange(String firstId, String lastId, DocumentRoot document) throws IllegalArgumentException{
        String str="";
        List<SimpleDocElement> articlesCopy = new ArrayList<>(document.getArticles().values());
        Key firstKey = new Key(Level.ART,firstId);
        Key lastKey = new Key(Level.ART,lastId);

        if(!document.getArticles().containsKey(firstKey))
            throw new IllegalArgumentException("Incorrect argument. Art. "+firstId+" does not exist");
        if(!document.getArticles().containsKey(lastKey))
            throw new IllegalArgumentException("Incorrect argument. Art. "+lastId+" does not exist");
        if(articlesCopy.indexOf(document.getArticles().get(firstKey)) > articlesCopy.indexOf(document.getArticles().get(lastKey)))
            throw new IllegalArgumentException("Incorrect argument. Wrong range");

        Iterator<SimpleDocElement> iterator = articlesCopy.listIterator(articlesCopy.indexOf(document.getArticles().get(firstKey)));
        SimpleDocElement article;
        while(iterator.hasNext()){
            article=iterator.next();
            if(articlesCopy.indexOf(article)<=articlesCopy.indexOf(document.getArticles().get(lastKey))){
                str=str+this.indentation.substring(article.getKey().getLevel().getRank())
                        +article.toString()+lineSeparator+getSubTree(article);
            }
            else break;
        }
        return str;
    }

    public String getSpecificElementContent(DocumentRoot document,List<Key> keys){
        if(!document.getArticles().containsKey(keys.get(0)) &&
                !document.getChildren().containsKey(keys.get(0)))
            throw new IllegalArgumentException("Incorrect argument. No such element exists");
        SimpleDocElement elem;
        if(keys.get(0).getLevel()== Level.ART) elem = document.getArticles().get(keys.get(0));
        else elem = document.getChildren().get(keys.get(0));

        if(keys.size()==1) return elem.toString()+lineSeparator+getSubTree(elem);
        return getContentFromKeyPath(keys.subList(1,keys.size()),elem);
    }

    private String getContentFromKeyPath(List<Key> keys, SimpleDocElement elem) throws IllegalArgumentException{
        String str="";
        Key key=keys.get(0);

        if(elem.getChildren().containsKey(key)){
            SimpleDocElement child=elem.getChildren().get(key);
            if(keys.size()==1){
                str=str+child.toString()+this.lineSeparator;
                str=str+getSubTree(child);
            } else str=str+ getContentFromKeyPath(keys.subList(1,keys.size()),child);
        } else throw new IllegalArgumentException("Incorrect argument. No such element exists");
        return str;
    }

    private String getSubTree(AbstractDocElement elem){
        StringBuilder str= new StringBuilder();
        if(!elem.getChildren().isEmpty()){
            for(SimpleDocElement child : elem.getChildren().values()){
                str.append(this.indentation.substring(child.getKey().getLevel().getRank())) // adjusting indentation
                        .append(child.toString())
                        .append(lineSeparator)
                        .append(getSubTree(child));
            }
        }
        return str.toString();
    }
}
