package infrastructure.repositories;

import java.util.List;

import org.jboss.logging.Logger;

import domain.Candidate;
import domain.Election;
import domain.ElectionRepository;
import io.quarkus.cache.CacheResult;
import io.quarkus.redis.datasource.RedisDataSource;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisElectionRepository implements ElectionRepository {

    @Override
    public List<Election> findAll() {
        LOGGER.info("Elections received from repository");
        return keyCommands.keys("elections:*")
                .stream()
                .map(id -> findById(id.replace("elections:", "")))
                .toList();
    }

    private static final Logger LOGGER = Logger.getLogger(RedisElectionRepository.class);

    public RedisElectionRepository(RedisDataSource redisDataSource) {
        redisDataSource.sortedSet(String.class, String.class);
        redisDataSource.key(String.class);
    }

    @Override
    @CacheResult(cacheName = "memorization")
    public Election findById(String id) {
    LOGGER.info("Election " + id + " received from repository");        

        sortedSetCommands.zrange("election:" + id, 0, -1, String.class);
        .stream().map(Candidate::new).toList();
        return new Election(id, candidates);

    }

    @Override
    public void vote(String electionId, Candidate candidate) {
       LOGGER.info("Election " + electionId + " received from repository");
       sortedSetCommands.zincrby("election:" + electionId, 1, candidate.id());
       
    }

}
