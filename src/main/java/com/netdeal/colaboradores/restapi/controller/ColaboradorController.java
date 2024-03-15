package com.netdeal.colaboradores.restapi.controller;

import org.springframework.web.bind.annotation.*;

import com.netdeal.colaboradores.restapi.entity.Colaborador;
import com.netdeal.colaboradores.restapi.repository.ColaboradorRepository;
import com.netdeal.colaboradores.restapi.service.EncriptadorSenha;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ColaboradorController {

    private final ColaboradorRepository colaboradorRepository;
    private final EncriptadorSenha encriptadorSenha;

    public ColaboradorController(ColaboradorRepository colaboradorRepository, EncriptadorSenha encriptadorSenha) {
        this.colaboradorRepository = colaboradorRepository;
        this.encriptadorSenha = encriptadorSenha;
    }

    @GetMapping("/colaboradores")
    public List<Colaborador> getAllColaboradores() {
        return colaboradorRepository.findAll();
    }

    @GetMapping("/colaborador/{id}")
    public Colaborador getColaboradorById(@PathVariable int id) {
        return colaboradorRepository.findById(id).orElse(null);
    }

    @PostMapping("/colaborador")
    public void createColaborador(@RequestBody Colaborador novoColaborador) {
        String senhaCriptografada = encriptadorSenha.encriptarSenha(novoColaborador.getSenha());
        int forcaSenha = calcularPontuacaoSenha(novoColaborador.getSenha());
        novoColaborador.setForcaSenha(forcaSenha);
        novoColaborador.setSenha(senhaCriptografada);
        colaboradorRepository.save(novoColaborador);
    }

    @PutMapping("/colaborador/{id}/senha")
    public Colaborador updateSenhaColaborador(@PathVariable int id, @RequestBody String novaSenha) {
        Colaborador colaborador = colaboradorRepository.findById(id).orElse(null);
        if (colaborador != null) {
            colaborador.setSenha(novaSenha);
            colaboradorRepository.save(colaborador);
        }
        return colaborador;
    }

    @DeleteMapping("/colaborador/{id}")
    public void deleteColaborador(@PathVariable int id) {
        colaboradorRepository.deleteById(id);
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
