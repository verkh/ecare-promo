package com.ecare.plans;

import com.ecare.network.Listener;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * Storage of viewed data
 */
@Startup
@Singleton
public class DataStorage {

    private static Logger logger = LogManager.getLogger(DataStorage.class);

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

    /**
     * Initializes storage and loads all plans
     */
    @PostConstruct
    void init() {
        logger.info("Initializing data storage...");
        loadPlans();
    }

    /**
     * Load all plans
     */
    public void loadPlans() {
        logger.trace("Loading plans from server...");
        plans = planService.getAll();
        updateTimestamp();
    }

    /**
     * Updates timestamp of latest load of plans
     */
    private void updateTimestamp() {
        lastUpdate = System.currentTimeMillis();
        logger.trace("Time of update: " + lastUpdate);
    }
}
