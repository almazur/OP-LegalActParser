package agh.cs.project1b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDocumentParser {
    protected Scanner scanner;
    protected AbstractDocElement lastDetectedDocElement; // last detected simple document element

    AbstractDocumentParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public DocumentRoot createTree() throws IllegalArgumentException{
        if(!this.scanner.hasNextLine()) throw new IllegalArgumentException("Incorrect argument. The document is empty");

        DocumentRoot root = new DocumentRoot();
        this.lastDetectedDocElement = root;

        // process text before first section where matching uppercase line to PODTYTUL should be ignored
        while(this.scanner.hasNextLine()){
            String line = this.scanner.nextLine();
            if(isSimpleText(line) || findLevel(line) == Level.PODTYTUL){
                processSimpleText(line);
            }
            else{
                processSimpleDocElem(line,root,findLevel(line));
                break;
            }
        }
        // section has been found. Process subtree, now PODTYTUL can be detected normally
        while(this.scanner.hasNextLine()){
            String line = this.scanner.nextLine();
            processLine(line,root);
        }
        return root;
    }

    protected void processLine(String line, DocumentRoot root){
        while(!line.isEmpty()){
            if(isSimpleText(line)) line=processSimpleText(line);
            else {
                Level level = findLevel(line);
                line = processSimpleDocElem(line,root,level);
            }
        }
    }

    protected String processSimpleText(String line){
        if(!matchesForbiddenRegex(line))
            this.lastDetectedDocElement.setContent(dehyphenContent(this.lastDetectedDocElement.getContent())+line);
        return "";
    }

    protected abstract String processSimpleDocElem(String line, DocumentRoot root, Level level);


    protected Boolean isSimpleText(String line) {
        for(Level level : Level.values())
            if(Pattern.compile(level.getRegex()).matcher(line).find()) return false;
        return true;
    }

    protected Level findLevel(String line){
        Iterator<Level> iterator = new ArrayList<>(Arrays.asList(Level.values())).listIterator();
        Level level = iterator.next();
        while(iterator.hasNext() && !Pattern.compile(level.getRegex()).matcher(line).find()){
            level = iterator.next();
        }
        return level;
    }

    protected String extractIdNum(String line, Level level){
        Pattern pattern = Pattern.compile(level.getRegex());
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher.group("id");
    }

    protected String removeId(String line,Level level){
        return line.replaceFirst(level.getRegex(),"");
    }

    private Boolean matchesForbiddenRegex(String line){
        for(ForbiddenRegex forbiddenRegex : ForbiddenRegex.values())
            if(line.matches(forbiddenRegex.toString())) return true;
        return false;
    }

    // prepares existing content (removes hyphen or adds space) when new part is to be added
    private String dehyphenContent(String content){
        if(content.isEmpty()) return content;
        if(content.endsWith("-")) return content.replaceFirst("-$","");
        return content+" ";
    }
}
