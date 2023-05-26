package br.com.treinaweb.springbootAPI;

import br.com.treinaweb.springbootAPI.entity.Animal;
import br.com.treinaweb.springbootAPI.entity.Pessoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PessoaTests {

    @Test
    public void testarValidadorCPF(){
        Pessoa pessoa = new Pessoa("Miguelzinho", "111.444.777-35");
        Assertions.assertEquals(true, pessoa.validate_cpf(pessoa.getCpf()));

        Pessoa pessoa2 = new Pessoa("Miguelzinho", "111.444.777-34");
        Assertions.assertEquals(false, pessoa2.validate_cpf(pessoa2.getCpf()));

        Pessoa pessoa3 = new Pessoa("Miguelzinho", "11144477735");
        Assertions.assertEquals(true, pessoa3.validate_cpf(pessoa3.getCpf()));

        Pessoa pessoa4 = new Pessoa("Miguelzinho", "11144477738");
        Assertions.assertEquals(false, pessoa4.validate_cpf(pessoa4.getCpf()));

    }

    public void teste(){

    }

    @Test
    public void testarValordoGasto(){

        //Criar lista de animais
        List<Animal> listaAnimal = new ArrayList<Animal>();

        //Eu- Criar pessoa para o animal
        Pessoa pessoa_dono = new Pessoa(1,"Miguelzinho","11144477735",listaAnimal);

        //Criar uns 2 ou 3 animais com preços diferente
        Animal animal1 = new Animal("Jacare", 20.00, pessoa_dono);
        listaAnimal.add(animal1);

        Animal animal2 = new Animal("Dragao", 45.50, pessoa_dono);
        listaAnimal.add(animal2);

        Animal animal3 = new Animal("Pangare", 10.50, pessoa_dono);
        listaAnimal.add(animal3);

        //Cria pessoa passando lista de animais

        //colocar no validador  o valor esperado(somando os preços) e chama a funcao para calcular
        //Pessoa pessoa = new Pessoa(1,"Miguelzinho", "111.444.777-35", listaAnimal);

        //melhor dos casos com valores corretos
        Assertions.assertEquals(76.00, pessoa_dono.calcular_gasto());


        //não adiciona os animais e passa a lista vazia

        //passa um numero incorreto e ve o que acontece

    }

}
