package infrastructure.repositories;

import java.util.List;

import org.glassfish.expressly.stream.Stream;
import org.hibernate.mapping.Map;

import domain.Election;
import domain.ElectionRepository;
import domain.annotations.Principal;
import infrastructure.repositories.entities.ElectionCandidate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@Principal
@ApplicationScoped
public class SQLElectionRepository implements ElectionRepository {

    private final EntityManager entityManager;
    
    public SQLElectionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void submit(Election election) {
        infrastructure.repositories.entities.Election entity = infrastructure.repositories.entities.Election.fromDomain(election);
        entityManager.merge(entity);

        election.votes()
        .entrySet()
        .stream()
        .map(entry -> ElectionCandidate.fromDomain(election,
         entry.getKey(),
         entry.getValue()))
        .forEach(entityManager::merge);
    }
    @Override
    public List<Election> findAll() {
     Stream<Object[]> stream = entityManager.createQuery("SELECT e FROM Election e")
     .getResultStream();
     return stream
     .map(row -> (infrastructure.repositories.entities.Election) row[0])
     .map(infrastructure.repositories.entities.Election::toDomain)
     .toList();

     Map<String, List<Object[]>> map = entityManager.createQuery("SELECT e FROM Election e")
     .getResultList()
     .stream()
     .collect(Collectors.groupingBy(row -> ((infrastructure.repositories.entities.Election) row[0]).getId()));

     return map.entrySet()
     .stream()
     .map(entry ->{
        Map.Entry<Candidate, Integer>[] candidates = entry.getValue()
        .stream()
        .map(row -> (infrastructure.repositories.entities.ElectionCandidate) row[0])
        .map(infrastructure.repositories.entities.ElectionCandidate::toDomain)
        .map(electionCandidate -> new Map.Entry<Candidate, Integer>(electionCandidate.getCandidate(), electionCandidate.getVotes())
     })

    }

    public void sync(Election election) {
        election.votes()
        .entrySet()
        .stream()
        .map(entry -> {
            ElectionCandidate.fromDomain(election,
         entry.getKey(),
         entry.getValue())
        })
        .forEach(entityManager::merge);
    }

}
