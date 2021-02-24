package fr.maximedavid.todos;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.apache.groovy.util.Maps;
import org.testcontainers.containers.GenericContainer;

import java.util.Map;

public class DatabaseResource implements QuarkusTestResourceLifecycleManager {

    private final String IMAGE_NAME = "mongo:4.4";
    private final Integer TEST_PORT = 27018;
    private final GenericContainer container = new GenericContainer(IMAGE_NAME)
            .withExposedPorts(TEST_PORT)
            .withCommand("--port " + TEST_PORT);

    @Override
    public Map<String, String> start() {
        container.start();
        return Maps.of("quarkus.mongodb.connection-string", "mongodb://"
                + container.getContainerIpAddress() + ":" + container.getFirstMappedPort());
    }


    @Override
    public void stop() {
        if(null != container) {
            container.stop();
        }
    }
}