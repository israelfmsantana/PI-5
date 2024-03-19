package com.israelsantana.demo.models.projection;

import com.israelsantana.demo.models.Action;
import com.israelsantana.demo.models.User;

public interface PortfolioProjection {
    
    public Long getId();

    public User getUser();

    public Action getAction();
}
