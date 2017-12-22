package agh.cs.project1b;

public enum ForbiddenRegex {

    DATE("^\\d{4}-\\d{2}-\\d{2}$"),
    KANCELARIA(".*©Kancelaria Sejmu.*"),
    SINGLECHAR("^.?$");

    private String regex;

    ForbiddenRegex(String regex){
        this.regex = regex;
    }
    public String toString() {
        return regex;
    }
}
