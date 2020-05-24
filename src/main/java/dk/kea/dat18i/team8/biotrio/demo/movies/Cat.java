package dk.kea.dat18i.team8.biotrio.demo.movies;

public class Cat extends Animal {

    public Cat(int age){
        super(age);
    }

    private String name;

    public String getName(){
        return name;
    }
    public static void main(String[] args){
        Cat cat1 = new Cat(2);
        System.out.println(cat1.getName());
    }
}
