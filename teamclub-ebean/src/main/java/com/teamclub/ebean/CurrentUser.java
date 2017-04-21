package com.teamclub.ebean;

import com.avaje.ebean.config.CurrentUserProvider;
import org.springframework.stereotype.Component;

/**
 * Created by ilkkzm on 17-4-21.
 */
@Component
public class CurrentUser implements CurrentUserProvider {
    @Override
    public Object currentUser() {
        return "teamclub";
    }
}
