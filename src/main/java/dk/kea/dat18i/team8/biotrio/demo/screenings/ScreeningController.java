package dk.kea.dat18i.team8.biotrio.demo.screenings;


import dk.kea.dat18i.team8.biotrio.demo.movies.Movie;
import dk.kea.dat18i.team8.biotrio.demo.movies.MovieRepository;
import dk.kea.dat18i.team8.biotrio.demo.theater.Theater;
import dk.kea.dat18i.team8.biotrio.demo.theater.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Controller
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepo;
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private TheaterRepository theaterRepo;


    //displays a specific screening
    @GetMapping("/screeningview")
    @ResponseBody
    public Screening showScreening() {

        Screening screening = screeningRepo.findScreening(1);

        return screening;

    }

    //displays all screenings
    @GetMapping("/screenings")
    public String screening(Model model) {

        List<Screening> screeningList = screeningRepo.findAllScreenings();
        model.addAttribute("screeninglist", screeningList);

        return "show-screenings";
    }


    //displays a form for adding a new screening
    @GetMapping("/addscreening")
    public String addScreening(Model model) {

        ScreeningForm screeningForm = new ScreeningForm();
        List<Movie> movieList = movieRepo.showAllMovies();
        List<Theater> theaterList= theaterRepo.findAllTheaters();
        model.addAttribute(  "movielist", movieList );
        model.addAttribute( "theaterlist", theaterList );
        model.addAttribute( "screeningForm", screeningForm);

        return "add-screening";
    }


    @PostMapping("/savescreening")
    public String saveScreening(@ModelAttribute ScreeningForm screeningData){
        Screening newScreening = new Screening();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "yyyy MM dd HH:mm" );
        newScreening.setShowing( LocalDateTime.parse(screeningData.getShowing(),dtf ));

        newScreening.setMovie( movieRepo.showMovie( screeningData.getMovie_id() ) );
        newScreening.setTheater( theaterRepo.findTheater( screeningData.getTheater_id() ) );

        screeningRepo.insertScreening(newScreening);
        return "redirect:/screenings";
    }

    @RequestMapping(value = "screenings/delete/{screening_id}")
    public String removeScreening(@PathVariable int screening_id) {

        screeningRepo.deleteScreening(screening_id);

        return "redirect:/screenings";
    }


    //displays a form to edit a specific screening
    @GetMapping("/screenings/edit/{screening_id}")
    public String editScreening(Model model, @PathVariable(name = "screening_id") int screening_id){

        Screening screeningToEdit = screeningRepo.findScreening(screening_id);
        model.addAttribute("screening", screeningToEdit);
        ScreeningForm screeningForm2 = new ScreeningForm(  );

        List<Movie> movieList2 = movieRepo.showAllMovies();
        List<Theater> theaterList2 = theaterRepo.findAllTheaters();

        model.addAttribute(  "movielist2", movieList2 );
        model.addAttribute( "theaterlist2", theaterList2 );
        model.addAttribute( "screeningForm2", screeningForm2);

        return "edit-screening";
    }

    @PostMapping("/updatescreening")
    public String saveEditScreening( @ModelAttribute Screening upScreening, @ModelAttribute ScreeningForm screeningData){

        upScreening.setShowing( upScreening.getShowing() );
        upScreening.setMovie( movieRepo.showMovie( screeningData.getMovie_id() ));
        upScreening.setTheater( theaterRepo.findTheater( screeningData.getTheater_id() ) );

        screeningRepo.update(upScreening);
        return "redirect:/screenings";
    }

    //displays all screenings for a specific movie
    @GetMapping("/screeningbymovie/{movie_id}")
    public String screeningByMovie(Model model, @PathVariable(name = "movie_id") int movie_id){

        List<Screening> screeningsForMovies= screeningRepo.findScreeningsByMovie( movie_id );

        model.addAttribute( "screeningsForMovies", screeningsForMovies);

        return "movies-screenings";

    }

    /*
     *displays all screenings at a specific date
    if there are no screenings at that date, displays a message
     */
    @GetMapping("/screenings-date")
    public String getScreeningsDate(){
        return "/screenings-date";
    }
    @PostMapping("/screenings-search")
    public String showScreeningsByDate(@RequestParam (value = "search", required = false) String search, Model model) {

        if(!search.isEmpty()) {
            List<Screening> screeningSearch = screeningRepo.findScreeningsByDate(search);
            model.addAttribute("search", screeningSearch);
        }
        return "/screenings-date";
    }
}