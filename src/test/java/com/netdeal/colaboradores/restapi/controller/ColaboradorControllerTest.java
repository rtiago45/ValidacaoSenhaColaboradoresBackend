package com.netdeal.colaboradores.restapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.netdeal.colaboradores.restapi.entity.Colaborador;
import com.netdeal.colaboradores.restapi.repository.ColaboradorRepository;
import com.netdeal.colaboradores.restapi.service.EncriptadorSenha;

public class ColaboradorControllerTest {

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @Mock
    private EncriptadorSenha encriptadorSenha;

    @InjectMocks
    private ColaboradorController colaboradorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllColaboradores() {
        // Mock data
        List<Colaborador> colaboradores = Arrays.asList(new Colaborador(), new Colaborador());
        when(colaboradorRepository.findAll()).thenReturn(colaboradores);

        // Test
        List<Colaborador> result = colaboradorController.getAllColaboradores();

        // Verify
        assertEquals(colaboradores.size(), result.size());
    }

    @Test
    public void testGetColaboradorById() {
        // Mock data
        Colaborador colaborador = new Colaborador();
        when(colaboradorRepository.findById(1)).thenReturn(Optional.of(colaborador));

        // Test
        Colaborador result = colaboradorController.getColaboradorById(1);

        // Verify
        assertEquals(colaborador, result);
    }

    @Test
    public void testCreateColaborador() {
        // Mock data
        Colaborador novoColaborador = new Colaborador();
        novoColaborador.setNome("Teste");
        novoColaborador.setCargo("CargoTeste");
        novoColaborador.setSenha("senhaTeste");

        // Configuração do mock do encriptador de senha
        when(encriptadorSenha.encriptarSenha("senhaTeste")).thenReturn("senhaCriptografada");

        // Test
        colaboradorController.createColaborador(novoColaborador);

        // Verify
        verify(encriptadorSenha).encriptarSenha("senhaTeste");
        verify(colaboradorRepository).save(novoColaborador);
    }

    @Test
    public void testUpdateSenhaColaborador() {
        // Mock data
        Colaborador colaborador = new Colaborador();
        colaborador.setId(1L);
        colaborador.setSenha("senhaAntiga");
        when(colaboradorRepository.findById(1)).thenReturn(Optional.of(colaborador));

        // Test
        Colaborador result = colaboradorController.updateSenhaColaborador(1, "novaSenha");

        // Verify
        assertEquals("novaSenha", result.getSenha());
        verify(colaboradorRepository).save(colaborador);
    }

 
}
