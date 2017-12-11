package agh.cs.project1b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocParser {
    private Scanner scanner;
    private AbstractDocElement lastDetectedSimpleDocElement;
    private SectionDocElement lastDetectedSection;
    private Boolean sectionHasBeenDetected;

    DocParser(Scanner scanner) {
        this.scanner = scanner;
        this.sectionHasBeenDetected=false;
    }

    public Document createTree() throws IllegalArgumentException{
        if(!this.scanner.hasNextLine()) throw new IllegalArgumentException("The document is empty");
        else {
            Document root = new Document();
            this.lastDetectedSimpleDocElement = root;

            while(this.scanner.hasNextLine()){
                String line = this.scanner.nextLine();
                processLine(line,root);
            }
            return root;
        }
    }

    private void processLine(String line, Document root){
        while(!line.isEmpty()){
            if(isSimpleText(line)) line=processSimpleText(line,root);
            else {
                Levels level = findLevel(line);
                if(level==Levels.DZIAL || level==Levels.ROZDZIAL) line = processSection(line,root,level);
                else line = processSimpleDocElem(line,root,level);
            }
        }
    }

    private String processSimpleText(String line, Document root){
        if(!matchesForbiddenRegex(line)) {
            if (this.sectionHasBeenDetected) this.lastDetectedSection.addContent(line);
            else this.lastDetectedSimpleDocElement.addContent(line);
        }
        return "";
    }

    private String processSection(String line, Document root, Levels level){
        SectionDocElement section = new SectionDocElement(new Key(level, extractIdNum(line, level)));
        root.addSection(section);
        this.sectionHasBeenDetected = true;
        this.lastDetectedSection = section;
        return removeId(line,level);
    }

    private String processSimpleDocElem(String line, Document root, Levels level){
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        if(level==Levels.ART) this.lastDetectedSection.setLastId(extractIdNum(line, level));
        this.sectionHasBeenDetected = false;
        this.lastDetectedSimpleDocElement = child;
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

    private Boolean isSimpleText(String line) {
        if(line.isEmpty()) return false;
        for(Levels level : Levels.values()){
            if(Pattern.compile(level.toString()).matcher(line).find()) return false;
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
        return line.replaceFirst(level.toString(),"");
    }

    private Boolean matchesForbiddenRegex(String line){
        return (line.matches("^\\d{4}-\\d{2}-\\d{2}$")
                || line.matches(".*©Kancelaria Sejmu.*")
                || line.matches("^.?$"));
    }
}
