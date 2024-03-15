package com.netdeal.colaboradores.restapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ColaboradorTest {

    private Colaborador colaborador;

    @BeforeEach
    public void setup() {
        colaborador = new Colaborador(1L, "João", "Desenvolvedor", "senha", new Date(), "123456789");
    }

    @Test
    public void testGettersAndSetters() {
        // Test
        assertEquals(1L, colaborador.getId());
        assertEquals("João", colaborador.getNome());
        assertEquals("Desenvolvedor", colaborador.getCargo());
        assertEquals("senha", colaborador.getSenha());
        assertEquals("123456789", colaborador.getTelefone());

        // Alterando valores
        colaborador.setId(2L);
        colaborador.setNome("Maria");
        colaborador.setCargo("Gerente");
        colaborador.setSenha("outrasenha");
        colaborador.setTelefone("987654321");

        // Verificar alterações
        assertEquals(2L, colaborador.getId());
        assertEquals("Maria", colaborador.getNome());
        assertEquals("Gerente", colaborador.getCargo());
        assertEquals("outrasenha", colaborador.getSenha());
        assertEquals("987654321", colaborador.getTelefone());
    }

    @Test
    public void testToString() {
        // Test
        assertEquals("Colaborador [id=1, nome=João, cargo=Desenvolvedor, senha=senha, dataNascimento=" + colaborador.getDataNascimento() + ", telefone=123456789]", colaborador.toString());
    }
}
