package br.com.treinaweb.springbootAPI.controller;

import br.com.treinaweb.springbootAPI.entity.Animal;
import br.com.treinaweb.springbootAPI.entity.Pessoa;
import br.com.treinaweb.springbootAPI.repositorry.AnimalRepository;
import br.com.treinaweb.springbootAPI.repositorry.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
public class AnimalControler {
    @Autowired
    private AnimalRepository _animalRepository;
    @Autowired
    private PessoaRepository _pessoaRepository;

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

    @RequestMapping(value = "/AnimalRaca/{raca}", method = RequestMethod.GET)
    public List<Animal> GetByIds(@PathVariable(value = "raca") String raca) {
        List<Animal> animals = new ArrayList<>();
        //percorrer os animais
        for (Animal animal : _animalRepository.findAll()) {
            String raca_animal = animal.getraca();
            if (raca_animal.equals(raca)) {
                animals.add(animal);
            }
        }
        return animals;
    }

    @RequestMapping(value = "/animal", method = RequestMethod.POST)
    public ResponseEntity<Animal> Post(@Valid @RequestBody Animal animal){
        System.out.println(animal);
        long id_pessoa = animal.getPessoa().getId(); // pega o id-pessoa que esta no animal pq aqui eu nap passo comp parametro nada de pessoa
        Optional<Pessoa> pessoa = _pessoaRepository.findById(id_pessoa); //_pessoaRepositorio tem todas as pessoas cadastradas, encontra pessoa especifica pelo id
        if(pessoa.isPresent()){
            animal.setPessoa(pessoa.get());
        }
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
