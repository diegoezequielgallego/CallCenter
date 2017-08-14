package com.almundo.examen.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almundo.examen.dto.ConfigDTO;
import com.almundo.examen.service.ConfigService;
import com.almundo.examen.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	ConfigService configService;
	
	
	//-------------------Run New Config-------------------------------------------
	
	@RequestMapping(value = "/run/{calls}", method = RequestMethod.POST)
	public ResponseEntity<?> run(@RequestBody List<ConfigDTO> config, @PathVariable("calls") int calls) {
		logger.info("run new : {}", config);
		try {
			configService.clearAllData();
			configService.run(config, calls);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(new CustomErrorType("fallo al ejecutar las llamadas"));
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	//-------------------Get Log-------------------------------------------
	
	@RequestMapping(value = "/getLog/", method = RequestMethod.POST)
	public ResponseEntity<?> getLog() {
		try {
			return ResponseEntity.ok(configService.getAllCall());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.badRequest().body(new CustomErrorType("fallo al ejecutar las llamadas"));
		}
	}
	
	
	

}