package agh.cs.project1b;

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
            document.explore(argsParser.parseArgs());
            //document.printTableOfContents();
            //document.printTree();
            scanner.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException | NoSuchFieldException | NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }
}
