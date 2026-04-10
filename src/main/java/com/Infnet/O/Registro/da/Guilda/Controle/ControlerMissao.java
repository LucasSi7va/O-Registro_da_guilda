package com.Infnet.O.Registro.da.Guilda.Controle;

import com.Infnet.O.Registro.da.Guilda.DTO.Filtros.FiltroGlobal;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.DetalharMissaoDTO;
import com.Infnet.O.Registro.da.Guilda.DTO.Missao.MissaoDTO;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.PainelTatico.PainelTaticoMissao;
import com.Infnet.O.Registro.da.Guilda.Service.ServiceMissao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missao")
@Validated
@RequiredArgsConstructor
public class ControlerMissao {

    private final ServiceMissao serviceMissao;



    // filtrar e listar missoes

    //http://localhost:8080/missao?status=EM_ANDAMENTO&perigo=CRITICO&inicio=2026-04-01T00:00:00&fim=2026-04-30T23:59:59&page=0&size=5&sort=dataInicio,asc
    @GetMapping
    public ResponseEntity<Page<MissaoDTO>> ListarMissoes(FiltroGlobal filtroMissao , Pageable pageable){
        return ResponseEntity.ok(serviceMissao.buscarMissao(filtroMissao , pageable));
    }

    //http://localhost:8080/missao/1
    @GetMapping("/{id}")
    public ResponseEntity<DetalharMissaoDTO> detalharMissao(@PathVariable Long id){
        return ResponseEntity.ok(serviceMissao.detalharMissao(id));
    }

    // como expĺicado, quando pesquiso primeira vez ele demora pouco, depois que faz o cache ele vai direto sem ter tempo para pesquisa
    //http://localhost:8080/missao/top15dias
    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> painelTatico(){
        return ResponseEntity.ok(serviceMissao.buscarquinzeDias());
    }



}
