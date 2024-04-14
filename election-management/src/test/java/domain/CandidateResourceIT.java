package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.instancio.Instancio;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import infrastructure.resources.CandidateResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.specification.RequestSpecification;
import jakarta.ws.rs.core.MediaType;

@QuarkusIntegrationTest
@TestHTTPEndpoint(CandidateResource.class)
public class CandidateResourceIT {
    
    @Test
    void create(){
        var in = Instancio.create(CreateCandidate.class);
        given().contextType(MediaType.APPLICATION_JSON).body(in)
            .when().post()
            .then().statusCode(RestResponse.StatusCode.CREATED);
    }

    @Test
    void update(){
        var id = UUID.randomUUID().toString();
        var in = Instancio.create(UpdateCandidate.class);
       
        var update = Instancio.of(UpdateCandidate.class)
        .set(field("photo"), in.photo())
        .set(field("givenName"), "Michael")
        .set(field("familyName"), "Polani")
        .set(field("email"), in.email())
        .set(field("phone"), in.phone())
        .set(field("jobTitle"), in.jobTitle())
        .create();
    
        var response1 = given().contentType(MediaType.APPLICATION_JSON).body(in)            .when().put("/" + id)
            .then().statusCode(RestResponse.StatusCode.OK).extract().response().asString()
        var response2 = given().contentType(MediaType.APPLICATION_JSON).body(in)
            .when().put("/" + id)
            .then().statusCode(RestResponse.StatusCode.OK).extract().response().asString()

            assertEquals(response1.id(), id);
            assertEquals(response2.id(), id);
            assertEquals(response1.fullName(), response2.fullName());
            assertEquals(response1.email(), response2.email());
            assertEquals(response1.phone(), response2.phone());
            assertEquals(response1.jobTitle(), response2.jobTitle());
             
    }
    
    }

}
