package edu.episen.si.ing1.fise.pds.backend.connectionPool;

public class Users {
    private int id;
    private String name;
    private int age;

    public int getId() {return this.id;}
    public String getName() {return this.name;}
    public int getAge() {return this.age;}

    public void setId(int id) {this.id=id;}
    public void setName(String n) {this.name=n;}
    public void setAge(int a) {this.age=a;}

    public Users()
    {}
    public Users(int id, String name,int age)
    {
        this.id=id;
        this.name=name;
        this.age=age;
    }
}
