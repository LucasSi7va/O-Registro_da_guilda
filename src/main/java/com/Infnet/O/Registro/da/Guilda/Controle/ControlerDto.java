package com.Infnet.O.Registro.da.Guilda.Controle;


import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AdicionarAventureiroDto;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.AventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Aventureiro.DetalhesDoAventureiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Companheiro.CompanheiroDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceAventureiro;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;


@RestController
@RequestMapping("/aventureiros")
@Validated
@RequiredArgsConstructor
public class ControlerDto {

    private final ServiceAventureiro serviceAventureiro;

// tp1

    // ===========================================================================//

    // json
    // ===========================================================================//
//    {
//        "nome": "Aragorn da Guilda",
//            "classe": "GUERREIRO",
//            "nivel": 15,
//            "ativo": true,
//            "organizacaoId": 1,
//            "usuarioCadastroId": 1
//    }

// http://localhost:8080/aventureiros/adicionar
    @PostMapping("/adicionar")
    public ResponseEntity<AdicionarAventureiroDto> adicionarAventureiro(@RequestBody @Valid AdicionarAventureiroDto aventureiroDTO) {
        AdicionarAventureiroDto aventureiroDTO1 = serviceAventureiro.adicionarAventureiro(aventureiroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(aventureiroDTO1);
    }

    // ===========================================================================//


    // ===========================================================================//
// json
//    {
//        "nome": "Lucas Silva - O Grão-Mestre",
//            "classe": "MAGO",
//            "nivel": 25,
//            "ativo": true
//    }
//

// http://localhost:8080/aventureiros/atualizar/1

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id , @RequestBody AventureiroDTO atualizandoaventureiro){
        AventureiroDTO aventureiroDTO = serviceAventureiro.atualizar(id , atualizandoaventureiro);

        return ResponseEntity.ok(aventureiroDTO);
    }

    // ===========================================================================//


    // ===========================================================================//


// http://localhost:8080/aventureiros/1/ativo?ativo=false
    @PatchMapping("/{id}/ativo")
    public ResponseEntity<AventureiroDTO> mudarStatus(@PathVariable Long id , @RequestParam boolean ativo) {
        AventureiroDTO aventureiroDTO = serviceAventureiro.statusAtivo(id , ativo);
        return ResponseEntity.ok(aventureiroDTO);
    }


    // ======================================================================================================================

    // http://localhost:8080/aventureiros/companheiro/1
    @PutMapping("/companheiro/adicionar/{id}")
    public ResponseEntity<CompanheiroDTO> atualizarCompanheiro(@PathVariable Long id , @RequestBody @Valid CompanheiroDTO companheiroDTO ){
        CompanheiroDTO companheiroDTO1 = serviceAventureiro.atualizarCompanheiro(id , companheiroDTO);
        return ResponseEntity.ok(companheiroDTO1);
    }


    // ======================================================================================================================

    // http://localhost:8080/aventureiros/Companheiro/remove/1
    @DeleteMapping("Companheiro/remove/{id}")
    public ResponseEntity<AventureiroDTO> deletarCompanheiro(@PathVariable Long id) {
        AventureiroDTO aventureiroDTO = serviceAventureiro.deletarCompanheiro(id);
        return ResponseEntity.ok(aventureiroDTO);
    }

    // ======================================================================================================================


    // tp2


// http://localhost:8080/aventureiros?ativo=true&classe=GUERREIRO&nivel=1&page=0&size=5&sort=nome,asc
    @GetMapping
    public ResponseEntity<Page<AventureiroDTO>> filtro(
            @Valid FiltroGlobal filtroAventureiro, Pageable pageable) {

        return  ResponseEntity.ok(serviceAventureiro.filtrar(filtroAventureiro, pageable));
    }

// http://localhost:8080/aventureiros/perfil/nome/Aventureiro_1
    @GetMapping("perfil/nome/{nome}")
    public ResponseEntity<Page<AventureiroDTO>> getNome(
            @PathVariable String nome , Pageable pageable
    ){
        return ResponseEntity.ok(serviceAventureiro.buscarNome(nome , pageable));
    }


    //http://localhost:8080/aventureiros/perfil/1
    @GetMapping("perfil/{id}")
    public ResponseEntity<DetalhesDoAventureiroDTO> verHistorico(@PathVariable Long id){
        DetalhesDoAventureiroDTO detalhesDoAventureiroDTO = serviceAventureiro.buscarPorCompleto(id);
        return ResponseEntity.ok(detalhesDoAventureiroDTO);
    }


// ======================================================================================================================


}
