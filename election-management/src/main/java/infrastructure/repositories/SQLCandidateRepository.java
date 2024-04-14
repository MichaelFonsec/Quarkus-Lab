package infrastructure.repositories;

import java.util.List;
import java.util.stream.Stream;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SQLCandidateRepository implements CandidateRepository {
 
    private final EntityManager entityManager;
    
    public SQLCandidateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(List<Candidate> candidates) {
         candidates.stream()
         .map(infrastructure.repositories.entities.Candidate.fromDomain)
         .forEach(entity -> {
            entityManager.merge(entity);
         });
    }


    // @Override
    // public List<Candidate> findAll() {
    //     return List.of();
    // }

    // @Override
    // public Optional<Candidate> findById(String id) {
    //   return Optional.empty();
    // }

    @Override
    public List<Candidate> find(CandidateQuery query) {
   
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(infrastructure.repositories.entities.Candidate.class);
        var root = cq.from(infrastructure.repositories.entities.Candidate.class);

        var where = query.ids().map(id-> cb.in(root.get("id")).value(id)).get();

        cq.select(root).where(conditions(query, null, null));

        return entityManager.createQuery(cq)
        .getResultStream()
        .map(infrastructure.repositories.entities.Candidate::toDomain)
        .toList();
    }    

    private Predicate[] conditions(CandidateQuery query,
                  CriteriaBuilder cb,
                  Root<infrastructure.repositories.entities.Candidate> root)
                  return Stream.of(query.ids().map(id -> cb.in(root.get("id")).value()),
                  query.name().map(name -> cb.or(cb.like(cb.lower(root.get("givenName")), "%" + name.toLowerCase() + "%"),
                  cb.like(cb.lower(root.get("familyName")), "%" + name.toLowerCase() + "%")))).toArray(Predicate[]::new);
                  .flatMap(Optional::stream)
                  .toArray(Predicate[]::new);
}