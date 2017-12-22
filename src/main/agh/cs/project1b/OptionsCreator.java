package agh.cs.project1b;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.*;
import java.util.stream.Collectors;

public class OptionsCreator {
    private Options options;

    OptionsCreator(){
        this.options=new Options();
    }

    public Options getOptions(){
        createOptions();
        return this.options;
    }

    private void createOptions(){
        Option constitution = Option.builder("k")
                .hasArg(false)
                .desc("information that the parsed document is Consitution")
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
                .desc("display table of contents (e.g. -t or -t II)")
                .hasArg()
                .argName("SECTION")
                .build();

        Option range = Option.builder("r")
                .longOpt("range")
                .hasArg()
                .numberOfArgs(2)
                .desc("display range of articles (e.g. -r 1 4a)")
                .argName("RANGE")
                .build();

        Option specificElem = Option.builder("s")
                .longOpt("specific-elem")
                .hasArgs()
                .desc("display specific document element (e.g. -s art. 1., ust. 2., pkt. 3.)")
                .argName("ELEM")
                .build();

        options.addOption(file);
        options.addOption(table);
        options.addOption(range);
        options.addOption(constitution);
        options.addOption(specificElem);
    }
}
