package fr.maximedavid.todos;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TodoService {

    @ConfigProperty(name = "mongo.database")
    String database;

    @ConfigProperty(name = "mongo.collection")
    String collection;

    private MongoClient mongoClient;

    public TodoService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void create(Todo todo) {
        Document document = new Document()
        .append("title", todo.getTitle())
        .append("description", todo.getDescription())
        .append("completed", todo.isCompleted());
        getCollection().insertOne(document);
    }

    public List<Todo> list(){
        List<Todo> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Todo todo = new Todo();
                todo.setTitle(document.getString("title"));
                todo.setDescription(document.getString("description"));
                todo.setCompleted(document.getBoolean("completed"));
                list.add(todo);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase(database).getCollection(collection);
    }

}
