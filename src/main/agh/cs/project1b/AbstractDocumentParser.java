package agh.cs.project1b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDocumentParser {
    protected Scanner scanner;
    protected AbstractDocElement lastDetectedDocElement;

    AbstractDocumentParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public DocumentRoot createTree() throws IllegalArgumentException{
        if(!this.scanner.hasNextLine()) throw new IllegalArgumentException("Incorrect argument. The document is empty");
        else {
            DocumentRoot root = new DocumentRoot();
            this.lastDetectedDocElement = root;

            // PROCESS ROOT CONTENT
            while(this.scanner.hasNextLine()){
                String line = this.scanner.nextLine();
                if(isSimpleText(line) || findLevel(line) == Levels.TYTUL) processSimpleText(line);
                else{
                    processSimpleDocElem(line,root,findLevel(line));
                    break;
                }
            }
            // PROCESS SUBTREE
            while(this.scanner.hasNextLine()){
                String line = this.scanner.nextLine();
                processLine(line,root);
            }
            return root;
        }
    }

    protected void processLine(String line, DocumentRoot root){
        while(!line.isEmpty()){
            if(isSimpleText(line)) line=processSimpleText(line);
            else {
                Levels level = findLevel(line);
                line = processSimpleDocElem(line,root,level);
            }
        }
    }

    protected String processSimpleText(String line){
        if(!matchesForbiddenRegex(line)) this.lastDetectedDocElement.addContent(line);
        return "";
    }

    protected abstract String processSimpleDocElem(String line, DocumentRoot root, Levels level);

    protected Levels findLevel(String line){
        Iterator<Levels> iterator = new ArrayList<>(Arrays.asList(Levels.values())).listIterator();
        Levels level = iterator.next();
        while(iterator.hasNext() && !Pattern.compile(level.toString()).matcher(line).find()){
            level = iterator.next();
        }
        return level;
    }

    protected Boolean isSimpleText(String line) {
        for(Levels level : Levels.values()){
            if(Pattern.compile(level.toString()).matcher(line).find()) {
                return false;
            }
        }
        return true;
    }

    protected String extractIdNum(String line, Levels level){
        Pattern pattern = Pattern.compile(level.toString());
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher.group("id");
    }

    protected String removeId(String line,Levels level){
        return line.replaceFirst(level.toString(),"");
    }

    private Boolean matchesForbiddenRegex(String line){
        return (line.matches("^\\d{4}-\\d{2}-\\d{2}$")
                || line.matches(".*©Kancelaria Sejmu.*")
                || line.matches("^.?$"));
    }
}
