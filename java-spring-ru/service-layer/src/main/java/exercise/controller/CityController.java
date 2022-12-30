package exercise.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping(path = "/cities/{id}")
    public Map getCityInfo(@PathVariable long id) throws JsonProcessingException {
        return weatherService.getCityInfo(id);
    }

    @GetMapping(path = "/search")
    public List<Map> queryCities(@RequestParam(required = false) String name) throws JsonProcessingException  {
        List<City> cities;
        if(name == null)
            cities = cityRepository.findAllByOrderByNameAsc();
        else
            cities = cityRepository.findByNameStartingWithIgnoreCase(name);

        return cities.stream()
                .map(el -> {
                    try {
                        Map<String, String> info = weatherService.getCityInfo(el.getId());
                        return info.entrySet().stream()
                                .filter(map -> map.getKey().equals("temperature") || map.getKey().equals("name"))
                                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}

