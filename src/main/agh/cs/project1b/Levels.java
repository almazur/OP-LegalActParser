package agh.cs.project1b;

public enum Levels {
    DZIAL("^DZIAŁ\\s\\w+"),
    ROZDZIAL("^Rozdział\\s\\w+"),
    ART("^Art\\.\\s\\w+\\..*"),//("^Art\\.\\s\\w+\\.*\\-*.*")
    UST("^\\d+\\w*\\..+"),
    PKT("^\\d+\\)\\s.+"),
    LIT("^[a-z]{1}\\)\\s.+");

    private String regex;
    Levels(String regex){
        this.regex=regex;
    }

    public String toString() {
        return regex;
    }
}
