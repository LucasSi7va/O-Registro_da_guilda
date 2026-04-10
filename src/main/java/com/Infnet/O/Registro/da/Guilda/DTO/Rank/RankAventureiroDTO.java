package com.Infnet.O.Registro.da.Guilda.DTO.Rank;

import java.math.BigDecimal;

public record RankAventureiroDTO(
    Long id,
    String nome,
    long TotalDeParticipacoes ,
    BigDecimal somaDeRecompensasRecebidas,
    Long quantidadeDestaquesObtidos
) {
}
