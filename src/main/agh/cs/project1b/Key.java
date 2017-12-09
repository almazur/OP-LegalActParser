package agh.cs.project1b;

public class Key {
    private Levels level;
    private String id;

    Key(Levels level, String id){
        this.level = level;
        this.id = id;
    }

    public Levels getLevel() {
        return this.level;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;

        Key key = (Key) o;

        if (getLevel() != key.getLevel()) return false;
        return getId() != null ? getId().equals(key.getId()) : key.getId() == null;
    }

    @Override
    public int hashCode() {
        int result = getLevel() != null ? getLevel().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }

    public String toString() {
        if(this.level== Levels.PKT || this.level== Levels.LIT) return this.id+")";
        if(this.level== Levels.ART) return "Art. "+this.id+".";
        if(this.level== Levels.UST) return this.id+".";
        if(this.level== Levels.ROZDZIAL) return "Rozdział "+this.id;
        if(this.level== Levels.DZIAL) return "DZIAŁ "+this.id;
        return "";
    }

    public Boolean inRange(String firstId, String lastId){
        //System.out.println("firstId: "+firstId+", lastId "+lastId+", this.id "+this.id);

        if(firstId.equals(this.id) || lastId.equals(this.id)) {
            //System.out.println("HURRAY");
            return true;
        }

        Integer firstNum = Integer.valueOf(firstId.replaceFirst("[a-z]+",""));
        Integer lastNum = Integer.valueOf(lastId.replaceFirst("[a-z]+",""));
        Integer thisNum = Integer.valueOf(this.id.replaceFirst("[a-z]+",""));
        String firstStr=firstId.replaceFirst("\\d+","");
        String lastStr=lastId.replaceFirst("\\d+","");
        String thisStr=this.id.replaceFirst("\\d+","");

        Boolean leftEdge,rightEdge;
        leftEdge = thisStr.isEmpty() && firstStr.isEmpty() ? thisNum >= firstNum :
                thisStr.isEmpty() ? thisNum > firstNum :
                        thisNum > firstNum || thisNum.equals(firstNum) && thisStr.compareTo(firstStr) >=0;
        rightEdge = thisStr.isEmpty() && lastStr.isEmpty() ? thisNum <= lastNum :
                lastStr.isEmpty() ? thisNum < lastNum :
                        thisNum < lastNum || thisNum.equals(lastNum) && thisStr.compareTo(lastStr) <=0;
        return leftEdge && rightEdge;
    }
}
