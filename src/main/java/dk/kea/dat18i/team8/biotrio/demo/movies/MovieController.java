package dk.kea.dat18i.team8.biotrio.demo.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {


    @Autowired
    private MovieRepository movieRepo;

    //displays a specific movie
    @GetMapping("/moviesview")
    @ResponseBody
    public Movie showMovie(){
        Movie movie = movieRepo.showMovie(1);
        return movie;
    }

    //displays all movies, view for employees
    @GetMapping("/movies")
    public String movie(Model model){
        List<Movie> movieList= movieRepo.showAllMovies();
        model.addAttribute("movies",movieList);
        return "show-movies";
    }

    //displays all movies, view for customer
    @GetMapping("/moviesuser")
    public String moviesUser(Model model){
        List<Movie> movieList= movieRepo.showAllMovies();
        model.addAttribute("movies",movieList);
        return "show-moviesuser";

    }

    //displays a form for adding a movie
    @GetMapping("/addmovie")
    public String addMovie(Model model){
        model.addAttribute("movieform", new Movie());
        return "add-movie";
    }

    @PostMapping("/savemovie")
    public String saveMovie(@ModelAttribute Movie movie){
        movieRepo.insert(movie);
        return "redirect:/movies";
    }

    @GetMapping("/deletemovie/{movie_id}")
    public String deleteMovie (@PathVariable(name="movie_id") int id){
        movieRepo.delete(id);
        return "redirect:/movies";
    }

    //displays a form for editing a movie
    @GetMapping("/editmovie/{movie_id}")
    public String editMovie (Model m, @PathVariable(name="movie_id") int id){
        Movie movieToEdit= movieRepo.showMovie(id);
        m.addAttribute("movie",movieToEdit);
        return "edit-movie";
    }

    @PostMapping("/updateMovie")
    public String saveEditMovie(@ModelAttribute Movie movie){
        movieRepo.update(movie);
        return "redirect:/movies";
    }

}

