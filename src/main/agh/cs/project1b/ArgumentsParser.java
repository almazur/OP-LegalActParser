package agh.cs.project1b;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ArgumentsParser {
    //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\uokik.txt"
    //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\konst.txt"

    private List<String> args;

    ArgumentsParser(String[] args){
        this.args= Arrays.asList(args);
    }

    public ArrayList<Object> parseArgs() throws IllegalArgumentException {
        if(this.args.size() <= 2) throw new IllegalArgumentException("Too few arguments");
        ArrayList<Object> argsList = new ArrayList<>();
        if(this.args.get(1).equals("-t")){
            argsList.add(false);
            if(this.args.size()==2){
                return argsList;
            }
            if(this.args.size()>3) throw new IllegalArgumentException("-t option accepts only one argument");
            argsList.add(this.args.get(2));
            return argsList;
        }
        argsList.add(true);
        if(args.size()==3){
            //System.out.println("Size of command line to "+args.size());
            if(this.args.get(1).equals("art."))  argsList.add(Levels.ART);
            if(this.args.get(1).equals("rozdział"))  argsList.add(Levels.ROZDZIAL);
            argsList.add(this.args.get(2));
        }
        return argsList;
    }

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
