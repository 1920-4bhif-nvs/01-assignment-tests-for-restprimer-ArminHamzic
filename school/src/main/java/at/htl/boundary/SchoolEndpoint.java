package at.htl.boundary;

import at.htl.control.SchoolRepository;
import at.htl.entity.School;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Transactional
@Path("/school")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SchoolEndpoint {

    @Inject
    SchoolRepository schoolRepository;

    @GET
    @Path("/all")
    public List<School> getAll() {
        return schoolRepository.findAll().list();
    }

    @GET
    @Path("/{id}")
    public School findById(@PathParam("id") Long id) {
        School s = schoolRepository.findById(id);
        if(s == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return s;
    }

    @POST
    public Response addSchool(School school) {
        School s = new School();
        s.CopyProperties(school);
        schoolRepository.persist(s);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSchool(@PathParam("id") long id){
        School s = schoolRepository.findById(id);
        if(s == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        schoolRepository.delete(s);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateSchool(@PathParam("id") long id, School school) {
        School s = schoolRepository.findById(id);
        if(s == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        s.CopyProperties(school);
        return Response.ok().build();
    }
}
