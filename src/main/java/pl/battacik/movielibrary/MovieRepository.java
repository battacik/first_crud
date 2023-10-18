package pl.battacik.movielibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository //informuje Springa że klasa pełni rolę repozytorium do DB
public class MovieRepository {

    /*informuje że pole jdbcTemplate powinno zostać wstrzyknięte
    (dostarczone) przez kontener Springa - czyli nie trzeba tworzyć
    obiektu JdbcTemplate - Spring sam zarządza jego życiem*/
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Movie> getAll(){
        //zostanie zwrócona lista obiektów typu Movie
        return jdbcTemplate.query("SELECT id, name, rating FROM movie",
                BeanPropertyRowMapper.newInstance(Movie.class));
    }

    //zwraca jeden film o konkretnym endpoincie movie/{id}
    public Movie getById(int id){
        return jdbcTemplate.queryForObject("SELECT id, name, rating FROM movie " +
                "WHERE id = ?", BeanPropertyRowMapper.newInstance(Movie.class),id);
    }

    public int save(List<Movie> movies) {
        movies.forEach(movie -> jdbcTemplate
                .update("INSERT INTO movie(name,rating) VALUES(?,?)",
                        movie.getName(), movie.getRating()));
        //metoda dodająca to jdbcTemplate.update


        return 1;
    }

    public int update(Movie movie){
        jdbcTemplate.update("UPDATE movie SET " +
                "name=?, rating=? WHERE id=?",
                movie.getName(), movie.getRating(), movie.getId());

        return 1;
    }
    public int patch(Movie movie){
        return jdbcTemplate.update("UPDATE movie SET " +
                "name=?, rating=? WHERE id=?",
                movie.getName(),movie.getRating(),movie.getId());
    }

    public int delete(int id){
        jdbcTemplate.update("DELETE FROM movie WHERE id=?",id);

        return 1;
    }
}
