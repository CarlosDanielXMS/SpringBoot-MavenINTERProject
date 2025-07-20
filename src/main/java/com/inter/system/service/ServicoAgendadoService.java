// ServicoAgendadoService.java
package com.inter.system.service;

import com.inter.system.model.ServicoAgendado;
import com.inter.system.model.ServicoAgendadoId;
import com.inter.system.repository.ServicoAgendadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ServicoAgendadoService {

    private final ServicoAgendadoRepository repo;

    public ServicoAgendadoService(ServicoAgendadoRepository repo) {
        this.repo = repo;
    }

    // public List<ServicoAgendado> listarPorAgenda(Integer idAgenda) {
    //     return repo.findByAgendaId(idAgenda);
    // }

    public ServicoAgendado buscarPorId(Integer idAgenda, Integer idServico, Integer idProfissional) {
        return repo.findById(new ServicoAgendadoId(idAgenda, idServico, idProfissional))
                   .orElseThrow(() -> new IllegalArgumentException("Serviço Agendado não encontrado"));
    }

    public ServicoAgendado criar(ServicoAgendado sa) {
        return repo.save(sa);
    }

    public ServicoAgendado atualizar(ServicoAgendado sa) {
        buscarPorId(sa.getAgenda().getId(), sa.getServico().getId(), sa.getProfissional().getId());
        return repo.save(sa);
    }

    public void excluir(Integer idAgenda, Integer idServico, Integer idProfissional) {
        repo.deleteById(new ServicoAgendadoId(idAgenda, idServico, idProfissional));
    }
}
