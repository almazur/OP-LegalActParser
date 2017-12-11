package agh.cs.project1b;

import java.util.*;

public class Document extends AbstractDocElement {
    private HashMap<Key,SectionDocElement> sections;
    private Levels sectionLevel;

    Document() {
        super();
        this.sections=new LinkedHashMap<>();
    }

    public void addSection(SectionDocElement section){
        if(this.sections.isEmpty() || this.sectionLevel.equals(section.key.getLevel())){
            this.sections.put(section.getKey(),section);
            this.sectionLevel=section.getKey().getLevel();
        } else{
            SectionDocElement lastElem=getLastSection();
            lastElem.addChild(section);
        }
    }

    protected SectionDocElement getLastSection(){
        if(this.sections.isEmpty()) return null;
        else{
            List<SectionDocElement> sectionsCopy = new LinkedList<>(this.sections.values());
            return sectionsCopy.get(sectionsCopy.size()-1);
        }
    }

    public void printTableOfContents() throws NoSuchFieldException {
        System.out.println("SPIS TRESCI");
        if(this.sectionLevel == Levels.ROZDZIAL){
            this.printSections("* ");
        }
        else {
            for(Key key : this.sections.keySet()){
                System.out.print("* ");
                printSectionContent(key.getId());
            }
        }
    }

    public void printSections(String prefix){
        if(!this.sections.isEmpty()){
            for(AbstractDocElement section : this.sections.values()){
                System.out.println(prefix+section.toString());
            }
        }
    }

    private void printSectionContent(String id){
        SectionDocElement section = this.sections.get(new Key(Levels.DZIAL,id));
        System.out.println(section.toString());
        if(!section.children.isEmpty()) section.printChildren("    - ");
    }

    public void printTree() {
        System.out.println(this.content);
        if (this.sections.isEmpty()) System.out.println("No sections");
        for (SectionDocElement section : this.sections.values()) {
            System.out.println(section.toString());
            if (!section.children.isEmpty()) {
                for (SimpleDocElement chapter : section.children.values()) {
                    System.out.println("  " + chapter.toString());
                    printArticles((SectionDocElement) chapter, "  ");
                }
            } else {
                printArticles(section, "  ");
            }
        }
    }

    private void printArticles(SectionDocElement section,String indentation){
        List<SimpleDocElement> childrenCopy=new LinkedList<>(this.children.values());
        SimpleDocElement article = this.children.get(new Key(Levels.ART,section.getFirstId()));
        Iterator<SimpleDocElement> iterator = childrenCopy.listIterator(childrenCopy.indexOf(article));

        while(iterator.hasNext()){
            article=iterator.next();
            System.out.println(indentation+article.toString());
            article.printSubTree("    ", "  ");
            if(article.getKey().getId().equals(section.getLastId())) break;
        }
    }

    public void explore(ArrayList<Object> args) throws NoSuchFieldException, IllegalArgumentException{
        Boolean printContentMode = (Boolean) args.get(0);

        if(!printContentMode) {
            if (args.size()==1) { //args: [false]
                printTableOfContents();
                return;
            }
            if(this.sectionLevel != Levels.DZIAL) { //args: [false,">>id of DZIAL<<"]
                throw new IllegalArgumentException("Document has no DZIAŁ's");
            }
            printSectionContent((String)args.get(1));
            return;
        }
        if(args.size()==3) {
            Levels level = (Levels) args.get(1);
            System.out.println(getChild(level, (String) args.get(2)));
            getChild(level, (String) args.get(2)).printSubTree("","   ");
        }
    }
}
