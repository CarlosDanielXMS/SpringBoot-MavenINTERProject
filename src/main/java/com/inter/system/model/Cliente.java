package com.inter.system.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cliente")
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agenda> agendamentos = new ArrayList<>();

    public void setAgendamentos(List<Agenda> agendamentos) { this.agendamentos = agendamentos; }

    public List<Agenda> getAgendamentos() { return agendamentos; }

}
