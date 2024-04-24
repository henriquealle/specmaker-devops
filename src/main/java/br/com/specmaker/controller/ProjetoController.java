package br.com.specmaker.controller;


import br.com.specmaker.entity.Projeto;
import br.com.specmaker.record.CadastroProjetoRecord;
import br.com.specmaker.record.ListagemProjetosRecord;
import br.com.specmaker.service.ProjetoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;
    private static final Logger logger = LogManager.getLogger(ProjetoController.class);

    @GetMapping
    public Page<ListagemProjetosRecord> listar(@PageableDefault(size=10,
            sort={"nomeProjeto"}) Pageable paginacao){
        logger.info("listando projetos");
        final List<ListagemProjetosRecord> projetosRecords = projetoService.findAll(paginacao).stream()
                .map(ListagemProjetosRecord::new)
                .collect(Collectors.toList());

        return new PageImpl<>(projetosRecords, paginacao, projetosRecords.size());
    }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid CadastroProjetoRecord cadastroProjetoRecord){
        logger.info("gravando projeto ", cadastroProjetoRecord);
        projetoService.cadastrar( new Projeto(cadastroProjetoRecord) );
        logger.info("projeto cadastrado com sucesso");
    }


}
