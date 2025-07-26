// src/main/java/com/inter/system/model/Usuario.java
package com.inter.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Usuario implements Serializable {

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
    @Min(1) @Max(2)
    @Column(nullable = false)
    private Short status;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Short getStatus() { return status; }
    public void setStatus(Short status) { this.status = status; }
}
