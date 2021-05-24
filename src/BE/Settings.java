package BE;

public class Settings {
    private int id;
    private SettingsType type;
    private String attribute;

    public Settings() {
    }

    public Settings(int type, String attribute) {
        this.type = SettingsType.valueOf(type);
        this.attribute = attribute;
    }

    public Settings(int id, int type, String attribute) {
        this.id = id;
        this.type = SettingsType.valueOf(type);
        this.attribute = attribute;
    }

    public Settings(int id, SettingsType type, String attribute) {
        this.id = id;
        this.type = type;
        this.attribute = attribute;
    }

    public Settings(SettingsType type, String attribute) {
        this.type = type;
        this.attribute = attribute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SettingsType getType() {
        return type;
    }

    public void setType(SettingsType type) {
        this.type = type;
    }

    public void setType(int type) {
        this.type = SettingsType.valueOf(type);
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
