package br.com.specmaker.service;


import br.com.specmaker.entity.Projeto;
import br.com.specmaker.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> findAll() {
       return projetoRepository.findAll();
    }

    public List<Projeto> findAll(Pageable pageable) {
        return projetoRepository.findAll(pageable).toList();
    }

    public void cadastrar(Projeto projeto) {
        projetoRepository.save(projeto);
    }

    public void excluir(Long id) {
        projetoRepository.deleteById(id);
    }

    public Projeto buscarPorId(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }
}
