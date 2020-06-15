package it.uniroma3.siw.progetto.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.repository.TaskRepository;

@Service
public class TaskService 
{
	@Autowired
	protected TaskRepository taskRepository;
	
	/* Salva un task nel repository */
	@Transactional
	public void save(Task task)
	{
		this.taskRepository.save(task);
	}
	
	/* Aggiunge un task al progetto */
	@Transactional
	public void addTask(Progetto progetto, Task task)
	{
		task.setProgetto(progetto);
		this.save(task);
	}
}
