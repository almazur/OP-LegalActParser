package agh.cs.project1b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class DocParser {
    private Scanner scanner;

    DocParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public Document createTree() throws IllegalArgumentException{
        if(!this.scanner.hasNextLine()) throw new IllegalArgumentException("The document is empty");
        else {
            Document root = new Document();
            AbstractDocElement last = root;

            while(this.scanner.hasNextLine()){
                String line = this.scanner.nextLine();
                last = processLineAndReturnLastElem(line,root,last);
            }
            return root;
        }
    }

    private AbstractDocElement processLineAndReturnLastElem(String line, Document root, AbstractDocElement last){
        while(!line.isEmpty()){
            if(isSimpleText(line)){
                if(!matchesForbiddenRegex(line)) last.addContent(line);
                line=""; ///Moze funkcja moveContent ktora od razu robi line=""?
            }
            else {
                Levels level = findLevel(line);
                SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
                root.addChild(child);
                line = removeId(line, level);
                last = child;
            }
        }
        return last;
    }

    private Levels findLevel(String line){
        Iterator<Levels> iterator = new ArrayList<>(Arrays.asList(Levels.values())).listIterator();
        Levels level = iterator.next();
        while (iterator.hasNext() && !line.matches(level.toString())) {
            level = iterator.next();
        }
        return level;
    }

    private Boolean isSimpleText(String line) {
        if(line.isEmpty()) return false;
        for(Levels level : Levels.values()){
            if (line.matches(level.toString())) return false;
        }
        return true;
    }

    private String extractIdNum(String line, Levels level){
        if(level== Levels.PKT || level== Levels.LIT) return line.split("\\)", 2)[0];
        if(level== Levels.UST) return line.split("\\.", 2)[0];
        String[] parts = line.split("\\s", 3);
        if(level== Levels.ART) return parts[1].split("\\.", 2)[0];
        else return parts[1];
    }

    private String removeId(String line,Levels level){
        if(level== Levels.PKT || level== Levels.LIT) return line.split("\\)\\s", 2)[1];
        if(level== Levels.UST) return line.split("\\.\\s", 2)[1];
        if(line.matches("^Art\\.\\s\\w+\\.\\s.+")) return line.split("\\s", 3)[2];
        return "";
    }

    private Boolean matchesForbiddenRegex(String line){
        return (line.matches("^\\d{4}-\\d{2}-\\d{2}$") || line.matches(".*©Kancelaria Sejmu.*"));
    }
}
