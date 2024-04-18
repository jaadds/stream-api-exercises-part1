package com.example.mysolutions;

import com.example.domain.Director;
import com.example.domain.Movie;
import com.example.service.InMemoryMovieService;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Excercise1 {
    public static void main(String[] args) {

        Collection<Movie> movies = InMemoryMovieService.getInstance().findAllMovies();
//        Iterator<Movie> movieIterator = movies.iterator();
//        int i = 0;
//        while (i++ < 3){
//            Movie movie = movieIterator.next();
//            System.out.println(movie);
//            System.out.println(movie.getTitle());
//            System.out.printf("%20s %n",movie.getImdb());
//            System.out.printf("%30s %n",movie.getDirectors());
//            System.out.printf("%20s %n",movie.getGenres());
//        }
//        List<Director> directors =
//                movies.stream().map(movie -> movie.getDirectors()).flatMap( list -> list.stream()).collect(Collectors.toList());
//        System.out.println("Length of Directors : "+directors.size());
//        System.out.println("Distinct Directors : "+ directors.stream().distinct().count());
        Map<Director, Long> counts =
                movies.stream().map(movie -> movie.getDirectors())
                        .flatMap(list -> list.stream())
                        .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        counts.forEach((director, aLong) -> System.out.printf("%30s : %10d %n",director, aLong));

        for(Map.Entry<Director, Long> entry : counts.entrySet()){
            int size =
                    InMemoryMovieService.getInstance()
                            .findAllMoviesByDirectorId(entry.getKey().getId()).size();
            assert size == entry.getValue().intValue();
        }
    }


}
