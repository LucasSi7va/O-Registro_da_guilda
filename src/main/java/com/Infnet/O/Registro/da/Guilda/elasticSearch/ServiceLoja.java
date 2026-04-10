package com.Infnet.O.Registro.da.Guilda.elasticSearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.Infnet.O.Registro.da.Guilda.elasticSearch.Model.GuildaLoja;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ServiceLoja {

    // utilizei ElasticsearchClient para fazer operacoes no cluster como buscar
    private  final ElasticsearchClient client;


    public List<GuildaLoja> buscarNome(String termo) throws IOException {
        SearchResponse<GuildaLoja> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q
                                .match(m -> m
                                        .field("nome")
                                        .query(termo)
                                )
                        ),
                GuildaLoja.class
        );

        return response.hits().hits().stream()
                .map(h -> h.source())
                .toList();
    }



    public List<GuildaLoja> buscarDescricao(String termo) throws IOException {
    SearchResponse<GuildaLoja> response = client.search(p ->
            p.index("guilda_loja")
                    .query(q -> q
                            .match(m -> m
                                    .field("descricao")
                                    .query(termo)
                            )
                    ), GuildaLoja.class
    );

        return response.hits().hits().stream()
                .map(h -> h.source())
                .toList();
    }




    public List<GuildaLoja> buscarDescricaoExatas(String termo) throws IOException {
        SearchResponse<GuildaLoja> response = client.search(p ->
                p.index("guilda_loja")
                        .query(q -> q
                                .matchPhrase(m -> m
                                        .field("descricao")
                                        .query(termo)
                                )
                        ), GuildaLoja.class
        );

        return response.hits().hits().stream()
                .map(h -> h.source())
                .toList();
    }

// eu estudei sobre fuzziness e descobri que utilizar "AUTO" ajudaria muito em caso de o usuario pesquisar errado como esppd porque alem de ele encontrar 2 erros ele encontra 2 a 5 erros
    public List<GuildaLoja> buscarFuzzy(String termo) throws IOException {
        SearchResponse<GuildaLoja> response = client.search(p ->
                p.index("guilda_loja")
                        .query(q -> q
                                .match(m -> m
                                        .field("nome")
                                        .query(termo)
                                        .fuzziness("AUTO")
                                )
                        ), GuildaLoja.class
        );
        return response.hits().hits().stream()
                .map(h -> h.source())
                .toList();
    }

// utilizei multiMatch para buscar o mesmo termo em nome e descricao
    public List<GuildaLoja> buscarMultiCampos(String termo) throws IOException {
        SearchResponse<GuildaLoja> response = client.search(p ->
                p.index("guilda_loja")
                        .query(q -> q
                                .multiMatch(m -> m
                                        .fields("nome" , "descricao")
                                        .query(termo)
                                        .fuzziness("AUTO")
                                )
                        ), GuildaLoja.class
        );

        return response.hits().hits().stream()
                .map(h -> h.source())
                .toList();
    }

    // aqui fiz .filter que serve para filtrar especifico categoria e achar a descricao dentro dessa categoria
    // e utilizei term para filtrar especifico termo
    public List<GuildaLoja> filtrarPorCategoria(String termo , String categoria) throws IOException {
        SearchResponse<GuildaLoja> response = client.search(p -> p
                .index("guilda_loja")
                .query(q -> q
                        .bool(b -> b
                                .must(m -> m
                                        .match(t  -> t
                                                .field("descricao")
                                                .query(termo)
                                                .fuzziness("AUTO")
                                        )
                                )
                                .filter(f -> f
                                        .term(t -> t
                                                .field("categoria")
                                                .value(categoria)
                                        )
                                )
                        )
                ) ,
                GuildaLoja.class
        );


        return response.hits().hits().stream()
                .map(h -> h.source())
                .toList();

    }

    // aqui eu utilizei gte e lte para filtrar o preco de acordo com o minimo e maximo que o usuario digitou
    public List<GuildaLoja> produtosFaixaPreco(double min, double max) throws IOException {
        SearchResponse<GuildaLoja> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q
                                .range(r -> r
                                        .number(n -> n
                                        .field("preco")
                                        .gte(min)
                                        .lte(max)
                                )
                                )
                        ),
                GuildaLoja.class
        );
        return response.hits().hits().stream()
                .map(h -> h.source())
                .toList();
    }


    public List<GuildaLoja> filtrarCategoriaRaridadeMaxMin(String categoria , String raridade , double min , double max) throws IOException {
        SearchResponse<GuildaLoja> response = client.search(p -> p
                        .index("guilda_loja")
                        .query(q -> q
                                .bool(b -> b
                                        .filter(f -> f
                                                .term(m -> m.field("categoria").value(categoria))
                                        )
                                        .filter(f -> f
                                                .term(m -> m.field("raridade").value(raridade))
                                        )
                                        .filter(f -> f
                                                .range(r -> r
                                                        .number(n -> n
                                                                .field("preco")
                                                                .gte(min)
                                                                .lte(max)
                                                        )
                                                )
                                        )
                                )
                        ) ,
                GuildaLoja.class
        );


        return response.hits().hits().stream()
                .map(h -> h.source())
                .toList();

    }


    public Map<String , Long> quantidadePorCategoria() throws IOException{
    SearchResponse<Void> response = client.search(s -> s
            .index("guilda_loja")
            .size(0)
            .aggregations("contagem_por_categoria" , a -> a
                    .terms(t -> t
                            .field("categoria")
                    )
            ) ,
            Void.class
    );

        Map<String, Long> resultado = new HashMap<>();
        response.aggregations().get("contagem_por_categoria").sterms().buckets().array().forEach(bucket -> {
            resultado.put(bucket.key().stringValue(), bucket.docCount());
        });

    return resultado;
    }

    public Map<String, Long> quantidadePorRaridade() throws IOException {

        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("contagem_por_raridade" , a -> a
                                .terms(t -> t
                                        .field("raridade")
                                )
                        ) ,
                Void.class
        );

        Map<String, Long> resultado = new HashMap<>();
        response.aggregations().get("contagem_por_raridade").sterms().buckets().array().forEach(bucket -> {
            resultado.put(bucket.key().stringValue(), bucket.docCount());
        });

        return resultado;
    }


    public Double mediaPreco() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("media_preco" , a -> a
                        .avg(t -> t
                                .field("preco")
                        )
                ) ,
                Void.class
        );


        return response.aggregations().get("media_preco").avg().value();
    }

    // aqui eu utilizei o AgregationRange para agrupar os valores
    // paricido como Group by em banco de dados
    public Map<String , Long> faixaPrecos() throws IOException {

       SearchResponse<Void> response = client.search(s -> s
               .index("guilda_loja")
               .size(0)
               .aggregations("faixa_preco" , a -> a
                       .range(r -> r
                               .field("preco")
                               .ranges(
                                       AggregationRange.of(rr -> rr.key("Abaixo de 100").to(100.0)),
                                       AggregationRange.of(rr -> rr.key("De 100 a 300").from(100.0).to(300.0)),
                                       AggregationRange.of(rr -> rr.key("De 300 a 700").from(300.0).to(700.0)),
                                       AggregationRange.of(rr -> rr.key("Acima de 700").from(700.0))
                               )
                       )
               ) ,
               Void.class
       );

        Map<String, Long> resultado = new HashMap<>();

        response.aggregations()
                .get("faixa_preco")
                .range()
                .buckets()
                .array()
                .forEach(bucket -> {
                    resultado.put(bucket.key().toString() , bucket.docCount());
                });

        return resultado;

    }





    }
