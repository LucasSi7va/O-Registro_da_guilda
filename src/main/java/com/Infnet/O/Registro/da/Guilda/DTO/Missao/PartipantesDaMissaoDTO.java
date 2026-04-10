package com.Infnet.O.Registro.da.Guilda.DTO.Missao;

public record PartipantesDaMissaoDTO(
        Long aventureiroId,
        String nome ,
        String classe ,
        java.math.@jakarta.validation.constraints.Min(0) BigDecimal recompensa ,
        Boolean foiMVP
        ) {
}
