package pl.coderslab.plain;

public class Group {
    private int id;
    private String name;

    public Group(){
    }

    public Group(String name){
        this.name = name;
    }

    public void setName (String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
