package com.Infnet.O.Registro.da.Guilda.repository.painelTaticoMissao;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.PainelTatico.PainelTaticoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;


public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao , Long> {

    List<PainelTaticoMissao> findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(LocalDateTime inicio);
}