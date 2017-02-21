package uk.ac.ebi.fg.biostudies.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by awais on 16/02/2017.
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode error = mapper.createObjectNode();
        error.put("errorMessage", ex.getMessage());
        ex.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error.toString())
                .type(MediaType.APPLICATION_JSON)
                .build();

    }
}