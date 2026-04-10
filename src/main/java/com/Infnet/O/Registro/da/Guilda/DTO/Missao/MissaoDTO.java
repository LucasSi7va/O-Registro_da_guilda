package com.Infnet.O.Registro.da.Guilda.DTO.Missao;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.NivelPerigo;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.Status;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;

import java.time.LocalDateTime;

public record MissaoDTO(
        Long id,
        String titulo ,
        Status status,
        NivelPerigo nivelPerigo,
        LocalDateTime dataCriacao,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino
        ) {}
