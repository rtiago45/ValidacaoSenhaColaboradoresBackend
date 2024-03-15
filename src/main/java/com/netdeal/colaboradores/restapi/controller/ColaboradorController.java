package com.netdeal.colaboradores.restapi.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netdeal.colaboradores.restapi.entity.Colaborador;
import com.netdeal.colaboradores.restapi.repository.ColaboradorRepository;
import com.netdeal.colaboradores.restapi.service.EncriptadorSenha;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ColaboradorController {
    
    private final ColaboradorRepository colaboradorRepository;
    private final EncriptadorSenha encriptadorSenha;
    
    public ColaboradorController(ColaboradorRepository colaboradorRepository, EncriptadorSenha encriptadorSenha) {
        this.colaboradorRepository = colaboradorRepository;
        this.encriptadorSenha = encriptadorSenha;
    }
     
    // Endpoint para obter todos os colaboradores
    @GetMapping("/colaboradores")
    public List<Colaborador> getAllColaboradores() {
        return colaboradorRepository.findAll();
    }
    
    // Endpoint para obter um colaborador por ID
    @GetMapping("/colaborador/{id}")
    public Colaborador getColaboradorById(@PathVariable int id) {
        return colaboradorRepository.findById(id).orElse(null);
    }
    
    
    
    
    // Endpoint para adicionar um novo colaborador
    @PostMapping("/colaborador")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createColaborador(@RequestBody Colaborador novoColaborador) {
        // Criptografar a senha antes de salvar o colaborador
        String senhaCriptografada = encriptadorSenha.encriptarSenha(novoColaborador.getSenha());
        novoColaborador.setSenha(senhaCriptografada);
        
        if (novoColaborador.getCargo() == null) {
            novoColaborador.setCargo("Sem cargo");
        }
        
        colaboradorRepository.save(novoColaborador);
    }
    
    
    // Endpoint para atualizar a senha de um colaborador por ID
    @PutMapping("/colaborador/{id}/senha")
    public Colaborador updateSenhaColaborador(@PathVariable int id, @RequestBody String novaSenha) {
        Colaborador colaborador = colaboradorRepository.findById(id).orElse(null);
        if (colaborador != null) {
            colaborador.setSenha(novaSenha);
            colaboradorRepository.save(colaborador);
        }
        return colaborador;
    }
    
    // Endpoint para excluir um colaborador por ID
    @DeleteMapping("/colaborador/{id}")
    public void deleteColaborador(@PathVariable int id) {
        colaboradorRepository.deleteById(id);
    }
    
    @PostMapping("/colaborador/senha/forca")
    public ResponseEntity<Map<String, String>> calcularForcaSenha(@RequestBody Map<String, String> requestBody) {
        String senha = requestBody.get("senha");
        String nome = requestBody.get("nome");
        String cargo = requestBody.get("cargo");

        if (senha == null || nome == null || cargo == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Por favor, forneça senha, nome e cargo no corpo da solicitação."));
        }

        int pontuacao = calcularPontuacaoSenha(senha);

        String nivelForcaSenha = calcularNivelForcaSenha(pontuacao);

        Map<String, String> response = new HashMap<>();
        response.put("pontuacao", String.valueOf(pontuacao));
        response.put("nivelForcaSenha", nivelForcaSenha);

        return ResponseEntity.ok(response);
    }

    private int calcularPontuacaoSenha(String senha) {
       
        int comprimento = senha.length();
        int pontuacao = 0;

        pontuacao += calcularPontuacaoComprimento(comprimento);
        pontuacao += calcularPontuacaoLetrasMaiusculas(senha);
        pontuacao += calcularPontuacaoLetrasMinusculas(senha);
        pontuacao += calcularPontuacaoNumeros(senha);
        pontuacao += calcularPontuacaoCaracteresEspeciais(senha);
        pontuacao += calcularPontuacaoCaracteresMeio(senha);
        pontuacao += calcularPontuacaoRequisitos(senha);
        return pontuacao;
    }

    private String calcularNivelForcaSenha(int pontuacao) {
        if (pontuacao >= 100) {
            return "FORTE";
        }
        if (pontuacao >= 80) {
            return "BOM";
        }
        if (pontuacao >= 50) {
            return "MEDIANA";
        }
        return "RUIM";
    }

    private int calcularPontuacaoComprimento(int comprimento) {
        return Math.min(8, comprimento) * 4;
    }

    private int calcularPontuacaoLetrasMaiusculas(String senha) {
        int contagem = senha.replaceAll("[^A-Z]", "").length();
        return (senha.length() - contagem) * 2;
    }

    private int calcularPontuacaoLetrasMinusculas(String senha) {
        int contagem = senha.replaceAll("[^a-z]", "").length();
        return (senha.length() - contagem) * 2;
    }

    private int calcularPontuacaoNumeros(String senha) {
        int contagem = senha.replaceAll("[^0-9]", "").length();
        return contagem * 4;
    }

    private int calcularPontuacaoCaracteresEspeciais(String senha) {
        int contagem = senha.replaceAll("[A-Za-z0-9]", "").length();
        return contagem * 6;
    }

    private int calcularPontuacaoCaracteresMeio(String senha) {
        int contagem = senha.substring(1, senha.length() - 1).replaceAll("[A-Za-z0-9]", "").length();
        return contagem * 2;
    }

    private int calcularPontuacaoRequisitos(String senha) {
        int contagem = 0;
        if (senha.matches(".*[A-Z].*")) contagem++;
        if (senha.matches(".*[a-z].*")) contagem++;
        if (senha.matches(".*\\d.*")) contagem++;
        if (senha.matches(".*[^A-Za-z0-9].*")) contagem++;
        return contagem * 2;
    }


}
