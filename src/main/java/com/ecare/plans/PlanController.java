package com.ecare.plans;

import com.ecare.config.Configurations;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * The main controller which is responsible for viewing as all plans as a single plan
 */
@Named
@RequestScoped
public class PlanController {

    private static Logger logger = LogManager.getLogger(PlanController.class);

    @Inject
    private DataStorage storage; // Data storage. Keeps plans and timestamps

    @Inject
    private FacesContext facesContext;

    @EJB @Getter
    private Configurations configurations;

    /**
     * @return Return all loaded plans as a list from storage
     */
    public List<Plan> getPlans() {
        return storage.getPlans();
    }

    /**
     * Select specific plan and saves its id in case plans would be reloaded
     * @param plan - selected plan
     */
    public void pickPlan(Plan plan) {
        logger.trace("Got request to view plan");
        storage.setPickedId(new Long(plan.getId()));
    }

    /**
     * Resets selected plan which triggers to view all plans again
     */
    public void resetPlan() {
        logger.trace("Got request to view all plans. Reset current viewed plan.");
        storage.setPickedId(null);
    }

    /**
     * @return current selected plan
     */
    public Plan getPlan() {
        if(storage.getPickedId() == null) return null;
        return getPlans().stream().filter(pl -> pl.getId().equals(storage.getPickedId())).findAny().orElse(null);
    }

    /**
     * Checks whether all plans view is needed to be updated. The check is based on timestamps
     */
    public void needUpdate() {
        if(storage.getLastViewUpdate() == null || storage.getLastUpdate() > storage.getLastViewUpdate()) {
            logger.trace("All plans view needs to be updated. Reloading current page..");
            logger.debug(String.format("Update triggered with params: lastViewUpdate=%d lastStorageUpdate=%d",
                    storage.getLastViewUpdate(), storage.getLastViewUpdate()));
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            try {
                storage.setLastViewUpdate(new Long(storage.getLastUpdate()));
                ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
