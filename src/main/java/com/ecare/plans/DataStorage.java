package com.ecare.plans;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton
public class DataStorage {

    @Getter @Setter
    private Long pickedId = null;

    @Getter
    private Long lastUpdate = null;

    @Getter @Setter
    private Long lastViewUpdate = null;

    @EJB
    private PlanService planService;

    @Getter @Setter
    private List<Plan> plans = null;

    @PostConstruct
    void init() {
        System.out.println("Init storage");
        loadPlans();
    }

    public void loadPlans() {
        System.out.println("Load plans storage");
        plans = planService.getAll();
        updateTimestamp();
    }

    private void updateTimestamp() {
        lastUpdate = System.currentTimeMillis();
        System.out.println("Updated TS " + lastUpdate);
    }
}
