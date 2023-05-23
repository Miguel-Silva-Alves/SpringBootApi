package br.com.treinaweb.springbootAPI;

import br.com.treinaweb.springbootAPI.entity.Pessoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

}
