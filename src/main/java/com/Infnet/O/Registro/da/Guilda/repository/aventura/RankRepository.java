package com.Infnet.O.Registro.da.Guilda.repository.aventura;

import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.Status;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RankRepository extends JpaRepository<ParticipacaoEmMissao , Long> {

    @Query("""
SELECT NEW com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO(
p.aventureiro.id , p.aventureiro.nome , count(p.id) , sum(p.recompensaOuro) , sum(case when p.destaque = true then 1 else 0 end)
)
FROM ParticipacaoEmMissao p 
WHERE  p.dataRegistro BETWEEN :inicio AND :fim 
AND (:status IS NULL OR p.missao.status = :status)
GROUP BY p.aventureiro.id , p.aventureiro.nome 
ORDER BY SUM(p.recompensaOuro) DESC , COUNT(p.id) DESC
""")
    List<RankAventureiroDTO> gerarRank(
            LocalDateTime inicio,
            LocalDateTime fim ,
            Status status);


    @Query("""
    SELECT new com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO(
        m.titulo, 
        m.status, 
        m.nivelPerigo, 
        COUNT(pa.id), 
        SUM(pa.recompensaOuro)
    )
    FROM ParticipacaoEmMissao pa 
    JOIN pa.missao m
    WHERE m.dataCriacao BETWEEN :inicio AND :fim
    GROUP BY m.titulo, m.status, m.nivelPerigo
    ORDER BY SUM(pa.recompensaOuro) DESC
""")
    List<RankMissaoDetalhadoDTO> gerarRankMissao(
            @Param("inicio")LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );



}
