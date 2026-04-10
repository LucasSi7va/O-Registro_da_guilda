package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio;


import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.ClasseAventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "aventureiro" , schema = "aventura")
@AllArgsConstructor@Setter@Getter@NoArgsConstructor
@Builder
public class Aventureiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id" , nullable = false)
    private Organizacao organizacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id" , nullable = false)
    private Usuario usuario_cadastro;

    @Column(name = "nome", length = 120 , nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "classe" , nullable = false)
      private ClasseAventureiro classe;

    @Column(name = "nivel" , nullable = false)
    @NotNull @Min(1)
    private Integer nivel;

    @Column(name = "ativo" , nullable = false)
      private boolean ativo;

    @Column(name = "data_criacao" , updatable = false , nullable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao" , nullable = false)
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Companheiro companheiro;
}
