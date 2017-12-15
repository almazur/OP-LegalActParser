package agh.cs.project1b;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionMaker {
    Options options;

    OptionMaker(){
        this.options=new Options();
        //options.addOption("t", "table-of-contents", false, "display table of contents");
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
        return this.options;
    }
}
