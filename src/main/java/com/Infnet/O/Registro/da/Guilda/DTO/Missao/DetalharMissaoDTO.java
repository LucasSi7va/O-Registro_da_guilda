package com.Infnet.O.Registro.da.Guilda.DTO.Missao;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.NivelPerigo;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record DetalharMissaoDTO(
        Long id,
        String titulo ,
        Status status,
        NivelPerigo nivelPerigo,
        LocalDateTime dataCriacao,
        List<PartipantesDaMissaoDTO> participantes
) {
}
