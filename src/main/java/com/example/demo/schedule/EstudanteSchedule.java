package com.example.demo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EstudanteSchedule {

	@Scheduled(cron = "0 0 12 * 2 1")
	public void executarTarefa() {
		log.info("Tarefa executada!");
	}
}
