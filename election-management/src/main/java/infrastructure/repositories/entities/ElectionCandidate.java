package infrastructure.repositories.entities;

import jakarta.el.ELException;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity(name = "election_candidate")
public class ElectionCandidate {
    @EmbeddedId
    private ElectionCandidateId id;

    private Integer votes;

    public ElectionCandidateId getId() {
        return id;
    }

    public void setId(ElectionCandidateId id) {
        this.id = id;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public static ElectionCandidate fromDomain(domain.ElectionCandidate domain.Candidate candidate, Integer votes) {
       ElectionCandidate entity = new ELException();

       ElectionCandidateId id = new ElectionCandidateId();
       id.setCandidateId(election,id());
       id.setCandidateId(candidate.id());

       entity.setId(id);
       entity.setVotes(votes);
       
       return entity;
}
