package com.Infnet.O.Registro.da.Guilda.Service;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceRank {

private final RankRepository rankRepository;


    public List<RankAventureiroDTO> buscarRank(FiltroGlobal filtroRank){

        List<RankAventureiroDTO> ranking = rankRepository.gerarRank(filtroRank.inicio() , filtroRank.fim() , filtroRank.status());
        return ranking;
    }


    public List<RankMissaoDetalhadoDTO> DetalhesRank(FiltroGlobal filtroRank) {

        List<RankMissaoDetalhadoDTO> detalhes = rankRepository.gerarRankMissao(filtroRank.inicio() ,filtroRank.fim());

        return detalhes;
    }
}
