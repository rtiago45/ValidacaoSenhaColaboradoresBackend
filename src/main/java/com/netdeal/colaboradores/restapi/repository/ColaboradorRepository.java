package com.netdeal.colaboradores.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netdeal.colaboradores.restapi.entity.Colaborador;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

	
}
