package agh.cs.project1b;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UokikParser extends AbstractDocumentParser{

    UokikParser(Scanner scanner) {
        super(scanner);
    }

    protected String processSimpleDocElem(String line, DocumentRoot root, Levels level){
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        if(level==Levels.ART) root.addArticle(child);
        this.lastDetectedDocElement = child;
        return removeId(line,level);
    }

    protected Boolean isSimpleText(String line) {
        for(Levels level : Levels.values()){
            if(Pattern.compile(level.toString()).matcher(line).find()){
                return level == Levels.TYTUL;
            }
        }
        return true;
    }
}
