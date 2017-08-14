package com.almundo.examen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.almundo.examen.dto.ConfigDTO;
import com.almundo.examen.model.Llamada;
import com.almundo.examen.model.Personal;
import com.almundo.examen.repositories.LlamadaRepository;
import com.almundo.examen.repositories.PersonalRepository;
import com.almundo.examen.thread.Distpacher;
import com.almundo.examen.thread.TaskCallable;

@Transactional
@Service("configService")
@Scope("singleton")
public class ConfigServiceImpl implements ConfigService {

	private static final int MAX_THREAD = 50;
	
	@Autowired
	private PersonalRepository personalRepository;

	@Autowired
	private LlamadaRepository llamadaRepository;

	@Override
	public void run(List<ConfigDTO> config, int calls) throws InterruptedException, ExecutionException {
		List<Llamada> llamadaList = new ArrayList<>();

		//
		Map<Integer, List<Personal>> personMap = setOperadores(config);
		Distpacher.setPersonal(personMap);
		
		// Se guarda en la base de daros H2 la lista de personal registrado
		for (Integer key : personMap.keySet()) {
			personalRepository.save(personMap.get(key));
		}
		
		//Se pone un limite maximo de 50 threads para ejecutarse en simultaneo
		ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);
		List<Callable<Llamada>> lstOfCalls = new ArrayList<Callable<Llamada>>();

		// por cada llamada indicada en el frontEnd se dispara un thread
		// simulando asi la entrada de varias llamadas simultaneas
		for (int i = 1; i <= calls; i++) {
			lstOfCalls.add(new TaskCallable(new Llamada(Long.valueOf(i))));
		}

		// invoca todos los thread que se crearon por llamadas
		List<Future<Llamada>> tasks = executorService.invokeAll(lstOfCalls);

		System.out.println("\n" + tasks.size() + " Llamadas fueron procesadas.\n");
		
		// itera sobre el result de cada thread
		for (Future<Llamada> task : tasks) {
			llamadaList.add(new Llamada(task.get().getId(), task.get().getDuracion(), task.get().getOperador()));
		}
		
		executorService.shutdown();
		llamadaRepository.save(llamadaList);
		
	}

	// En configList contiene por cada objeto el tipo
	// de operador y la cantidad que sea desea crear, de esta manera si
	// se quieren agregar mas cargos a la cadena se puede hacer desde el frontEnd
	// sin tener que modificar codigo java
	private Map<Integer, List<Personal>> setOperadores(List<ConfigDTO> confgList) {
		Personal p;
		List<Personal> plist;
		Map<Integer, List<Personal>> personalMap = new TreeMap<>();

		// Se recorre la lista de configs en la cual se crea un operador
		// por la cantidad indicada y del tipo indicado, los cuales se agregan
		// a un tree map el cual se ordena automaticamente por key, y la key
		// de este va a ser el rol y en el valor va a tener una lista de personas con
		// ese rol
		for (ConfigDTO confg : confgList) {

			for (int i = 1; i <= confg.getCantidad(); i++) {
				//El rol de la persona se podria haber normalizo mas en la BBDD
				//y ponerlo en otra tabla pero decidi que este en la misma
				//tabla persona asi es mas rapido la carga
				p = new Personal(confg.getDescripcion() + " " + i, confg.getTipoOperador());

				if (personalMap.get(confg.getTipoOperador()) != null) {
					personalMap.get(confg.getTipoOperador()).add(p);
				} else {
					plist = new ArrayList<>();
					plist.add(p);
					personalMap.put(confg.getTipoOperador(), plist);
				}

			}

		}

		return personalMap;
	}
	
	public List<Llamada> getAllCall(){
		return llamadaRepository.findAll();
		
	}

	@Override
	public void clearAllData() {
		Distpacher.clear();
		llamadaRepository.deleteAll();
		llamadaRepository.flush();
		personalRepository.deleteAll();
		personalRepository.flush();
		
		
	}

}
