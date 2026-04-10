package com.Infnet.O.Registro.da.Guilda.DTO.Rank;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.NivelPerigo;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.Status;

import java.math.BigDecimal;

public record RankMissaoDetalhadoDTO(
        String nome,
        Status status ,
        NivelPerigo nivelPerigo,
        long totalDeParticipantes ,
        BigDecimal somaDeRecompensasRecebidas
) {
}
