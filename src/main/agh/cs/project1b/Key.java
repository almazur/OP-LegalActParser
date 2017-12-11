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
}
