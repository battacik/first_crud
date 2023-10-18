package pl.battacik.movielibrary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//adnotacje lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private int id;
    private String name;
    private int rating;
}
