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

    public abstract AbstractRoot createTree() throws IllegalArgumentException;

    protected void processLine(String line, AbstractRoot root){
        while(!line.isEmpty()){
            if(isSimpleText(line)){
                line=processSimpleText(line,root);
            }
            else {
                Levels level = findLevel(line);
                line = processSimpleDocElem(line,root,level);
            }
        }
    }

    protected String processSimpleText(String line, AbstractRoot root){
        if(!matchesForbiddenRegex(line)) {
            this.lastDetectedDocElement.addContent(line);
        }
        return "";
    }

    protected abstract String processSimpleDocElem(String line, AbstractRoot root, Levels level);/*{
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        if(level==Levels.ART) root.addArticle(child);
        this.lastDetectedDocElement = child;
        return removeId(line,level);
    }*/

    protected Levels findLevel(String line){
        Iterator<Levels> iterator = new ArrayList<>(Arrays.asList(Levels.values())).listIterator();
        Levels level = iterator.next();
        while(iterator.hasNext() && !Pattern.compile(level.toString()).matcher(line).find()){
            level = iterator.next();
        }
        return level;
    }

    protected abstract Boolean isSimpleText(String line);

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
