package infrastructure.schedulers;


import domain.Election;
import domain.annotations.Principal;
import infrastructure.repositories.RedisElectionRepository;
import infrastructure.repositories.SQLElectionRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Sync {
   private final SQLElectionRepository sqlRepository;
    private final RedisElectionRepository redisElectionRepository;


    public Sync(@Principal SQLElectionRepository sqlElectionRepository, RedisElectionRepository redisElectionRepository) {
        this.sqlRepository = sqlElectionRepository;
        this.redisElectionRepository = redisElectionRepository;
    }


    @Scheduled(cron ="*/10 * * * * *")
    void sync(){
      sqlRepository.findAll().forEach(election ->{
            Election updated =redisElectionRepository.sync(election);
            sqlRepository.sync(updated);
         });

    }
    
}
