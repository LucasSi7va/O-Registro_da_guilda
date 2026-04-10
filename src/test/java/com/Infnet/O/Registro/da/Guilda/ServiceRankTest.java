package com.Infnet.O.Registro.da.Guilda;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Rank.RankMissaoDetalhadoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.*;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Usuario;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceRank;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.CompanheiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.AventureiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.MissaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.audit.OrganizacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.ParticipacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.audit.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.ClasseAventureiro.GUERREIRO;
import static com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.ClasseAventureiro.MAGO;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(com.Infnet.O.Registro.da.Guilda.Service.ServiceRank.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ServiceRankTest {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;
    @Autowired
    private CompanheiroRepository companheiroRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    @Autowired
    private ServiceRank serviceRank;


    @BeforeEach
    void setUp(){
        // organizacao
        Organizacao orgExistente = organizacaoRepository.findById(1L).orElseThrow(() -> new RuntimeException("Organizacao nao encontrada"));

        // Usuario
        Usuario usuarioExistente = usuarioRepository.findById(1L).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        // Aventureiro
        Aventureiro aventureiroLucas = aventureiroRepository.save(Aventureiro.builder().organizacao(orgExistente).usuario_cadastro(usuarioExistente).nome("Lucas").classe(GUERREIRO).nivel(50).ativo(true).build());

        // Companheiro
        Companheiro companheiro = companheiroRepository.save(Companheiro.builder().aventureiro(aventureiroLucas).nome("Zion").especie(Especie.LOBO).lealdade(100).build());

        aventureiroLucas.setCompanheiro(companheiro);

        // criar 4 participacao da missao para aventureiroLucas
        for (int i = 1; i <= 4; i++){

            Missao missao = missaoRepository.save(Missao.builder().dataInicio(LocalDateTime.now()).dataFim(LocalDateTime.now()).titulo("Missao " + i).nivelPerigo(NivelPerigo.CRITICO).status(Status.EM_ANDAMENTO).organizacao(orgExistente).build());
            participacaoRepository.save(ParticipacaoEmMissao.builder().dataRegistro(LocalDateTime.now()).destaque(true).papel(PapelNaMissao.LIDER).recompensaOuro(BigDecimal.valueOf(500)).aventureiro(aventureiroLucas).missao(missao).build());

        }

        // criar 10 aventureiros aleatorios
        for (int i = 0; i < 10; i++)
        {
            Aventureiro aventureiro1 =  aventureiroRepository.save(Aventureiro.builder().organizacao(orgExistente).usuario_cadastro(usuarioExistente).nome("aventureiro: " + i).classe(MAGO).nivel(i + 10).ativo(true).build());
        }

    }





    @Test
    void buscarRank(){

        LocalDateTime inicio = LocalDateTime.of(2026, 4, 1, 12, 0);
        LocalDateTime fim = LocalDateTime.now();

        FiltroGlobal filtro = new FiltroGlobal(null , null , null , Status.EM_ANDAMENTO , null , inicio , fim);

        List<RankAventureiroDTO> rankAventureiroDTOS = serviceRank.buscarRank(filtro);

        assertThat(rankAventureiroDTOS).isNotEmpty();
        RankAventureiroDTO rankAventureiroDTO = rankAventureiroDTOS.get(0);
        System.out.println("nome: " + rankAventureiroDTO.nome() + " | total de recompensas: " + rankAventureiroDTO.somaDeRecompensasRecebidas() + " | quantidade de destaques obtidos: " + rankAventureiroDTO.quantidadeDestaquesObtidos() + " | Total de Participacoes: " + rankAventureiroDTO.TotalDeParticipacoes());

    }


    @Test
    void buscarRankDetalhado(){
        LocalDateTime inicio = LocalDateTime.of(2026, 4, 1, 12, 0);
        LocalDateTime fim = LocalDateTime.now();

        FiltroGlobal filtro = new FiltroGlobal(null , null , null , null , null , inicio , fim);

        List<RankMissaoDetalhadoDTO> rankMissaoDetalhadoDTOS = serviceRank.DetalhesRank(filtro);

        assertThat(rankMissaoDetalhadoDTOS).isNotEmpty();
        RankMissaoDetalhadoDTO rankMissaoDetalhadoDTO = rankMissaoDetalhadoDTOS.get(0);
        System.out.println("Nome: " + rankMissaoDetalhadoDTO.nome() + " | Status: " + rankMissaoDetalhadoDTO.status() + " | Nivel de perigo: " + rankMissaoDetalhadoDTO.nivelPerigo() + " | Soma das recompensa: " + rankMissaoDetalhadoDTO.somaDeRecompensasRecebidas() + " | Total de Participantes: " + rankMissaoDetalhadoDTO.totalDeParticipantes());
    }
}
