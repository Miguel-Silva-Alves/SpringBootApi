package br.com.treinaweb.springbootAPI.controller;

import br.com.treinaweb.springbootAPI.entity.Animal;
import br.com.treinaweb.springbootAPI.entity.Pessoa;
import br.com.treinaweb.springbootAPI.repositorry.AnimalRepository;
import br.com.treinaweb.springbootAPI.repositorry.PessoaRepository;
import org.apache.tomcat.util.descriptor.web.WebXml;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class PessoaControler {
    @Autowired
    private PessoaRepository _pessoaRepository;
    @Autowired
    private AnimalRepository _animalRepository;

    @RequestMapping(value = "/pessoa", method = RequestMethod.GET)
    public List<Pessoa> Get(){
        return _pessoaRepository.findAll();
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pessoa> GetById(@PathVariable(value = "id") long id){
        Optional<Pessoa> pessoa = _pessoaRepository.findById(id);
        if(pessoa.isPresent()){
            return new ResponseEntity<>(pessoa.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/pessoaAnimal/{id_pessoa}", method = RequestMethod.GET)
    public List<Animal> GetByIds(@PathVariable(value = "id_pessoa") long id_pessoa){
        List<Animal> animals = new ArrayList<>();

        //percorrer os animais
        for(Animal animal : _animalRepository.findAll()){
            Pessoa pessoa = animal.getPessoa();
            //verificar se a pessoa é nula
            if(pessoa != null){
                long id_pessoa_animal = pessoa.getId();
                //printar eles
                System.out.println(id_pessoa_animal);
                //printar so os animais com o id_pessoa
                if (id_pessoa_animal == id_pessoa){
                    System.out.println(animal);
                    //adicionar na lista de animais
                    animals.add(animal);
                }
            }
        }


        return animals;
    }

    @RequestMapping(value = "/pessoa", method = RequestMethod.POST)
    public ResponseEntity<Pessoa> Post(@Valid @RequestBody Pessoa pessoa){
        System.out.println(pessoa);
        boolean cpf_validate = pessoa.validate_cpf(pessoa.getCpf());
        if (!cpf_validate){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            Pessoa saved = _pessoaRepository.save(pessoa);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Pessoa> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Pessoa newPessoa){
        Optional<Pessoa> oldPessoa = _pessoaRepository.findById(id);
        if(oldPessoa.isPresent()){
            Pessoa pessoa = oldPessoa.get();
            pessoa.setNome(newPessoa.getNome());
            try{
                _pessoaRepository.save(pessoa);
                return new ResponseEntity<>(pessoa, HttpStatus.OK);
            }catch (DataIntegrityViolationException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
        Optional<Pessoa> pessoa = _pessoaRepository.findById(id);
        if(pessoa.isPresent()){
            _pessoaRepository.delete(pessoa.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
