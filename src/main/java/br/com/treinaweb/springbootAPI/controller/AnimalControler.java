package br.com.treinaweb.springbootAPI.controller;

import br.com.treinaweb.springbootAPI.entity.Animal;
import br.com.treinaweb.springbootAPI.entity.Pessoa;
import br.com.treinaweb.springbootAPI.repositorry.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@RestController
public class AnimalControler {
    @Autowired
    private AnimalRepository _animalRepository;

    @RequestMapping(value = "/animal", method = RequestMethod.GET)
    public List<Animal> Get(){
        return _animalRepository.findAll();
    }

    @RequestMapping(value = "/animal/{id}", method = RequestMethod.GET)
    public ResponseEntity<Animal> GetById(@PathVariable(value = "id") long id){
        Optional<Animal> animal = _animalRepository.findById(id);
        if(animal.isPresent()){
            return new ResponseEntity<>(animal.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/animal", method = RequestMethod.POST)
    public ResponseEntity<Animal> Post(@Valid @RequestBody Animal animal){
        System.out.println(animal);
        try{
            Animal saved = _animalRepository.save(animal);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/animal/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Animal> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Animal newAnimal){
        Optional<Animal> oldAnimal = _animalRepository.findById(id);
        if(oldAnimal.isPresent()){
            Animal animal = oldAnimal.get();
            animal.setNome(newAnimal.getNome());
            animal.setRaca(newAnimal.getraca());
            try{
                _animalRepository.save(animal);
                return new ResponseEntity<>(animal, HttpStatus.OK);
            }catch (DataIntegrityViolationException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/animal/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
        Optional<Animal> animal = _animalRepository.findById(id);
        if(animal.isPresent()){
            _animalRepository.delete(animal.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
