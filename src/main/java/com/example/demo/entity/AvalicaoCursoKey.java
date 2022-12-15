package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AvalicaoCursoKey implements Serializable{

	private static final long serialVesionUID = 1L;
	
	@Column(name = "estudante_id")
	Long estudanteId;
	
	@Column(name = "curso_id")
	Long cursoId;
}
