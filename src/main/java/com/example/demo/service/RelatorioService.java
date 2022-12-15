package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class RelatorioService {

	NotificacaoService notificacaoService;
	
	@Async
	public void gerarRelatorio() throws InterruptedException {
		log.info("Tarefa iniciada com sucesso.");
		Thread.sleep(10000);

		notificacaoService.publicar("Tarefa finalizada com sucesso.");
	}
}
