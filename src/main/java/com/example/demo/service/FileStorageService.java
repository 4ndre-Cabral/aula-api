package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.FileStorageProperties;
import com.example.demo.exception.ArquivoNaoEncontradoException;
import com.example.demo.exception.UploadArquivoException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageService {

	private final Path fileStorageLocation;
	
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir());
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new UploadArquivoException("Nao foi possivel criar o diretorio", e);
		}
	}
	
	public String salvarArquivo(MultipartFile file) {
		String nomeArquivo = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if(nomeArquivo.contains("..")) {
				throw new UploadArquivoException("Arquivo invalido");
			}
			Path targetLocation = this.fileStorageLocation.resolve(nomeArquivo);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return nomeArquivo;
		} catch (IOException e) {
			throw new UploadArquivoException("Erro ao tentar salvar o arquivo", e);
		}
	}
	
	public Resource carregarArquivo(String nomeArquivo) {
		try {
			Path filePath = this.fileStorageLocation.resolve(nomeArquivo).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new ArquivoNaoEncontradoException("Arquivo nao encontrado");
			}
		} catch (Exception e) {
			throw new ArquivoNaoEncontradoException("Arquivo nao encontrado");
		}
	}
	
	public String getContentType(HttpServletRequest request, Resource resource) {
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			log.error("Nao foi possivel determinar o tipo de arquivo");
		}
		
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		return contentType;
	}
}
