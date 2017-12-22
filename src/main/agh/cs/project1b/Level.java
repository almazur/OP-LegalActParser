package agh.cs.project1b;

public enum Level {

    DZIAL("^DZIAŁ\\s(?<id>\\w+)",7,"dział","DZIAŁ ",""),
    ROZDZIAL("^Rozdział\\s(?<id>\\w+)",6,"rozdział","Rozdział ",""),
    PODTYTUL("^(?<id>(\\s*[\\p{Lu}][,]*)+)$",5,"oddział","",""),
    ART("^Art\\.\\s(?<id>\\w+(–\\w+)?)\\.\\s*",4,"art","Art. ","."),
    UST("^(?<id>\\d+\\w*)\\.\\s",3,"ust","","."),
    PKT("^(?<id>\\d+\\w*)[)]\\s",2,"pkt","",")"),
    LIT("^(?<id>[a-z]{1})[)]\\s",1,"lit","",")");

    private String regex;
    private Integer rank; // used to compare levels dział > rozdział > podtytul etc.
    private String representation; // used to detect levels in command line
    private String prefix; // used to recreate designation of document element
    private String sufix; // used to recreate designation of document element

    Level(String regex, Integer rank, String representation, String prefix, String sufix){
        this.regex = regex;
        this.rank = rank;
        this.representation = representation;
        this.prefix = prefix;
        this.sufix = sufix;
    }

    public Boolean lessThen(Level other){
        return this.rank < other.rank;
    }

    public String getRegex() {
        return regex;
    }
    public String getRepresentation(){
        return this.representation;
    }
    public Integer getRank(){
        return this.rank;
    }
    public String getPrefix() {
        return this.prefix;
    }
    public String getSufix() {
        return this.sufix;
    }
}
