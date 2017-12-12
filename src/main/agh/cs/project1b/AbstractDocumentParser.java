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
    //protected Levels recentLevel;

    AbstractDocumentParser(Scanner scanner) {
        this.scanner = scanner;
        //this.recentLevel=null;
    }

    /*public KonstRoot createTree() throws IllegalArgumentException{
        if(!this.scanner.hasNextLine()) throw new IllegalArgumentException("The document is empty");
        else {
            AbstractRoot root = new AbstractRoot();
            this.lastDetectedDocElement = root;

            while(this.scanner.hasNextLine()){
                String line = this.scanner.nextLine();
                processLine(line,root);
            }
            return root;
        }
    }*/

    protected void processLine(String line, AbstractRoot root){
        while(!line.isEmpty()){
            //System.out.println("Processing line "+line);
            if(isSimpleText(line)){
                //System.out.println(line+" is simple text");
                line=processSimpleText(line,root);
            }
            else {
                Levels level = findLevel(line);
                //System.out.println(line+" is not simple text, level: "+level.toString());
                line = processSimpleDocElem(line,root,level);
            }
        }
    }

    private String processSimpleText(String line, AbstractRoot root){
        if(!matchesForbiddenRegex(line)) {
            this.lastDetectedDocElement.addContent(line);
        }
        return "";
    }

    private String processSimpleDocElem(String line, AbstractRoot root, Levels level){
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        if(level==Levels.ART) root.addArticle(child);
        this.lastDetectedDocElement = child;
        //this.recentLevel = level;
        return removeId(line,level);
    }

    private Levels findLevel(String line){
        Iterator<Levels> iterator = new ArrayList<>(Arrays.asList(Levels.values())).listIterator();
        Levels level = iterator.next();
        while(iterator.hasNext() && !Pattern.compile(level.toString()).matcher(line).find()){
            level = iterator.next();
        }
        return level;
    }

    protected abstract Boolean isSimpleText(String line);/*{
        if(line.isEmpty()) return false;
        for(Levels level : Levels.values()){
            if(Pattern.compile(level.toString()).matcher(line).find()){
                //System.out.println("    LEVEL: "+level.toString());
                //if(level!=Levels.TYTUL) return false;
                //return this.recentLevel!=Levels.ROZDZIAL;
                return false;
            }
        }
        return true;
    }*/

    private String extractIdNum(String line, Levels level){
        Pattern pattern = Pattern.compile(level.toString());
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher.group("id");
    }

    private String removeId(String line,Levels level){
        //if(level==Levels.TYTUL) return line;
        //System.out.println("    after removing id: "+line.replaceFirst(level.toString(),""));
        return line.replaceFirst(level.toString(),"");
    }

    private Boolean matchesForbiddenRegex(String line){
        return (line.matches("^\\d{4}-\\d{2}-\\d{2}$")
                || line.matches(".*©Kancelaria Sejmu.*")
                || line.matches("^.?$"));
    }
}
