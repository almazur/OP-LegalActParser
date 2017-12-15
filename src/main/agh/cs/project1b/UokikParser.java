package agh.cs.project1b;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UokikParser extends AbstractDocumentParser{

    UokikParser(Scanner scanner) {
        super(scanner);
    }

    public UokikRoot createTree() throws IllegalArgumentException{
        if(!this.scanner.hasNextLine()) throw new IllegalArgumentException("The document is empty");
        else {
            UokikRoot root = new UokikRoot();
            this.lastDetectedDocElement = root;

            while(this.scanner.hasNextLine()){
                String line = this.scanner.nextLine();
                processLine(line,root);
            }
            return root;
        }
    }

    protected String processSimpleDocElem(String line, AbstractRoot root, Levels level){
        SimpleDocElement child = new SimpleDocElement(new Key(level, extractIdNum(line, level)));
        root.addChild(child);
        //System.out.println("    LEVEL: "+level.toString());
        if(level==Levels.ART) {
            //System.out.println("DODAWANIE...");
            root.addArticle(child);
        }
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
