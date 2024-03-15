package com.netdeal.colaboradores.restapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class EncriptadorSenhaTest {

    private EncriptadorSenha encriptadorSenha;

    @BeforeEach
    public void setup() {
        encriptadorSenha = new EncriptadorSenha();
    }

    @Test
    public void testEncriptarSenha() throws NoSuchAlgorithmException {
        // Mock do MessageDigest
        MessageDigest messageDigest = mock(MessageDigest.class);
        when(messageDigest.digest("senha".getBytes())).thenReturn(new byte[] { 10, 20, 30 });

        EncriptadorSenha encriptadorSenhaMock = new EncriptadorSenha() {
            @Override
            protected MessageDigest getMessageDigest() {
                return messageDigest;
            }
        };

        // Test
        String senhaEncriptada = encriptadorSenhaMock.encriptarSenha("senha");

        assertEquals("0a141e", senhaEncriptada);
    }

    @Test
    public void testEncriptarSenhaComExcecao() {
        // Test
        String senhaEncriptada = encriptadorSenha.encriptarSenha("senha");

        // Verificar
        assertEquals(null, senhaEncriptada);
    }
}
