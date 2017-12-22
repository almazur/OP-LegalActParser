package agh.cs.project1b;

public class Key {
    private Level level;
    private String id;

    Key(Level level, String id){
        this.level = level;
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Key)) return false;

        Key key = (Key) object;

        return getLevel() == key.getLevel() && (getId() != null ? getId().equals(key.getId()) : key.getId() == null);
    }

    @Override
    public int hashCode() {
        int result = getLevel() != null ? getLevel().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }

    public Level getLevel() {
        return this.level;
    }
    public String getId() {
        return this.id;
    }

    // returns document element designation
    public String toString() {
        return this.level.getPrefix()+this.id+this.level.getSufix();
    }

}
