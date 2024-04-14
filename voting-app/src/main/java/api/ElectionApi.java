package api;

import java.util.List;

import domain.Election;
import domain.ElectionService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ElectionApi {
   private final ElectionService service;

   public ElectionApi(ElectionService service) {
       this.service = service;  
   }

   public List<Election> findAll() {
    List<domain.Election> elections = service.findAll();
       return elections.stream().map(Election::fromDomain).toList();
   }

public void vote(String electionId, String candidateId) {
    service.vote(electionId, candidateId);
}
}
