package dk.kea.dat18i.team8.biotrio.demo.movies;

import java.time.LocalDate;

public class Acor {
    private String name;
    private Movie movie;
    private String birthday;

    public Acor(String name, Movie movie, String birthday){
        this.name=name;
        this.movie=movie;
        this.birthday=birthday;
    }

    public void it(){
        System.out.println("hello");
    }

    public static void main(String[] args) {
        //Acor.it();
        Acor actor=new Acor("Jenifer",new Movie(),"11.02.1961");
        actor.it();
    }

}
