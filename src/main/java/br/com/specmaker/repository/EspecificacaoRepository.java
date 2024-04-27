package br.com.specmaker.repository;

import br.com.specmaker.entity.Especificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EspecificacaoRepository extends JpaRepository<Especificacao, Integer> {

    List<Especificacao> findByProjetoId(Long projetoId);
}
