package com.netdeal.colaboradores.restapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private ColaboradorController colaboradorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        colaboradorController = new ColaboradorController(colaboradorRepository, encriptadorSenha);
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
        novoColaborador.setSenha("senhaTeste"); // Defina uma senha válida para o teste

        // Configuração do mock do encriptador de senha
        when(encriptadorSenha.encriptarSenha("senhaTeste")).thenReturn("senhaCriptografada");

        // Test
        colaboradorController.createColaborador(novoColaborador);

        // Verificar se o método encriptarSenha foi chamado com a senha correta
        verify(encriptadorSenha).encriptarSenha("senhaTeste");

        // Verificar se o método save do colaboradorRepository foi chamado com o novo colaborador
        verify(colaboradorRepository).save(novoColaborador);
    }

    @Test
    public void testUpdateSenhaColaborador() {
        // Mock data
        Colaborador colaborador = new Colaborador();
        colaborador.setId((long) 1);
        colaborador.setSenha("senhaAntiga");
        when(colaboradorRepository.findById(1)).thenReturn(Optional.of(colaborador));

        // Test
        Colaborador result = colaboradorController.updateSenhaColaborador(1, "novaSenha");

        // Verify
        assertEquals("novaSenha", result.getSenha());
        verify(colaboradorRepository).save(colaborador);
    }

    @Test
    public void testDeleteColaborador() {
        // Test
        colaboradorController.deleteColaborador(1);

        // Verify
        verify(colaboradorRepository).deleteById(1);
    }

}
