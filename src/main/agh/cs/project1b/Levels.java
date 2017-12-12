package agh.cs.project1b;

public enum Levels {

    DZIAL("^DZIAŁ\\s(?<id>\\w+)",7),
    ROZDZIAL("^Rozdział\\s(?<id>\\w+)",6),
    TYTUL("^(?<id>(\\s*[\\p{Lu}][,]*)+)$",5),
    ART("^Art\\.\\s(?<id>\\w+(–\\w+)?)\\.\\s*",4),
    UST("^(?<id>\\d+\\w*)\\.\\s",3),
    PKT("^(?<id>\\d+\\w*)[)]\\s",2),
    LIT("^(?<id>[a-z]{1})[)]\\s",1);

    private String regex;
    private Integer height;
    Levels(String regex,Integer height){
        this.regex=regex;
        this.height=height;
    }

    public Boolean lessThen(Levels other){
        return this.height < other.height;
    }

    public String toString() {
        return regex;
    }
}
