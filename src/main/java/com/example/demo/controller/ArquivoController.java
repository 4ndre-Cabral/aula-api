package com.example.demo.controller;

import java.net.http.HttpHeaders;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.Arquivo;
import com.example.demo.service.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/arquivos")
@AllArgsConstructor
public class ArquivoController {

	private FileStorageService service;
	
	@PostMapping("/upload")
	public Arquivo uploadArquivo(@RequestParam("file") MultipartFile file) {
		String nomeArquivo = service.salvarArquivo(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
									.path("/arquivos/download/")
									.path(nomeArquivo)
									.toUriString();
		return new Arquivo(nomeArquivo, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@GetMapping("/download/{nomeArquivo}")
	public ResponseEntity<Resource> downloadArquivo(@PathVariable String nomeArquivo, HttpServletRequest request) {
		Resource resource = service.carregarArquivo(nomeArquivo);
		
		String contentType = service.getContentType(request, resource);
		
		return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(contentType))
					.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; nomeArquivo=\"" + resource.getFilename())
					.body(resource);
	}
}
