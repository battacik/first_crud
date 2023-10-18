package pl.battacik.movielibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies") //wszystkie poniżej będą w /movies
public class MovieController {
    @Autowired //wstrzykujemy zależność do tego repozytorium
    MovieRepository movieRepository;


    @GetMapping("") //nowy mapping, endpoint movies
    public List<Movie> getAll(){
        return movieRepository.getAll();
    }
    @GetMapping("/{id}")
    public Movie getById(@PathVariable("id") int id) { //adnotacja mówiąca, że chcemy zmienną w adresie
        return movieRepository.getById(id);
    }

    @PostMapping("")
    //@RequestBody - Spring wie, że lista przyjdzie w JSON razem z zapytaniem
    //aplikacja widzi, że na serwer (/movies) idzie POST w JSON, więc
    //obsługuje go poprzez wywołanie movieRepository.save()
    public int add(@RequestBody List<Movie> movies){
        return movieRepository.save(movies);
    }

    @PutMapping("/{id}") //aktualizujemy id podane w ścieżce
    //PathVariable - argumentem id ze ścieżki
    public int update(@PathVariable("id") int id,
                      @RequestBody Movie updatedMovie){
        Movie movie = movieRepository.getById(id);
        if(movie != null){
            movie.setName(updatedMovie.getName());
            movie.setRating(updatedMovie.getRating());

            movieRepository.update(movie);
            return 1;
        }else{
            //w prawdziwym projekcie REST API powinno się zwrócić
            //odpowiedni kod błędu tu 404, wyżej 200;
            return -1;
        }
    }

    @PatchMapping("/{id}")
    public int partiallyUpdate(@PathVariable("id") int id,
                               @RequestBody Movie updatedMovie){
        Movie movie = movieRepository.getById(id);
        if(movie != null){
            if(updatedMovie.getName() != null)
                movie.setName(updatedMovie.getName());
            if(updatedMovie.getRating() > 0)
                movie.setRating(updatedMovie.getRating());

            movieRepository.patch(movie);

            return 1;
        }else{
            return -1;
        }
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id){
        return movieRepository.delete(id);
    }
}
