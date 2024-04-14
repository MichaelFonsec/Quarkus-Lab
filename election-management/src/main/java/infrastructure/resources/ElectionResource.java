package infrastructure.resources;

import java.util.List;

import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import domain.ElectionApi;
import infrastructure.repositories.entities.Election;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/elections")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ElectionResource {
    private final ElectionApi api;

    public ElectionResource(ElectionApi api) {
        this.api = api;
    }
    
    @POST
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    @Transactional
    public void submit(){
        api.submit();
    }

    @GET
    public List<Election> list(){
        return api.findAll();
    }
}
