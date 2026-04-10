package com.Infnet.O.Registro.da.Guilda.repository.aventura;

;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ParticipacaoRepository extends JpaRepository<ParticipacaoEmMissao, Long> {



    // buscar por completo
// ============================================================================================================================

    @Query("SELECT p FROM ParticipacaoEmMissao p WHERE p.aventureiro.id = :id ORDER BY p.dataRegistro DESC LIMIT 1")
    Optional<ParticipacaoEmMissao> findFirstByAventureiroIdOrderByDataDeRegistroDesc(Long id);

    Long countByAventureiroId(Long aventureiroId);

// ============================================================================================================================

    @Query("SELECT p FROM ParticipacaoEmMissao p join fetch p.aventureiro where p.missao.id = :missaoId")
    List<ParticipacaoEmMissao> findByMissaoId(Long missaoId);




}
