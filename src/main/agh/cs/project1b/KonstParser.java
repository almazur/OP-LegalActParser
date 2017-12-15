package agh.cs.project1b;

import java.util.Scanner;
import java.util.regex.Pattern;

public class KonstParser extends AbstractDocumentParser{
    private Boolean insideDocument;

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
            if(isSimpleText(line)){
                line=processSimpleText(line,root);
            }
            else {
                this.insideDocument=true;
                Levels level = findLevel(line);
                line = processSimpleDocElem(line,root,level);
            }
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
        if(level==Levels.ROZDZIAL){
            line = this.scanner.nextLine();
            return processSimpleText(line,root);
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
