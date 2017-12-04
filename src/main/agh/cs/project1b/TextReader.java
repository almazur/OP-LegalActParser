package agh.cs.project1b;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TextReader {
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException{
        try {
            ArgumentsParser argsParser = new ArgumentsParser(args);
            //argParser.printArgs();
            Scanner scanner = argsParser.initScanner();
            Document document = new DocParser(scanner).createTree();
            if(document.findChild(Levels.ART,"139")) System.out.println("Element istnieje");
            else System.out.println("Element nie istnieje");
            document.printTableOfContents();
            //document.printTree();
            scanner.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException | NoSuchFieldException | NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }
}
