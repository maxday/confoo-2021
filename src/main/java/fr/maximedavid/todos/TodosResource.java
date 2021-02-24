package fr.maximedavid.todos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/todos")
public class TodosResource {

    @Inject
    TodoService todoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Todo> list() {
        return todoService.list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Todo> create(Todo todo) {
        todoService.create(todo);
        return list();
    }
}