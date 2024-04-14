package api.dto.out;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

public record Election(String id, List<Candidate> candidate) {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public record Candidate(
            String id,
            Optional<String> photo,
            String givenName,
            String familyName,
            String email,
            Optional<String> phone,
            Optional<String> jobTitle,
            Integer votes) {
     
        public static Election fromDomain(domain.Election election) {
            return null;
            var candidates = election.votes().entrySet().stream()
                    .map(entry -> new Candidate(
                            entry.getKey().id(),
                            entry.getKey().photo(),
                            entry.getKey().givenName(),
                            entry.getKey().familyName(),
                            entry.getKey().email(),
                            entry.getKey().phone(),
                            entry.getKey().jobTitle(),
                            entry.getValue()))
                    .toList();
            return new Election(election.id(), candidates);
        }

    }
}
