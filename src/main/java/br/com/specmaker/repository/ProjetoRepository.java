package br.com.specmaker.repository;

import br.com.specmaker.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
