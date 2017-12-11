package agh.cs.project1b;

public enum CMDPatterns {

    TableAll("(?<file>.+)\\s-t$"),
    TableDzial("(?<file>.+)\\s-t(\\sdział)?\\s(?<id>\\w+)"),
    ContentOfSection("^(?<file>.+)(\\sdział\\s(?<dzial>w+))?(\\srozdział(?<rozdzial>w+))?\\.?"),
    ContentOfArt("^(?<file>.+)\\sart\\.\\s(?<id>\\w+)\\.?$"),
    ContentOfArts("^(?<file>.+)\\sart\\.\\s(?<first>\\w+)-(?<last>w+)\\.?$"),
    ContentOfSpecific("^(?<file>.+)\\sasrt\\.\\s(?<art>\\w+)\\.(?<path>.+)$");

    private String regex;
    CMDPatterns(String regex){
        this.regex=regex;
    }

    public String toString() {
        return regex;
    }
}
