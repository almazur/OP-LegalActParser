package agh.cs.project1b;

import java.util.*;
import java.util.stream.Collectors;

public class OptionArgsConverter {

    // returns list of Keys
    // each Key is created from two following Strings from input table
    public List<Key> toKeys(String[] path){
        if(path.length % 2 == 1) throw new IllegalArgumentException("Incorrect argument. Wrong path");

        HashMap<String,Level> levels = new LinkedHashMap<>();
        for(Level level : Level.values()) levels.put(level.getRepresentation(),level);

        List<String> pathList = Arrays.asList(path);

        // removing series of ".", "," or ")" from the end of every String in table
        pathList = pathList.stream().map(str->str.replaceAll("[).,]*$","")).collect(Collectors.toList());

        List<Key> keys = new ArrayList<>();

        Iterator<String> iterator = pathList.listIterator();
        while(iterator.hasNext()){
            String levelStr = iterator.next();
            String id = iterator.next();
            if(!levels.containsKey(levelStr)) throw new IllegalArgumentException("Incorrect argument. Wrong path");
            keys.add(new Key(levels.get(levelStr),id));
        }
        return keys;
    }
}
