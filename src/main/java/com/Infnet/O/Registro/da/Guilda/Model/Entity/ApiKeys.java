package com.Infnet.O.Registro.da.Guilda.Model.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "API_KEYS" , schema = "audit")
public class ApiKeys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id" , nullable = false)
    private Organizacao organizacao;

    @JoinColumn(name = "nome" , unique = true)
    private String nome;

    @JoinColumn(name = "key_hash")
    private String keyHash;

    @JoinColumn(name = "ativo")
    private boolean ativo;

    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_used_at", nullable = false , updatable = false)
    private LocalDateTime lastUsedAt;

}
