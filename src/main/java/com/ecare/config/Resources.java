package com.ecare.config;

import com.ecare.plans.PlanService;
import org.springframework.context.annotation.Bean;

import javax.ejb.Stateful;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;


@Stateful
@RequestScoped
public class Resources {

    @Produces
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
