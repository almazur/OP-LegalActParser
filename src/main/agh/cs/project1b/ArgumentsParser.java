package agh.cs.project1b;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ArgumentsParser {
    //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\uokik.txt"
    //"C:\\Users\\Ola\\IdeaProjects\\project1\\src\\main\\agh\\cs\\project1\\konst.txt"

    private String[] args;

    ArgumentsParser(String[] args){
        this.args= args;
    }

    public Scanner initScanner() throws FileNotFoundException {
        String filename = this.args[0];
        return new Scanner(new File(filename));
    }

    public void printArgs(){
        for(String arg : this.args) {
            System.out.println(arg);
        }
    }


}
