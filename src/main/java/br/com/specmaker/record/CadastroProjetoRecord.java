package br.com.specmaker.record;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


public record CadastroProjetoRecord (

        Long id,
        @NotBlank
        String produto,
        @NotBlank
        String cliente,
        @NotBlank
        String nomeProjeto,
        @NotBlank
        String responsavel,
        @NotNull @Valid
        LocalDate dataInicioProjeto,
        @NotNull @Valid List<EspecificacaoRecord> especificacoes


) {
}
