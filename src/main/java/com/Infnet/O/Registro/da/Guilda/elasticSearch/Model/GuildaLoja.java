package com.Infnet.O.Registro.da.Guilda.elasticSearch.Model;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "guilda_loja")
@Getter @Setter
public class GuildaLoja {

@Id
private String id;

@Field(type = FieldType.Text , name = "nome")
private String nome;

@Field(type = FieldType.Text , name = "descricao")
private String descricao;

@Field(type = FieldType.Keyword , name = "categoria")
private String categoria;

@Field(type = FieldType.Keyword , name = "raridade")
private String raridade;

@Field(type = FieldType.Double , name = "preco")
private Double preco;

}
