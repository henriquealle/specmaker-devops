package br.com.specmaker.record;

import br.com.specmaker.entity.Especificacao;
import br.com.specmaker.entity.Projeto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ConsultaProjetoRecord(
        Long id,
        String produto,
        String cliente,
        String nomeProjeto,
        String responsavel,
        LocalDate dataInicioProjeto,
        List<Especificacao>especificacoes,
        LocalDateTime dtCriacao,
        LocalDateTime dtAtualizacao

) {

    public ConsultaProjetoRecord(Projeto projeto){
        this(projeto.getId(),
                projeto.getProduto(),
                projeto.getCliente(),
                projeto.getNomeProjeto(),
                projeto.getResponsavel(),
                projeto.getDataInicioProjeto(),
                projeto.getEspecificacoes(),
                projeto.getDtCriacao(),
                projeto.getDtAtualizacao());
    }

}
