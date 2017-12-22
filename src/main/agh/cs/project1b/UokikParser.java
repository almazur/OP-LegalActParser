package agh.cs.project1b;

import java.util.Scanner;

public class UokikParser extends AbstractDocumentParser{

    UokikParser(Scanner scanner) {
        super(scanner);
    }

    protected String processSimpleDocElem(String line, DocumentRoot root, Level level){
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        if(level== Level.ART) root.addArticle(child);
        this.lastDetectedDocElement = child;
        return removeId(line,level);
    }

}
