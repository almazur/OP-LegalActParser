package agh.cs.project1b;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TextReader {
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException, ParseException {
        try {
            //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\uokik.txt"
            //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\konst.txt"

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(new CMDParserToolKit().getOptions(), args);
            Scanner scanner = new Scanner(new File(cmd.getOptionValue("f")));
            DocumentRoot document;

            if(cmd.hasOption("k")) document = new KonstParser(scanner).createTree();
            else document = new UokikParser(scanner).createTree();

            //document.explore(cmd);
            document.runExplorer(cmd);
            scanner.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException | NoSuchElementException e){
            System.out.println(e.getMessage());
        }catch(ParseException e) {
            System.err.println( "Parsing failed. " + e.getMessage() );
        }
    }
}
