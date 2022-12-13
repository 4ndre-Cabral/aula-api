package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arquivo {
	
	private String nome;
	private String urlDownload;
	private String tipoArquivo;
	private long size;

}
