package agh.cs.project1b;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CMDParserToolKit {
    private Options options;

    CMDParserToolKit(){
        this.options=new Options();
    }
    private void createOptions(){
        Option constitution = Option.builder("k")
                .hasArg(false)
                .desc("information that parsed document is Consitution")
                .build();

        Option file = Option.builder("f")
                .required()
                .longOpt("file")
                .desc("file path")
                .hasArg()
                .argName("FILE")
                .build();

        Option table = Option.builder("t")
                .optionalArg(true)
                .longOpt("table-of-contents")
                .desc("display table of contents")
                .hasArg()
                .argName("SECTION")
                .build();

        Option range = Option.builder("r")
                .longOpt("range")
                .hasArg()
                .numberOfArgs(2)
                .desc("display range of articles")
                .argName("RANGE")
                .build();

        Option specificElem = Option.builder("s")
                .longOpt("specific-elem")
                .hasArgs()
                .desc("display dpecific document element")
                .argName("ELEM")
                .build();

        options.addOption(file);
        options.addOption(table);
        options.addOption(range);
        options.addOption(constitution);
        options.addOption(specificElem);
    }

    public Options getOptions(){
        createOptions();
        return this.options;
    }

    public List<Key> convertToKeys(String[] path){
        if(path.length % 2 == 1) throw new IllegalArgumentException("Incorrect path");

        List<Key> keys = new ArrayList<>();
        List<String> pathList = Arrays.asList(path);
        pathList = pathList.stream().map(str->str.replaceAll("[).,]*","")).collect(Collectors.toList());

        Iterator<String> iterator = pathList.listIterator();
        while(iterator.hasNext()){
            String levelStr = iterator.next();
            String id = iterator.next();
            if(!levelStr.matches("art|ust|pkt|lit")) throw new IllegalArgumentException("Incorrect path");
            switch(levelStr) {
                case "art": {
                    keys.add(new Key(Levels.ART,id));
                    break;
                }
                case "ust": {
                    keys.add(new Key(Levels.UST,id));
                    break;
                }
                case "pkt": {
                    keys.add(new Key(Levels.PKT,id));
                    break;
                }
                default : {
                    keys.add(new Key(Levels.LIT,id));
                    break;
                }
            }
        }
        return keys;
    }
}
