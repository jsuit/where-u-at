package gitmad.app.WhereUAt.model;

public class Memo {

    private long id;

    private String name;

    private String location;

    private String filePath;

    public Memo() {
    }

    public Memo(long id, String name, String location, String filePath) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
