package com.Infnet.O.Registro.da.Guilda.elasticSearch;

import com.Infnet.O.Registro.da.Guilda.elasticSearch.Model.GuildaLoja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ControlerLojaGuilda {
    @Autowired
    private ServiceLoja serviceLoja;

    //http://localhost:8080/produtos/busca/nome?termo=espada
    @GetMapping("/busca/nome")
    public ResponseEntity<List<GuildaLoja>> buscarNome(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(serviceLoja.buscarNome(termo));
    }

    //http://localhost:8080/produtos/busca/descricao?termo=cura
    // coloquei cura porem ele retorna vazio pois nao existe no termo cura, se colocar raro ele ira aparecer
    @GetMapping("/busca/descricao")
    public ResponseEntity<List<GuildaLoja>> buscarDescricao(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(serviceLoja.buscarDescricao(termo));
    }


    // item vazio:
//http://localhost:8080/produtos/busca/frase?termo=cura superior
// item com frase:
// http://localhost:8080/produtos/busca/frase?termo=tem utilizado
    @GetMapping("/busca/frase")
    public ResponseEntity<List<GuildaLoja>> buscarDescricaoExatas(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(serviceLoja.buscarDescricaoExatas(termo));
    }

//http://localhost:8080/produtos/busca/fuzzy?termo=espdaa
    @GetMapping("/busca/fuzzy")
    public ResponseEntity<List<GuildaLoja>> buscarFuzzy(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(serviceLoja.buscarFuzzy(termo));
    }


//http://localhost:8080/produtos/busca/multicampos?termo=dragao
    @GetMapping("busca/multicampos")
    public ResponseEntity<List<GuildaLoja>> buscarMultiCampos(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(serviceLoja.buscarMultiCampos(termo));
    }

  // item vazio:
  // http://localhost:8080/produtos/busca/com-filtro?termo=pocao&categoria=pocoes
    // item com frase:
//    http://localhost:8080/produtos/busca/com-filtro?termo=batalha&categoria=pergaminhos
    @GetMapping("busca/com-filtro")
    public ResponseEntity<List<GuildaLoja>> filtrarPorCategoria(@RequestParam String termo , @RequestParam String categoria) throws IOException {
        return ResponseEntity.ok(serviceLoja.filtrarPorCategoria(termo , categoria));
    }

//http://localhost:8080/produtos/busca/faixa-preci?min=50&max=500
    @GetMapping("busca/faixa-preci")
    public ResponseEntity<List<GuildaLoja>> produtosFaixaPreco(@RequestParam int min , @RequestParam int max) throws IOException {
        return ResponseEntity.ok(serviceLoja.produtosFaixaPreco(min , max));
    }

//http://localhost:8080/produtos/busca/avancada?categoria=armas&raridade=raro&min=200&max=1000
    @GetMapping("busca/avancada")
    public ResponseEntity<List<GuildaLoja>> filtrarCategoriaRaridadeMaxMin(@RequestParam String categoria , @RequestParam String raridade ,  @RequestParam double min , @RequestParam double max) throws IOException {
        return ResponseEntity.ok(serviceLoja.filtrarCategoriaRaridadeMaxMin(categoria , raridade, min , max));
    }

//http://localhost:8080/produtos/busca/agregacoes/por-categoria
    @GetMapping("busca/agregacoes/por-categoria")
    public ResponseEntity<Map<String , Long>> quantidadePorCategoria() throws IOException {
        return ResponseEntity.ok(serviceLoja.quantidadePorCategoria());
    }

//http://localhost:8080/produtos/busca/agregacoes/por-raridade
@GetMapping("busca/agregacoes/por-raridade")
public ResponseEntity<Map<String , Long>> quantidadePorRaridade() throws IOException {
    return ResponseEntity.ok(serviceLoja.quantidadePorRaridade());
}


//http://localhost:8080/produtos/busca/agregacoes/preco-medio
@GetMapping("busca/agregacoes/preco-medio")
public ResponseEntity<Double> medioPreco() throws IOException {
    return ResponseEntity.ok(serviceLoja.mediaPreco());
}

//
@GetMapping("busca/agregacoes/faixa-preco")
public ResponseEntity<Map<String , Long>> faixaPreco() throws IOException {
    return ResponseEntity.ok(serviceLoja.faixaPrecos());
}




}