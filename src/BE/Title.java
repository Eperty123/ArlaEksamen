package BE;

public class Title {
    int Id;
    String title;

    public Title(int id, String title) {
        this.Id = id;
        this.title = title;
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
