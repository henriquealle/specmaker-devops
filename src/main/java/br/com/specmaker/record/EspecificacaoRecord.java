package br.com.specmaker.record;

import jakarta.validation.constraints.NotBlank;

public record EspecificacaoRecord(@NotBlank String titulo, @NotBlank String urlArquivo){
        }
