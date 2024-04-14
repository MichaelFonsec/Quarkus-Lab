package infrastructure.resources;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


import org.jboss.resteasy.reactive.RestStreamElementType;

import api.dto.Election;
import infrastructure.rest.ElectionManagement;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class ResultResource {
    private final ElectionManagement electionManagement;

    public ResultResource(ElectionManagement electionManagement) {
        this.electionManagement = electionManagement;
    }


    @GET
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<List<Election>> result() {
        Multi.createFrom()
        .ticks()
        .every(Duration.of(5, ChronoUnit.SECONDS))
        .onItem()
        .transformToMultiAndMerge(n -> electionManagement.getElections().toMulti());
        return electionManagement.getElections().toMulti();

    }
}
