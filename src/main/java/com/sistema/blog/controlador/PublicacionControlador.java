package com.sistema.blog.controlador;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.servicio.PublicacionServicio;
import com.sistema.blog.utilerias.AppConstantes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {

	@Autowired
	private PublicacionServicio publicacionServicio;

	@GetMapping
	public PublicacionRespuesta listarPublicaciones(
			@RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
			@RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
			@RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
			@RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {
		return publicacionServicio.obtenerTodasLasPublicaciones(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
	}

	@GetMapping("/{id}")
	@CrossOrigin(origins = "*", allowedHeaders = "*", methods= {RequestMethod.GET})
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
	}

	//private KafkaTemplate<String, String> kafkaTemplate;

	
//	public PublicacionControlador(KafkaTemplate<String, String> kafkaTemplate) {
//		this.kafkaTemplate = kafkaTemplate;
//	}

	@PostMapping
	@CrossOrigin(origins = "*", methods= {RequestMethod.POST})
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestPart("file") MultipartFile file, @RequestParam("publicacionDTO") String publicacionDTOJson) {
		try {
			// Convertir el JSON de la publicación a objeto PublicacionDTO
			ObjectMapper objectMapper = new ObjectMapper();
			PublicacionDTO publicacionDTO = objectMapper.readValue(publicacionDTOJson, PublicacionDTO.class);

			// Verificar si se envió un archivo adjunto
			if (file != null && !file.isEmpty()) {
				// Realizar el guardado de la imagen
				String imageUrl = guardarImagen(file);
				publicacionDTO.setImg(imageUrl); // Establecer la URL de la imagen en el objeto PublicacionDTO
			}

			// Guardar la publicación
			PublicacionDTO resultado = publicacionServicio.crearPublicacion(publicacionDTO);
			return new ResponseEntity<>(resultado, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	private String guardarImagen(MultipartFile file) throws IOException {
		// Check if the file is empty
		if (file.isEmpty()) {
			throw new IllegalArgumentException("Empty file");
		}

		// Get the original file name
		String fileName = file.getOriginalFilename();

		// Get the file extension
		String fileExtension = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));

		// Generate a unique name for the file
		String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

		// Define the destination path to save the file
		String uploadDir = "../Frontend/public/images/victimas"; // Replace with the desired path

		// Create the destination directory if it doesn't exist
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// Save the file to the destination directory
		Path filePath = Paths.get(uploadDir, uniqueFileName);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		// Return the URL or path to the saved image
		return "/images/victimas/" + uniqueFileName;
	}


	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	@CrossOrigin(origins = "*", allowedHeaders = "*", methods= {RequestMethod.PUT})
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO,
			@PathVariable(name = "id") long id) {
		PublicacionDTO publicacionRespuesta = publicacionServicio.actualizarPublicacion(publicacionDTO, id);
		return new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@CrossOrigin(origins = "*", allowedHeaders = "*", methods= {RequestMethod.DELETE})
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id) {
		publicacionServicio.eliminarPublicacion(id);
		return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
	}

	@PostMapping("/upload-image")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
		try {
			// Check if the file is empty
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("Empty file");
			}

			// Get the original file name
			String fileName = file.getOriginalFilename();

			// Get the file extension
			String fileExtension = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));

			// Generate a unique name for the file
			String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

			// Define the destination path to save the file
			String uploadDir = "../Frontend/public/images/victimas"; // Replace with the desired path

			// Create the destination directory if it doesn't exist
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String absolutePath = dir.getAbsolutePath();
			System.out.println("Absolute path: " + absolutePath);

			// Save the file to the destination directory
			Path filePath = Paths.get(uploadDir, uniqueFileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			// Return the URL or path to the saved image
			String fileUrl = "../Frontend/public/images/victimas" + uniqueFileName; // Replace with the appropriate URL or path
			return ResponseEntity.ok(fileUrl);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving the image");
		}
	}



}
