package com.almundo.examen.thread;

import java.util.concurrent.Callable;

import com.almundo.examen.model.Llamada;

public class TaskCallable implements Callable<Llamada> {

	private Llamada llamada;

	public TaskCallable(Llamada llamda) {
		this.llamada = llamda;
		
	}

	@Override
	public Llamada call() throws Exception {
		Distpacher.proceseCall(llamada);
		return llamada;
	}
}
