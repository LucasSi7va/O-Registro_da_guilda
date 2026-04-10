package com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Enums;

import com.Infnet.O.Registro.da.Guilda.Model.Entity.Dominio.Aventureiro;

import java.util.Arrays;

public enum ClasseAventureiro {
    GUERREIRO,
    MAGO,
    ARQUEIRO,
    CLERIGO,
    LADINO;


    public static ClasseAventureiro buscar(String texto) {
        return Arrays.stream(values()).filter(c -> c.name().equalsIgnoreCase(texto)).
                findFirst().
                orElseThrow(() -> new RuntimeException("Classe " + texto + " invalido!"));
    }

}
