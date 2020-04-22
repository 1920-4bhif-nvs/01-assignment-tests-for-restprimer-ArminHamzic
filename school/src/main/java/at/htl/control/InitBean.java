package at.htl.control;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class InitBean {

    private static final String FILE_NAME = "file.csv";

    @Inject
    SchoolRepository repo;

    private void init(@Observes StartupEvent event) {
        repo.readTemperatureFile(FILE_NAME);
    }
}
