package com.netdeal.colaboradores.restapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ColaboradorTest {

    private Colaborador colaborador;

    @BeforeEach
    public void setup() {
        colaborador = new Colaborador(1L, "João", "Desenvolvedor", "senha");
    }

    @Test
    public void testGettersAndSetters() {
        // Test
        assertEquals(1L, colaborador.getId());
        assertEquals("João", colaborador.getNome());
        assertEquals("Desenvolvedor", colaborador.getCargo());
        assertEquals("senha", colaborador.getSenha());

        // Alterando valores
        colaborador.setId(2L);
        colaborador.setNome("Maria");
        colaborador.setCargo("Gerente");
        colaborador.setSenha("outrasenha");

        // Verificar alterações
        assertEquals(2L, colaborador.getId());
        assertEquals("Maria", colaborador.getNome());
        assertEquals("Gerente", colaborador.getCargo());
        assertEquals("outrasenha", colaborador.getSenha());
    }

    @Test
    public void testToString() {
    	assertEquals("Colaborador [id=1, nome=João, cargo=Desenvolvedor, senha=senha ]", colaborador.toString());

    }
}
