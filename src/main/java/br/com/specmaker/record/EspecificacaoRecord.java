package br.com.specmaker.record;

import jakarta.validation.constraints.NotBlank;

public record EspecificacaoRecord(Long id, @NotBlank String titulo, String urlArquivo, @NotBlank String queryId){
        }
