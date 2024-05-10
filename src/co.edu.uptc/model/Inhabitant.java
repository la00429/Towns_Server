package co.edu.uptc.model;

public class Inhabitant {
    private String id;
    private String name;

    public Inhabitant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public synchronized String getId() {
        return id;
    }

    public synchronized String getName() {
        return name;
    }
}
