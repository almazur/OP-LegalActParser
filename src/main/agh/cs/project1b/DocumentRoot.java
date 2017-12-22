package agh.cs.project1b;

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

}
