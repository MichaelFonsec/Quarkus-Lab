import org.junit.jupiter.api.AfterEach;

import com.google.inject.Inject;

import domain.CandidateRepository;
import domain.CandidateRepositoryTest;
import infrastructure.repositories.SQLCandidateRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;

@QuarkusTest
public class SQLCandidateRepositoryTest extends CandidateRepositoryTest {
     @Inject
     SQLCandidateRepository repository;

     @Inject
     EntityManager entityManager;

    @Override
    public CandidateRepository repository() {
        return repository;
    }
    
    @AfterEach
    @TestTransaction
    void tearDown(){
        entityManager.createNativeQuery("TRUNCATE TABLE candidates").executeUpdate();
    }
}
