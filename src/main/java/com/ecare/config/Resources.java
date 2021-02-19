package com.ecare.config;

import com.ecare.plans.PlanService;
import org.springframework.context.annotation.Bean;

import javax.ejb.Stateful;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Defines resources of the project
 */
@Stateful
@RequestScoped
public class Resources {

    /**
     * @return FacesContext for the application
     */
    @Produces
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
