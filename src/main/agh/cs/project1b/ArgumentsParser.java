package agh.cs.project1b;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentsParser {

    private List<String> args;

    //-->OPTION PARSING JAVA

    ArgumentsParser(String[] args){
        this.args= Arrays.asList(args);
    }

    public ArrayList<Object> parseArgs() throws IllegalArgumentException {
        ArrayList<Object> parsedArgs = new ArrayList<>();
        String argsAsString = "";
        for (String s : this.args/*.subList(1, this.args.size())*/) {
            argsAsString = argsAsString + " " + s;
        }
        System.out.println(argsAsString);


        Iterator<CMDPatterns> iterator = new ArrayList<>(Arrays.asList(CMDPatterns.values())).listIterator();
        CMDPatterns cmd = iterator.next();
        System.out.println("Pattern: "+cmd.toString());

        while(iterator.hasNext() && !argsAsString.matches(cmd.toString())){//!matcher.find()){
            cmd = iterator.next();
            //matcher.usePattern(Pattern.compile(cmd.toString()));
            System.out.println("Pattern: "+cmd.toString());
        }

        Matcher matcher = Pattern.compile(cmd.toString()).matcher(argsAsString);
        matcher.find();
        System.out.println("FINISH Pattern: "+cmd.toString());
        switch (cmd) {
            case TableAll: {
                System.out.println("TABLE ALL");
                parsedArgs.add(false);
                return parsedArgs;
            }
            case TableDzial: {
                System.out.println("TABLE DZIAL");
                parsedArgs.add(false);
                parsedArgs.add(matcher.group("id"));
                return parsedArgs;
            }
            default:{
                System.out.println("DIDN'T WORK");
                parsedArgs.add(false);
                return parsedArgs;
            }
        }
        //parsedArgs.add(false);
        //return parsedArgs;
    }

    /*public ArrayList<Object> parseArgs() throws IllegalArgumentException {
        ArrayList<Object> parsedArgs = new ArrayList<>();
        String argsAsString = "";
        for (String s : this.args.subList(1, this.args.size())) {
            argsAsString = argsAsString + " " + s;
        }
        System.out.println(argsAsString);
        //wypisanie całego spisu treści | in: "-t", out "false"
        if (argsAsString.matches("^\\s\\-t$")) {
            parsedArgs.add(false);
            return parsedArgs;
        }
        //wypisanie treści działu  | in: "-t dział id" or "-t id", out "false id"
        if (argsAsString.matches("^\\s\\-t\\s\\w+\\s\\w+$") && this.args.get(1).toLowerCase().equals("dział")
                || argsAsString.matches("^\\s-t\\s\\w+$")) {
            parsedArgs.add(false);
            parsedArgs.add(this.args.get(this.args.size() - 1));
            return parsedArgs;
        }
        //wypisanie tresci jednego artykułu | in: "-t art. id." out "true id"
        if (argsAsString.matches("^\\s-t\\s\\w+\\.\\s\\w+\\.$") && this.args.get(1).toLowerCase().equals("art.")) {
            parsedArgs.add(true);
            parsedArgs.add(this.args.get(this.args.size() - 1));
            return parsedArgs;
        }
        //wypisanie tresci zakresu artykułów | in: "-t art. id-id." out "true id"
        if (argsAsString.matches("^ -t\\s\\w+\\.\\s\\w+-\\w+\\.$") && this.args.get(1).toLowerCase().equals("art.")) {
            parsedArgs.add(true);
            parsedArgs.add(this.args.get(1));
            parsedArgs.add(this.args.get(2));
            return parsedArgs;
        }
        throw new IllegalArgumentException("Arguments are not correct");
    }*/


        //if(args.matches("^-t"))



        /*if(this.args.size() <= 2) throw new IllegalArgumentException("Too few arguments");
        ArrayList<Object> parsedArgs = new ArrayList<>();
        if(this.args.get(1).equals("-t")){
            parsedArgs.add(false);
            if(this.args.size()==2){
                return parsedArgs;
            }
            if(this.args.size()>3) throw new IllegalArgumentException("-t option accepts only one argument");
            parsedArgs.add(this.args.get(2));
            return parsedArgs;
        }
        parsedArgs.add(true);
        if(args.size()==3){
            //System.out.println("Size of command line to "+args.size());
            if(this.args.get(1).equals("art.")) {
                parsedArgs.add(Levels.ART);
                if(this.args.get(2).matches("^d+-d+$")){
                    String[] parts = this.args.get(2).split("-",2);
                    parsedArgs.add(parts[0]);
                    parsedArgs.add(parts[1]);
                    return parsedArgs;
                }
                parsedArgs.add(this.args.get(2));
                return parsedArgs;
            }
            //if(this.args.get(1).equals("rozdział"))  parsedArgs.add(Levels.ROZDZIAL);
            //parsedArgs.add(this.args.get(2));
        }
        return parsedArgs;*/

    public Scanner initScanner() throws FileNotFoundException {
        String filename = this.args.get(0);
        return new Scanner(new File(filename));
    }

    public void printArgs(){
        for(String arg : this.args) {
            System.out.println(arg);
        }
    }


}
