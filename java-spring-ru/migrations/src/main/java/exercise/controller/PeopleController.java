package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    JdbcTemplate jdbc;

    @PostMapping(path = "")
    public void createPerson(@RequestBody Map<String, Object> person) {
        String query = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        jdbc.update(query, person.get("first_name"), person.get("last_name"));
    }

    @GetMapping("")
    public List<Map<String, Object>> getAll() {
        String query = "SELECT * FROM PERSON";
        return jdbc.queryForList(query);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        String query = "SELECT * FROM PERSON WHERE ID = ?";
        return jdbc.queryForMap(query, new Object[]{id});
    }

    // BEGIN
    
    // END
}
