package com.Infnet.O.Registro.da.Guilda;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.Especie;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.NivelPerigo;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.PapelNaMissao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.Status;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Missao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Usuario;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceMissao;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.CompanheiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.AventureiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.MissaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.audit.OrganizacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.ParticipacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.audit.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import static com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.ClasseAventureiro.GUERREIRO;
import static com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.ClasseAventureiro.MAGO;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Import(com.Infnet.O.Registro.da.Guilda.Service.ServiceMissao.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ServiceMissaoTest {

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
    private ServiceMissao serviceMissao;

    @Autowired
    private EntityManager entityManager;

    private Long missaoId;

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

            this.missaoId = missao.getId();
            participacaoRepository.save(ParticipacaoEmMissao.builder().dataRegistro(LocalDateTime.now()).destaque(true).papel(PapelNaMissao.LIDER).recompensaOuro(BigDecimal.valueOf(500)).aventureiro(aventureiroLucas).missao(missao).build());

        }

        // criar 10 aventureiros aleatorios
        for (int i = 0; i < 10; i++)
        {
            Aventureiro aventureiro1 =  aventureiroRepository.save(Aventureiro.builder().organizacao(orgExistente).usuario_cadastro(usuarioExistente).nome("aventureiro: " + i).classe(MAGO).nivel(i + 10).ativo(true).build());
        }

    }





    @Test
    void listarMissoes(){
        Page<Missao> missoes = missaoRepository.findAll(PageRequest.of(0, 10));
        assertThat(missoes).isNotNull();
        assertThat(missoes.getTotalElements()).isGreaterThan(0);

        Page<ParticipacaoEmMissao> participacoes = participacaoRepository.findAll(PageRequest.of(0, 10));
        assertThat(participacoes).isNotNull();
        assertThat(participacoes.getTotalElements()).isGreaterThan(0);


        missoes.forEach(missao -> {
            System.out.println("Missao: " + missao.getTitulo() + " | Status: " + missao.getStatus() + " | NivelPerigo: " + missao.getNivelPerigo());
            System.out.println("Data de inicio: " + missao.getDataInicio() + " | Data de termino: " + missao.getDataFim());
        });

        participacoes.forEach(participacao -> {
            System.out.println("Aventureiro: " + participacao.getAventureiro().getNome() + " | Papel na missao: " + participacao.getPapel());
            System.out.println("Recompensa em ouro: " + participacao.getRecompensaOuro());
            System.out.println("Missao: " + participacao.getMissao().getStatus());
        });

    }


    @Test
    void buscarMissao(){

        LocalDateTime dataInicio = LocalDateTime.now().minusDays(1);
        LocalDateTime dataFim = LocalDateTime.now().plusDays(1);

        FiltroGlobal filtro = new FiltroGlobal(null , null , null , Status.EM_ANDAMENTO , NivelPerigo.CRITICO , dataInicio , dataFim);

        Page<MissaoDTO> missaoDTO = serviceMissao.buscarMissao(filtro, PageRequest.of(0, 10));

        assertThat(missaoDTO.getContent()).isNotEmpty();


        missaoDTO.getContent().forEach(m -> {
                    System.out.println("Missao: " + m.titulo() + " | Status: " + m.status() + " | NivelPerigo: " + m.nivelPerigo());
                    System.out.println("Data de inicio: " + m.dataInicio() + " | Data de termino: " + m.dataTermino());
                }
        );

    }



    @Test
    void detalharMissao(){
        entityManager.flush();
        entityManager.clear();

        DetalharMissaoDTO detalharMissaoDTO = serviceMissao.detalharMissao(this.missaoId);
        assertThat(detalharMissaoDTO).isNotNull();
        System.out.println("Missao: " + detalharMissaoDTO.titulo() + " | Status: " + detalharMissaoDTO.status() + " | NivelPerigo: " + detalharMissaoDTO.nivelPerigo() + " | participacao_em_missao:  " + detalharMissaoDTO.participantes());

    }


}
