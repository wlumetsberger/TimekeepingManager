import at.wlumetsberger.viewManager.Person;
import at.wlumetsberger.viewManager.Race;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * Created by Wolfgang on 29.07.2015.
 */
public class WebServiceHelper {

    private static final String base_url = "http://10.0.0.80:8080/";
    private static WebServiceHelper instance;

    private Client client;
    private WebResource baseResource;
    private WebServiceHelper(){
        this.client = Client.create();
        this.baseResource = client.resource(base_url);

    }
    public static WebServiceHelper getInstance(){
        if(instance == null){
            instance = new WebServiceHelper();
        }
        return instance;
    }

    public Race getActiveRace(){
        ClientResponse response = baseResource.path("/race/getActive").get(ClientResponse.class);
        System.out.println(response.getStatusInfo());
        Race r = response.getEntity(Race.class);
        return r;
    }
    public Person getPerson(Object raceId, Object tagId){
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("raceId", ""+raceId);
        queryParams.add("tagId", ""+tagId);
        Person p = baseResource.path("/person/findByTagAndRaceId").queryParams(queryParams).get(Person.class);
        return p;
    }
    public int getPosition(Person p){
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("personId", ""+p.getId());
        String pos = baseResource.path("lap/getPositionByGroup").queryParams(queryParams).get(String.class);
        return Integer.parseInt(pos);
    }
    public int getPositionByGroup(Person p){
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("personId", ""+p.getId());
        String pos = baseResource.path("lap/getPosition").queryParams(queryParams).get(String.class);
        return Integer.parseInt(pos);
    }
    public int getLapCount(Person p){
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("personId", ""+p.getId());
        String pos = baseResource.path("lap/getLapCount").queryParams(queryParams).get(String.class);
        return Integer.parseInt(pos);
    }

    public List<Race> getRaces(){
        List<Race> races = baseResource.path("race/getAll").get(new GenericType<List<Race>>(){});
        return races;
    }

    public boolean addPerson(Person p){
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("name", p.getName());
        queryParams.add("club",p.getClub());
        queryParams.add("tagId", p.getTagId());
        queryParams.add("raceId", ""+p.getRace().getId());
        queryParams.add("number", ""+p.getNumber());
        queryParams.add("starterGroup", ""+p.getStarterGroup());

        Person pRet = baseResource.path("person/insert").queryParams(queryParams).get(Person.class);
        return pRet.getId() > 0;
    }
}
