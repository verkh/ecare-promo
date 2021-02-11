package com.ecare.plans;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;

@Stateless
public class PlanService {

    private RestTemplate restTemplate = new RestTemplate();
    private PlanDeserializer planDeserializer = new PlanDeserializer();

    public List<Plan> getAll() {
        final String url = "http://192.168.31.107:8090/ecare/api/plans";
        ResponseEntity<Plan[]> response = restTemplate.getForEntity(url, Plan[].class);
        System.out.println(response.getBody());
        return Arrays.asList(response.getBody());
    }
}
