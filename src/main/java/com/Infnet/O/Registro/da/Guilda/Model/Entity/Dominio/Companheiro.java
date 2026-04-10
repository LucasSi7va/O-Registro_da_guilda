package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio;


import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums.Especie;
import com.Infnet.O.Registro.da.Guilda.Model.Entity.Organizacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Arrays;

@Entity
@Table(name = "companheiro" , schema = "aventura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Companheiro {

@Id
private Long id;

@OneToOne
@JoinColumn(name = "aventureiro_id", nullable = false, unique = true)
@OnDelete(action = OnDeleteAction.CASCADE)
@MapsId
private Aventureiro aventureiro;

@Column(name = "nome", length = 120 , nullable = false)
private String nome;


@Enumerated(EnumType.STRING)
@Column(name = "especie" , nullable = false)
private Especie especie;

@Column(name = "indice_lealdade" , nullable = false)
@Min(0)
@Max(100)
private int lealdade;

}
