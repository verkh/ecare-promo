package com.ecare.auth;

import com.ecare.plans.PlanService;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AuthController {

    @Inject
    private FacesContext facesContext;

    public boolean isLogged() {
        return false;
    }

    public void authenticate(String username) {
        if(isLogged()) return;
    }
}
