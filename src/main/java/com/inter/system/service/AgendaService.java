package com.inter.system.service;

import com.inter.system.model.Agenda;
import com.inter.system.model.Cliente;
import com.inter.system.model.Profissional;
import com.inter.system.model.Servico;
import com.inter.system.model.ServicoAgendado;
import com.inter.system.repository.AgendaRepository;
import com.inter.system.repository.ServicoAgendadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class AgendaService {

    private final AgendaRepository repo;
    private final ServicoAgendadoRepository servicoAgendadoRepo;

    @PersistenceContext
    private EntityManager em;

    public AgendaService(AgendaRepository repo, ServicoAgendadoRepository servicoAgendadoRepo) {
        this.repo = repo;
        this.servicoAgendadoRepo = servicoAgendadoRepo;
    }

    @Transactional(readOnly = true)
    public List<Agenda> listarTodos() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Agenda> listarAtivos() {
        return repo.findByStatus((short) 1);
    }

    @Transactional(readOnly = true)
    public Agenda buscarPorId(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada: " + id));
    }

    public Agenda salvar(Agenda agenda) {
        hidratarReferencias(agenda);
        // garante vínculo bidirecional
        if (agenda.getServicosAgendados() == null) {
            agenda.setServicosAgendados(new ArrayList<>());
        }
        for (ServicoAgendado sa : agenda.getServicosAgendados()) {
            sa.setAgenda(agenda);
            if (sa.getStatus() == null) sa.setStatus((short) 1);
        }

        // salva agenda
        Agenda saved = repo.save(agenda);

        // caso não haja cascade no mapeamento, garante persist dos itens
        for (ServicoAgendado sa : saved.getServicosAgendados()) {
            if (sa.getId() == null) {
                servicoAgendadoRepo.save(sa);
            }
        }
        return saved;
    }

    public Agenda atualizar(Integer id, Agenda dados) {
        Agenda ag = buscarPorId(id);

        // atualiza campos simples
        ag.setCliente(null);
        ag.setDataHora(dados.getDataHora());
        ag.setTempoTotal(dados.getTempoTotal());
        ag.setValorTotal(dados.getValorTotal());
        ag.setStatus(dados.getStatus());

        // re-hidrata cliente/itens/refs e substitui coleção
        if (dados.getCliente() != null && dados.getCliente().getId() != null) {
            ag.setCliente(em.getReference(Cliente.class, dados.getCliente().getId()));
        }
        // limpa itens antigos
        if (ag.getServicosAgendados() != null) {
            Iterator<ServicoAgendado> it = ag.getServicosAgendados().iterator();
            while (it.hasNext()) {
                ServicoAgendado old = it.next();
                it.remove();
                // se não houver orphanRemoval no mapeamento, opcionalmente remover:
                // servicoAgendadoRepo.delete(old);
            }
        } else {
            ag.setServicosAgendados(new ArrayList<>());
        }

        if (dados.getServicosAgendados() != null) {
            for (ServicoAgendado in : dados.getServicosAgendados()) {
                ServicoAgendado sa = new ServicoAgendado();
                sa.setAgenda(ag);
                sa.setStatus(in.getStatus() == null ? (short) 1 : in.getStatus());

                if (in.getServico() != null && in.getServico().getId() != null) {
                    sa.setServico(em.getReference(Servico.class, in.getServico().getId()));
                }
                if (in.getProfissional() != null && in.getProfissional().getId() != null) {
                    sa.setProfissional(em.getReference(Profissional.class, in.getProfissional().getId()));
                }
                ag.getServicosAgendados().add(sa);
            }
        }

        return repo.save(ag);
    }

    public void inativar(Integer id) {
        Agenda ag = buscarPorId(id);
        ag.setStatus((short) 2);
        repo.save(ag);
    }

    @Transactional(readOnly = true)
    public List<Agenda> listarNoPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repo.findByDataHoraBetween(inicio, fim);
    }

    @Transactional(readOnly = true)
    public boolean profissionalDisponivel(Integer profissionalId, LocalDateTime inicio, int duracaoMin) {
        LocalDateTime fim = inicio.plusMinutes(duracaoMin);
        LocalDateTime janelaIni = inicio.minusDays(1);
        LocalDateTime janelaFim = fim.plusDays(1);

        List<ServicoAgendado> itens = servicoAgendadoRepo
                .findDoProfissionalNoPeriodo(profissionalId, janelaIni, janelaFim);

        for (ServicoAgendado sa : itens) {
            Agenda a = sa.getAgenda();
            LocalDateTime aIni = a.getDataHora();
            LocalDateTime aFim = aIni.plusMinutes(a.getTempoTotal() != null ? a.getTempoTotal() : 0);
            boolean sobrepoe = !(aFim.compareTo(inicio) <= 0 || aIni.compareTo(fim) >= 0);
            if (sobrepoe) return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public List<Integer> profissionaisIndisponiveis(List<Integer> profIds, LocalDateTime inicio, int duracaoMin) {
        List<Integer> indisponiveis = new ArrayList<>();
        for (Integer id : profIds) {
            if (!profissionalDisponivel(id, inicio, duracaoMin)) {
                indisponiveis.add(id);
            }
        }
        return indisponiveis;
    }

    // -------- helpers --------
    private void hidratarReferencias(Agenda agenda) {
        if (agenda.getCliente() != null && agenda.getCliente().getId() != null) {
            agenda.setCliente(em.getReference(Cliente.class, agenda.getCliente().getId()));
        }
        if (agenda.getServicosAgendados() != null) {
            for (ServicoAgendado sa : agenda.getServicosAgendados()) {
                if (sa.getServico() != null && sa.getServico().getId() != null) {
                    sa.setServico(em.getReference(Servico.class, sa.getServico().getId()));
                }
                if (sa.getProfissional() != null && sa.getProfissional().getId() != null) {
                    sa.setProfissional(em.getReference(Profissional.class, sa.getProfissional().getId()));
                }
            }
        }
    }
}
