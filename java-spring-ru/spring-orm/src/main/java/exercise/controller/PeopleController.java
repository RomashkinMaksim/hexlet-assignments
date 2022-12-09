package exercise.controller;

import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {

    // Автоматически заполняем значение поля
    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person getPerson(@PathVariable long id) {
        return this.personRepository.findById(id);
    }

    @GetMapping(path = "")
    public Iterable<Person> getPeople() {
        return this.personRepository.findAll();
    }

    @PostMapping()
    public void createPeople(@RequestBody Person person){
        this.personRepository.save(person);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        this.personRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public void updatePeople(@RequestBody Person person, @PathVariable long id){
        person.setId(id);
        this.personRepository.save(person);
    }
}
