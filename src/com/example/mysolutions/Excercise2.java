package com.example.mysolutions;

import com.example.dao.InMemoryWorldDao;
import com.example.domain.City;
import com.example.domain.Country;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Excercise2 {
    // Find the most populated city of each continent
    public static void main(String[] args) {

        List<City> cities = InMemoryWorldDao.getInstance().findAllCities();

        int i = 0;
        while (i++ < 3){
            System.out.println(cities.get(i));
        }

        

        List<Country> countries = InMemoryWorldDao.getInstance().findAllCountries();
        Map<String,String> continentMap =
                countries.stream().collect(Collectors.toMap(Country::getCode,
                Country::getContinent));
        i=0;

       Map<String,Integer> maxContinent =  cities.stream().map( city -> Map.entry(city,
                        continentMap.get(city.getCountryCode())))
                .collect(Collectors.groupingBy(entry -> entry.getValue(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy(
                                        Comparator.comparingInt(value -> value.getKey().getPopulation())),
                                cityStringEntry -> cityStringEntry.get().getKey().getPopulation())
                        )
                );

        maxContinent.forEach((key,value) -> System.out.printf("Max population of Continent: %s " +
                "= %d %n",key,value));

    }
}
