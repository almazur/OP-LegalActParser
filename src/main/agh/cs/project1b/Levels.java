package agh.cs.project1b;

public enum Levels {

    DZIAL("^DZIAŁ\\s(?<id>\\w+)"),
    ROZDZIAL("^Rozdział\\s(?<id>\\w+)"),
    ART("^Art\\.\\s(?<id>\\w+(–\\w+)?)\\.\\s*"),
    UST("^(?<id>\\d+\\w*)\\.\\s"),
    PKT("^(?<id>\\d+\\w*)[)]\\s"),
    LIT("^(?<id>[a-z]{1})[)]\\s");

    private String regex;
    Levels(String regex){
        this.regex=regex;
    }

    public String toString() {
        return regex;
    }
}
