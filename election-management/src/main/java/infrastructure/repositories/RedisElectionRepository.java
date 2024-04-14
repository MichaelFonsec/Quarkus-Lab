package infrastructure.repositories;

import java.util.List;
import java.util.Map;

import domain.Election;
import domain.ElectionRepository;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.sortedset.ScoredValue;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisElectionRepository implements ElectionRepository {

private final SortedSetCommands<String, String> commands;

    public RedisElectionRepository(RedisDataSource redisDataSource){
       commands =  redisDataSource.sortedSet(String.class, String.class);
       redisDataSource.pubsub(String.class);
    }

    @Override
    public void submit(Election election) {
       
       Map<String, Double> rank = election.votes()
       .entrySet()
       .stream()
       .collect(Collectorrs.toMap(entry-> entry.getKey().id(),
        entry-> entry.getValue().doubleValue()))

        commands.zadd("election:" + election.id(), rank);
        pubSubCommands.publish("elections:" + election.id());
    }

    public Election sync(Election election) {
        List<ScoredValue<String>> values = commands.zrangeWithScores("election:" + election.id(), 0, -1);
        Map<String, Integer> votes = values
        .stream()
        .collect(Collectorrs.toMap(scoredValue-> scoredValue.getValue(),
         scoredValue-> scoredValue.getScore().intValue()));


         var map = scoredValues.stream().collect(Collectors.toMap(ScoredValue::getValue, ScoredValue ->
         Candidate candidate = election.votes()
         .keySet()
         .stream()
         .filter(candidate -> candidate.id().equals(ScoredValue.getValue()))
         .findFirst()
         .orElseThrow()
         .toDomain()        
         return Map.entry(candidate, (int) scoredValue.score());
         ))
         .toArray(Map.Entry[]::new);

         return new Election(election.id(), Map.ofEntries(map));

    }

    @Override
    public List<Election> findAll() {
        throw new UnsupportedOperationException();
    }

}
