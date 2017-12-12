package agh.cs.project1b;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TextReader {
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException{
        try {
            //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\uokik.txt"
            //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\konst.txt"
            ArgumentsParser argsParser = new ArgumentsParser(args);
            //argParser.printArgs();
            Scanner scanner = argsParser.initScanner();
            if(Pattern.compile(Levels.TYTUL.toString()).matcher("W trosce o byt i przyszłość naszej Ojczyzny").find()) System.out.println("DZIALA");
            AbstractRoot document = new UokikParser(scanner).createTree();
            document.explore(argsParser.parseArgs());
            //document.printTableOfContents();
            document.printTree();
            scanner.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException | NoSuchFieldException | NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }
}
