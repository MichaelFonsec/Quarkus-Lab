package infrastructure.repositories.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Array;
import java.util.List;

import org.instancio.Instancio;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import api.CandidateApi;
import api.dto.in.CreateCandidate;
import api.dto.out.Candidate;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class CandidateResourceTest {
    @InjectMock
    CandidateApi api;

    @Test
    void create(){
        var in = Instancio.create(CreateCandidate.class);

        given().contextType(MediaType.APPLICATION_JSON).body(in)
                .when().post()
                .then().statusCode(RestResponse.StatusCode.CREATED);

        verify(api).create(in);
        verifyNoMoreInteractions(api);
    }

    @Test
    void list(){
        var out = Instancio.stream(Candidate.class).limit(4).toList();

        when(api.list()).thenReturn((List<Candidate>) out);
        var response = given()
        .when().get()
        .then().statusCode(RestResponse.StatusCode.OK)
        

        verify(api).list();
        verifyNoMoreInteractions(api);

        assertEquals(out, Array.stream(response).toList());
        
    }
}
