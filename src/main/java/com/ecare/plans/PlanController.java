package com.ecare.plans;

import lombok.Getter;
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

@Named
@RequestScoped
public class PlanController {

    @Inject
    private DataStorage storage;

    @Inject
    private FacesContext facesContext;

    public List<Plan> getPlans() {
        return storage.getPlans();
    }

    public void pickPlan(Plan plan) {
        storage.setPickedId(new Long(plan.getId()));
    }

    public void resetPlan() {
        storage.setPickedId(null);
    }

    public Plan getPlan() {
        if(storage.getPickedId() == null) return null;
        return getPlans().stream().filter(pl -> pl.getId().equals(storage.getPickedId())).findAny().orElse(null);
    }

    public void needUpdate() {
        if(storage.getLastViewUpdate() == null || storage.getLastUpdate() > storage.getLastViewUpdate()) {
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
