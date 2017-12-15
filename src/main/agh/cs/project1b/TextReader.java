package agh.cs.project1b;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TextReader {
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException, ParseException {
        try {
            //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\uokik.txt"
            //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\konst.txt"
            //ArgumentsParser argsParser = new ArgumentsParser(args);
            //argParser.printArgs();
            //Scanner scanner = argsParser.initScanner();

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(new OptionMaker().getOptions(), args);
            Scanner scanner = new Scanner(new File(cmd.getOptionValue("f")));
            AbstractRoot document;

            if(cmd.hasOption("k")) document = new KonstParser(scanner).createTree();
            else document = new UokikParser(scanner).createTree();
            //document = new KonstParser(scanner).createTree();

            //document.explore(argsParser.parseArgs());
            document.explore(cmd);
            //document.printRange("31d","49a");
            //document.printTableOfContents();
            //document.printTree();
            scanner.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException | NoSuchElementException e){
            System.out.println(e.getMessage());
        }catch( ParseException exp ) {
            System.err.println( "Parsing failed. " + exp.getMessage() );
        }
    }
}
