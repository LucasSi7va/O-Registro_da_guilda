package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Companheiro;

import java.util.Arrays;

public enum Especie {
    LOBO ,
    CORUJA ,
    GOLEM ,
    DRAGAO_MINIATURA;

    public static Especie buscar(String texto) {
        return Arrays.stream(values()).filter(e -> e.name().equalsIgnoreCase(texto)).
                findFirst().
                orElseThrow(() -> new RuntimeException("Especie " + texto + " invalido!"));
    }
}
