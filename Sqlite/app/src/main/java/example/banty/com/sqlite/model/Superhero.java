package example.banty.com.sqlite.model;

public class Superhero {
    private int id;
    private String name;
    private String description;
    private String thumbURL;


    public Superhero(int id, String name, String description, String thumbURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbURL = thumbURL;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public int getId() {
        return id;
    }
}
