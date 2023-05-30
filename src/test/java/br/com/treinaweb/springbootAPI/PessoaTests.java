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
        List<Animal> listaAnimal_vazia = new ArrayList<>();
        Pessoa pessoaSemAnimais = new Pessoa(1,"Miguelzinho","11144477735",listaAnimal_vazia);
        Assertions.assertEquals(0, pessoaSemAnimais.calcular_gasto());

    }

    @Test
    public void testarValordoGastoByID(){



        //TESTE 1 - PASSA OS VALORES DE ID CORRETAMENTE COM TODOS
        //cria lista onde eu vou adicionar os animais
        List<Animal> listaAnimalByID = new ArrayList<>();

        //cria lista onde ira passar os id
        List<Long> listaID = new ArrayList<>();

        //cria pessoa que vai ser dona dos animais da lista
        Pessoa pessoa_dono = new Pessoa(1,"Miguel","11144477735",listaAnimalByID);

        //cria os animais com campo de id
        Animal animal1 = new Animal(111,"Zeus",70.00,"Cachorro",pessoa_dono);
        listaAnimalByID.add(animal1);
        listaID.add(animal1.getId());

        Animal animal2 = new Animal(222,"Zect",30.00,"Cachorro",pessoa_dono);
        listaAnimalByID.add(animal2);
        listaID.add(animal2.getId());

        Animal animal3 = new Animal(333,"Spider",20.00,"Gato",pessoa_dono);
        listaAnimalByID.add(animal3);
        listaID.add(animal3.getId());

        //chama funçao
        Assertions.assertEquals(120.00, pessoa_dono.calcular_gasto_byID(listaID));





        //TESTE 2 - PASSA OS VALORES DE ID CORRETOS MAS SEM TODOS ANIMAIS

        //cria lista onde eu vou adicionar os animais
        List<Animal> listaAnimalByID2 = new ArrayList<>();

        //cria lista onde ira passar os id
        List<Long> listaID2 = new ArrayList<>();

        //cria pessoa que vai ser dona dos animais da lista
        Pessoa pessoa_dono2 = new Pessoa(2,"Miguel","11144477735",listaAnimalByID2);

        //cria os animais com campo de id
        Animal animal4 = new Animal(444,"Zeus",20.00,"Cachorro",pessoa_dono);
        listaAnimalByID2.add(animal4);
        listaID2.add(animal4.getId());

        Animal animal5 = new Animal(555,"Zect",20.00,"Cachorro",pessoa_dono);
        listaAnimalByID2.add(animal5);
        listaID2.add(animal5.getId());

        Animal animal6 = new Animal(666,"Spider",20.00,"Gato",pessoa_dono);
        listaAnimalByID2.add(animal6);


        //chama funçao
        Assertions.assertEquals(40.00, pessoa_dono2.calcular_gasto_byID(listaID2));





        //TESTE 3 - PASSA OS VALORES DE ID INCORRETOS

        //cria lista para passar id errado
        List<Long> id_errado = new ArrayList<>();

        //popula lista com valores errados
        id_errado.add(1L);
        id_errado.add(2L);
        id_errado.add(3L);
        id_errado.add(4L);

        //chama funcao
        Assertions.assertEquals(0.00, pessoa_dono2.calcular_gasto_byID(id_errado));





        //TESTE 4 - PASSA UMA LISTA VAZIA

        //cria lista vazia onde teoricamente era pra adicionar animais
        List<Animal> animais_vazio = new ArrayList<>();

        //cria lista de ids vazio
        List<Long> id_vazio = new ArrayList<>();

        //cria pessoa sem nenhum animal vinculado
        Pessoa pessoa_sem_animais = new Pessoa(3,"Miguelzinho","11144477735", animais_vazio);

        Assertions.assertEquals(0, pessoa_sem_animais.calcular_gasto_byID(id_vazio));

    }

}
