package at.htl.soccer.business;

import at.htl.soccer.model.Matches;
import at.htl.soccer.model.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class InitBean {

    private Client client;
    private WebTarget target;

    private List<Matches> matchesList = new ArrayList<>();

    @PersistenceContext
    private EntityManager em;

    private void init(@Observes @Initialized(ApplicationScoped.class) Object init){
        client = ClientBuilder.newClient();
        target = client.target("https://www.openligadb.de/api/getmatchdata/bl1/2019/8");

        Response response = target.request(MediaType.APPLICATION_JSON)
                .get();
        String st = response.readEntity(String.class);
        JsonValue jsonValue = Json.createReader(new StringReader(st)).readArray();
        JsonArray jsonArray = (jsonValue.getValueType() == JsonValue.ValueType.ARRAY) ?
                jsonValue.asJsonArray():
                Json.createArrayBuilder().add(jsonValue).build();

        matchesList = jsonArray.stream()
                .map(m -> createMatch(m))
                .collect(Collectors.toList());
        matchesList.forEach(matches -> System.out.println(matches.getId()));

       matchesList.forEach(em::merge);
    }

    private Matches createMatch(JsonValue jsonValue){
        return new Matches(
                jsonValue.asJsonObject().getJsonNumber("MatchID").longValue(),
                createTeam(jsonValue.asJsonObject().getJsonObject("Team1")),
                createTeam(jsonValue.asJsonObject().getJsonObject("Team2")),
                jsonValue.asJsonObject().getJsonArray("MatchResults").get(0).asJsonObject().getInt("PointsTeam1"),
                jsonValue.asJsonObject().getJsonArray("MatchResults").get(0).asJsonObject().getInt("PointsTeam2"),
                11
        );
    }
    private Team createTeam(JsonValue jsonValue){
        return new Team(
                jsonValue.asJsonObject().getJsonNumber("TeamId").longValue(),
                jsonValue.asJsonObject().getString("TeamName"),
                jsonValue.asJsonObject().getString("ShortName")
        );
    }
}
