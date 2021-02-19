package com.ecare.plans;

import com.ecare.config.Configurations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;

/**
 * Class handles loading of plans from server
 */
@Stateless
public class PlanService {

    private RestTemplate restTemplate = new RestTemplate();
    private PlanDeserializer planDeserializer = new PlanDeserializer();

    @EJB
    private Configurations configurations;

    /**
     * Loads all plans
     * @return list of all loaded plans
     */
    public List<Plan> getAll() {
        final String url = configurations.getServerAddress()+"/api/plans";
        ResponseEntity<Plan[]> response = restTemplate.getForEntity(url, Plan[].class);
        System.out.println(response.getBody());
        return Arrays.asList(response.getBody());
    }
}
