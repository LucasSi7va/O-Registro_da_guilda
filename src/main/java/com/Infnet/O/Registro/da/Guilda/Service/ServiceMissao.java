package com.Infnet.O.Registro.da.Guilda.Service;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.PartipantesDaMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.PainelTatico.PainelTaticoMissao;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.MissaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.painelTaticoMissao.PainelTaticoMissaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.ParticipacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceMissao {

private final MissaoRepository missaoRepository;
private final ParticipacaoRepository participacaoRepository;
private final PainelTaticoMissaoRepository painelTaticoMissaoRepository;

// tp2

    public Page<MissaoDTO> buscarMissao(FiltroGlobal filtroMissao , Pageable pageable) {

        return missaoRepository.filtrarMissoes(
                filtroMissao.status(),
                filtroMissao.perigo(),
                filtroMissao.inicio(),
                filtroMissao.fim(),
                pageable
        );
    }


    public DetalharMissaoDTO detalharMissao(Long id) {
        Missao missao = missaoRepository.findById(id).orElseThrow();

        List<ParticipacaoEmMissao> participacao = participacaoRepository.findByMissaoId(id);

        List<PartipantesDaMissaoDTO> partipantesDaMissaoDTOS = participacao.stream().map(p -> new PartipantesDaMissaoDTO(
                p.getAventureiro().getId(),
                p.getAventureiro().getNome(),
                p.getPapel().name(),
                p.getRecompensaOuro(),
                p.getDestaque()
        )).toList();

        return new DetalharMissaoDTO(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelPerigo(),
                missao.getDataCriacao(),
                partipantesDaMissaoDTOS
        );

    }



    //==============================================================================================//
    // tp3


    @Cacheable(value = "paineisTaticos" , key = "#root.methodName")
    public List<PainelTaticoMissao> buscarquinzeDias() {
        // coloquei o salvo so para testar se o cache esta funcionando
       // System.out.println("salvo");
        // eu so adicionei o Cacheable e EnableCaching para que o spring ler o cache
        // utilizei o key = #root.methodName utilizei porque ja que utilizo a LocalDateTime.now() resumindo para pegar a data atual e calcular os ultimos 15 dias atras para o banco de dados, ele ja captura isso congela , manda para a memoria ram, que nao precisaria ter o processo
        // todo de procurar os ultimos 15 dias
        return painelTaticoMissaoRepository
                .findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(
                        LocalDateTime.now().minusDays(15)
                );
    }
}
