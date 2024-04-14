package domain;

import java.util.List;

import domain.annotations.Principal;
import io.quarkus.arc.runtime.BeanContainer.Instance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;

@ApplicationScoped
public class ElectionService {
 private final CandidateService candidateService;
 private final Instance<ElectionRepository> repositories;
private final ElectionRepository repository;


public ElectionService(CandidateService candidateService, @Any Instance<ElectionRepository> repositories, @Principal ElectionRepository repository) {
    this.candidateService = candidateService;
    this.repositories = repositories;
    this.repository = repository;
}

public List<Election> findAll() {
return repository.findAll();
}

public void submit(){
    Election.create(candidateService.findAll());
        repositories.forEach(repositories -> repository.submit(election));
}
    
}
