package com.inter.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 11, unique = true)
    private String telefone;

    @Email
    @Column(length = 30, unique = true)
    private String email;

    @Size(min = 8)
    @Column(nullable = false, length = 60)
    private String senha;

    @NotNull
    @Min(1)
    @Max(2)
    @Column(nullable = false)
    private Short status;   
    
    protected void setId(Integer id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setStatus(Short status) { this.status = status; }
    
    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public Short getStatus() { return status; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    
}
