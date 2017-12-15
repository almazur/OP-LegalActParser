package agh.cs.project1b;

import java.util.Scanner;
import java.util.regex.Pattern;

public class KonstParser extends AbstractDocumentParser{
    private Boolean insideDocument;

    public KonstParser(Scanner scanner) {
        super(scanner);
        this.insideDocument=false;
    }

    protected void processLine(String line, DocumentRoot root){
        while(!line.isEmpty()){
            if(isSimpleText(line)){
                line=processSimpleText(line);
            }
            else {
                this.insideDocument=true;
                Levels level = findLevel(line);
                line = processSimpleDocElem(line,root,level);
            }
        }
    }

    protected String processSimpleDocElem(String line, DocumentRoot root, Levels level){
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        if(level==Levels.ART) {
            root.addArticle(child);
        }
        this.lastDetectedDocElement = child;
        if(level==Levels.ROZDZIAL){
            line = this.scanner.nextLine();
            return processSimpleText(line);
        }
            return removeId(line,level);
    }

    protected Boolean isSimpleText(String line) {
        for(Levels level : Levels.values()){
            if(Pattern.compile(level.toString()).matcher(line).find()) {
                return level == Levels.TYTUL && !this.insideDocument;
            }
        }
        return true;
    }
}
