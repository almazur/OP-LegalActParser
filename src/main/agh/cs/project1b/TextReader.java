package agh.cs.project1b;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TextReader {
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException, ParseException {
        try {
            new HelpFormatter().printHelp(" ",new OptionsCreator().getOptions());

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(new OptionsCreator().getOptions(), args);
            Scanner scanner = new Scanner(new File(cmd.getOptionValue("f")));
            DocumentRoot document;


            if(cmd.hasOption("k")) document = new KonstParser(scanner).createTree();
            else document = new UokikParser(scanner).createTree();

            System.out.println(new DocumentExplorer(document).explore(cmd));
            scanner.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException | NoSuchElementException e){
            System.err.println(e.getMessage());
        }catch(ParseException e) {
            System.err.println("Parsing failed. " + e.getMessage());
        }
    }
}
