package agh.cs.project1b;

import java.util.Scanner;

public class KonstParser extends AbstractDocumentParser{

    public KonstParser(Scanner scanner) {
        super(scanner);
    }

    protected String processSimpleDocElem(String line, DocumentRoot root, Level level){
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        if(level== Level.ART) root.addArticle(child);
        this.lastDetectedDocElement = child;
        if(level== Level.ROZDZIAL){
            // every rozdzial has uppercase title in one line (stored as ROZDZIAL content).
            // any additional uppercase lines right after are PODTYTUL's
            line = this.scanner.nextLine();
            return processSimpleText(line);
        }
            return removeId(line,level);
    }

}
