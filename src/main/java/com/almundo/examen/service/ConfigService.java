package com.almundo.examen.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.almundo.examen.dto.ConfigDTO;
import com.almundo.examen.model.Llamada;

public interface ConfigService {
	
	void run(List<ConfigDTO> config, int calls) throws InterruptedException, ExecutionException;
	
	public List<Llamada> getAllCall();

	void clearAllData();

}
