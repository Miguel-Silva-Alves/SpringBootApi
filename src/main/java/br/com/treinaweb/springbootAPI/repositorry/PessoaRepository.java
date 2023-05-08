package br.com.treinaweb.springbootAPI.repositorry;

import br.com.treinaweb.springbootAPI.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
