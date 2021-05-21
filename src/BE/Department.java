package BE;

import java.util.List;

public class Department {
    int id;
    String name;
    List<User> workers;

    public Department(int id, String name, List<User> workers) {
        this.id = id;
        this.name = name;
        this.workers = workers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getWorkers() {
        return workers;
    }

    public void setWorkers(List<User> workers) {
        this.workers = workers;
    }
}
