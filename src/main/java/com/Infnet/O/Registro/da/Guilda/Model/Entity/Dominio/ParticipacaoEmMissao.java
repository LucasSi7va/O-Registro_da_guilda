package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.PapelNaMissao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "participacao_missao" , schema = "aventura" , uniqueConstraints = @UniqueConstraint(columnNames = {"aventureiro_id" , "missao_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipacaoEmMissao {

    @Id
    private Long id;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false)
    private PapelNaMissao papel;

    @Column(name = "recompensa_ouro" , precision = 10 , scale = 2)
    @Min(0)
    private BigDecimal recompensaOuro;

    @Column(name = "destaque", nullable = false)
    private Boolean destaque;

    @Column(name = "data_registro", nullable = false , updatable = false)
    @CreationTimestamp
    private LocalDateTime dataRegistro;

}

