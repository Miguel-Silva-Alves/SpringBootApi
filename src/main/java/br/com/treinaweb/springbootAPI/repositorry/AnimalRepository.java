package br.com.treinaweb.springbootAPI.repositorry;

import br.com.treinaweb.springbootAPI.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long>{
}
