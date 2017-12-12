package agh.cs.project1b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KonstParser extends AbstractDocumentParser{
    //private Scanner scanner;
    //private AbstractDocElement lastDetectedDocElement;
    private Boolean insideDocument;
    //private AbstractDocElement lastDetectedSection;

    public KonstParser(Scanner scanner) {
        super(scanner);
        this.insideDocument=false;
    }

    public KonstRoot createTree() throws IllegalArgumentException{
        if(!this.scanner.hasNextLine()) throw new IllegalArgumentException("The document is empty");
        else {
            KonstRoot root = new KonstRoot();
            this.lastDetectedDocElement = root;

            while(this.scanner.hasNextLine()){
                String line = this.scanner.nextLine();
                processLine(line,root);
            }
            return root;
        }
    }

    private void processLine(String line, KonstRoot root){
        while(!line.isEmpty()){
            //System.out.println("Processing line "+line);
            if(isSimpleText(line)){
                //System.out.println(line+" is simple text");
                line=processSimpleText(line,root);
            }
            else {
                this.insideDocument=true;
                Levels level = findLevel(line);
                //System.out.println(line+" is not simple text, level: "+level.toString());
                line = processSimpleDocElem(line,root,level);
            }
        }
    }

    private String processSimpleText(String line, KonstRoot root){
        if(!matchesForbiddenRegex(line)) {
            this.lastDetectedDocElement.addContent(line);
        }
        return "";
    }

    private String processSimpleDocElem(String line, KonstRoot root, Levels level){
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        if(level==Levels.ART) root.addArticle(child);
        //if(level==Levels.ROZDZIAL) this.lastDetectedSection=child;
        this.lastDetectedDocElement = child;
        //this.recentLevel = level;
        if(level==Levels.ROZDZIAL){
            line = this.scanner.nextLine();
            return processSimpleText(line,root);
        }
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

    protected Boolean isSimpleText(String line) {
        //System.out.println("Checking if is simple text: "+line);
        //if(line.isEmpty()) return false;
        for(Levels level : Levels.values()){
            //System.out.print("        WHAT");
            if(Pattern.compile(level.toString()).matcher(line).find()){
                //System.out.print("        WHAT...");
                //System.out.println("    LEVEL: "+level.toString());
                if(level!=Levels.TYTUL) return false;
                return !this.insideDocument;
            }
        }
        return true;
    }

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
