package at.htl.control;

import at.htl.entity.School;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
@Transactional
public class SchoolRepository implements PanacheRepository<School> {

    @PersistenceContext
    private EntityManager em;

    public void readTemperatureFile(final String fileName) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        br.lines().skip(1)
                .forEach(line -> parseCsvLine(line));
    }

    List<School> parseCsvLine(String line) {
        List<School> schools = new LinkedList<>();
        String[] lines = line.split(";");
        for (int i = 1; i < lines.length; i++) {
            School s = new School(lines[0], Integer.parseInt(lines[1]));
            schools.add(s);
        }
        for (School s: schools) {
            em.persist(s);
        }
        return schools;
    }
}
