package agh.cs.project1b;

import org.apache.commons.cli.CommandLine;

import java.util.*;

public class DocumentRoot extends AbstractDocElement {
    private HashMap<Key,SimpleDocElement> articles;

    DocumentRoot() {
        super();
        this.articles=new LinkedHashMap<>();
    }

    public void addArticle(SimpleDocElement article) {
        this.articles.put(article.getKey(), article);
    }

    public HashMap<Key, SimpleDocElement> getArticles() {
        return this.articles;
    }
    public HashMap<Key, SimpleDocElement> getChildren() {
        return this.children;

    }public Levels getChildLevel() {
        return this.childLevel;
    }
    public String getContent() {
        return this.content;
    }

}
