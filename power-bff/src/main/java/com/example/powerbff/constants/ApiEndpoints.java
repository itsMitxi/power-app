package com.example.powerbff.constants;

import com.example.powerbff.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiEndpoints {

    public static String BASE_URL;
    @Autowired
    public ApiEndpoints(AppProperties appProperties) {
        BASE_URL = "http://localhost:" + appProperties.getPort() + "/" + appProperties.getContextPath();
    }

    public static final String VERSION_1 = "/api/v1";

    // Users
    public static final String USERS_BASE = VERSION_1 + "/users";
    public static final String USER_BY_ID = "/";
    public static final String USER_BY_USERNAME = "/username/";

}
