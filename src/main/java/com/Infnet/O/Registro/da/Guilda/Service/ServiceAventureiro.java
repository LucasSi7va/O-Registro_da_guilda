package com.Infnet.O.Registro.da.Guilda.Service;

import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AdicionarAventureiroDto;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.DetalhesDoAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Companheiro.CompanheiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.ClasseAventureiro;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.Especie;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.ParticipacaoEmMissao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Usuario;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.AventureiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.CompanheiroRepository;
import com.Infnet.O.Registro.da.Guilda.repository.audit.OrganizacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.aventura.ParticipacaoRepository;
import com.Infnet.O.Registro.da.Guilda.repository.audit.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.Optional;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceAventureiro {


    // Repositorios
// ==================================================================================================//

    private final AventureiroRepository repository;
    private final ParticipacaoRepository participacaoRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CompanheiroRepository companheiroRepository;


// ==================================================================================================//


    // tp1
    public AdicionarAventureiroDto adicionarAventureiro(AdicionarAventureiroDto aventureiroDTO) {
        Organizacao organizacao = organizacaoRepository.findById(aventureiroDTO.organizacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Organizacao nao encontrada"));

        Usuario usuario = usuarioRepository.findById(aventureiroDTO.usuarioCadastroId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        Aventureiro novoAventureiro = Aventureiro.builder()
                .organizacao(organizacao)
                .usuario_cadastro(usuario)
                .nome(aventureiroDTO.nome())
                .classe(aventureiroDTO.classe())
                .nivel(aventureiroDTO.nivel())
                .ativo(aventureiroDTO.ativo())
                .build();

        repository.save(novoAventureiro);

      return aventureiroDTO;

    }


    public AventureiroDTO atualizar(Long id , AventureiroDTO aventureiroDTO) {

        Aventureiro aventureiro = repository.findById(id).orElseThrow(() -> new RuntimeException("Aventureiro nao encontrado"));

        aventureiro.setNome(aventureiroDTO.nome());
        aventureiro.setNivel(aventureiroDTO.nivel());
        aventureiro.setAtivo(aventureiroDTO.ativo());
        aventureiro.setClasse(ClasseAventureiro.buscar(aventureiroDTO.classe().toString()));

        Aventureiro aventureiroSalvo = repository.save(aventureiro);

        return new AventureiroDTO(
                aventureiroSalvo.getId(),
                aventureiroSalvo.getNome(),
                aventureiroSalvo.getClasse(),
                aventureiroSalvo.getNivel(),
                aventureiroSalvo.isAtivo()
        );
    }


    public AventureiroDTO statusAtivo(Long id, boolean ativo) {

        Aventureiro aventureiro = repository.findById(id).orElseThrow(() -> new RuntimeException("Aventureiro nao encontrado"));

        aventureiro.setAtivo(ativo);

        Aventureiro aventureiroSalvo = repository.save(aventureiro);

        return new AventureiroDTO(
                aventureiroSalvo.getId(),
                aventureiroSalvo.getNome(),
                aventureiroSalvo.getClasse(),
                aventureiroSalvo.getNivel(),
                aventureiroSalvo.isAtivo()
        );
    }


    public CompanheiroDTO atualizarCompanheiro(Long id , CompanheiroDTO companheiroDTO) {

        Aventureiro aventureiro = repository.findById(id).orElseThrow(() -> new RuntimeException("Aventureiro nao encontrado"));

// utilizei Optional.ofNullable serve para verificar se o aventureiro possui ou nao um companheiro
// porque se o companheiro for null ele irar criar um novo companheiro, se nao for null ele irar atualizar os dados do companheiro
        Companheiro companheiro = Optional.ofNullable(aventureiro.getCompanheiro())
                        .orElseGet(() -> {
                            Companheiro novo = new Companheiro();
                            novo.setAventureiro(aventureiro);
                            aventureiro.setCompanheiro(novo);
                            return novo;
                        });

        companheiro.setNome(companheiroDTO.nome());
        companheiro.setEspecie(Especie.buscar(companheiroDTO.especie().toString()));
        companheiro.setLealdade(companheiroDTO.lealdade());


         Companheiro salvo = companheiroRepository.save(companheiro);

        return new CompanheiroDTO(salvo.getNome(), salvo.getEspecie(), salvo.getLealdade());
    }


    public AventureiroDTO deletarCompanheiro(Long id) {
        Companheiro companheiro = companheiroRepository.findById(id).orElseThrow(() -> new RuntimeException("Companheiro nao encontrado"));

        Aventureiro aventureiro = companheiro.getAventureiro();
        aventureiro.setCompanheiro(null);
        repository.save(aventureiro);
        companheiroRepository.delete(companheiro);

        return new AventureiroDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
        );
    }


    // ============================================================================================== //




    // tp2


    public Page<AventureiroDTO> filtrar(FiltroGlobal filtroAventureiro, Pageable pageable) {
        return  repository.findByFiltro(
            filtroAventureiro.ativo(),
            filtroAventureiro.classe(),
            filtroAventureiro.nivel(),
            pageable
    );
    }



    public Page<AventureiroDTO> buscarNome(String nome , Pageable pageable) {
     return repository.buscarPorNome(nome , pageable);
    }


    public DetalhesDoAventureiroDTO buscarPorCompleto(Long id) {
        Aventureiro aventureiro = repository.findById(id).orElseThrow();

        Optional<ParticipacaoEmMissao> participacaoEmMIssao = participacaoRepository.findFirstByAventureiroIdOrderByDataDeRegistroDesc(id);

        AventureiroDTO aventureiroDTO = new AventureiroDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
                );

            return new DetalhesDoAventureiroDTO(
                    aventureiroDTO,
                    Optional.ofNullable(aventureiro.getCompanheiro())
                            .map(Companheiro::getNome)
                            .orElse("Nenhum"),
                    participacaoRepository.countByAventureiroId(id),

                    participacaoEmMIssao.map(p -> p.getMissao().getTitulo())
                            .orElse("Nenhuma missão registrada")
            );

            //==============================================================================================//
    }


}


