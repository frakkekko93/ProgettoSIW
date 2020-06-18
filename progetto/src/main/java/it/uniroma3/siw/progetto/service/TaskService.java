package it.uniroma3.siw.progetto.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Tag;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.repository.TaskRepository;

@Service
public class TaskService 
{
	@Autowired
	protected TaskRepository taskRepository;
	
	@Autowired
	protected ProgettoService progettoService;

	/* Prende tutti i task del database */
	public List<Task> getAllTasks()
	{
		return (List<Task>)this.taskRepository.findAll();
	}
	
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
		if(!progetto.getTasks().contains(task))
		{
			task.setProgetto(progetto);
			progetto.getTasks().add(task);
			this.taskRepository.save(task);
			this.progettoService.save(progetto);
		}
	}

	/* Prende un task in base al suo id */
	public Task getTask(Long id)
	{
		Optional<Task> result = this.taskRepository.findById(id);

		return result.orElse(null);
	}

	/* Elimina un task dal db */
	@Transactional
	public void delete(Task task)
	{
		this.taskRepository.delete(task);
	}

	/* Ritorna i task di un progetto di cui un membro è responabile */
	public List<Task> assignedTasks(Progetto progetto, Utente membro)
	{
		List<Task> taskAssegnati = this.taskRepository.findByResponsabile(membro);
		List<Task> returnList = new ArrayList<>();
		Iterator<Task> it = taskAssegnati.iterator();

		Task t = null;
		while(it.hasNext())
		{
			t = it.next();

			if(t.getProgetto().equals(progetto))
			{
				returnList.add(t);
			}
		}

		return returnList;
	}
	
	/* Elimina un tag dal task*/
	@Transactional
	public void deleteTag(Tag tag, Task task)
	{
		task.getTags().remove(tag);
		this.taskRepository.save(task);
	}
}
