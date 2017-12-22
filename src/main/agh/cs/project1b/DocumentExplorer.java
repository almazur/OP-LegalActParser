package agh.cs.project1b;

import org.apache.commons.cli.CommandLine;

import java.util.List;

public class DocumentExplorer {
    private DocumentRoot document;

    DocumentExplorer(DocumentRoot document){
        this.document=document;
    }

    public String explore(CommandLine cmd) throws IllegalArgumentException{
        if(cmd.hasOption("k") && cmd.getOptions().length!=3 || !cmd.hasOption("k") && cmd.getOptions().length!=2)
            throw new IllegalArgumentException("Illegal argument. Wrong number of options");

        if (cmd.hasOption("r")) {
            String[] range = cmd.getOptionValues("r");
            return new SpecificContentGetter().getRange(range[0],range[1],this.document);
        }
        if (cmd.hasOption("t")) {
            String dzial = cmd.getOptionValue("t","");
            if (dzial.isEmpty()){
                return new TOCGetter().getTableOfContents(this.document);
            }
            else {
                if(!this.document.children.containsKey(new Key(Level.DZIAL,dzial)))
                    throw new IllegalArgumentException("Incorrect argument. DZIAŁ "+ dzial +" does not exist");
                SimpleDocElement section = this.document.children.get(new Key(Level.DZIAL,dzial));
                return new TOCGetter().getTableOfSection(section);
            }
        }
        // cmd has option "s"
        String[] path = cmd.getOptionValues("s");
        List<Key> keys = new OptionArgsConverter().toKeys(path);
        return new SpecificContentGetter().getSpecificElementContent(this.document,keys);
    }
}
