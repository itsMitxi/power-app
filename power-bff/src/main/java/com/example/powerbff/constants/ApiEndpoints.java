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
    public static final String VERSION = VERSION_1;

    // Users
    public static final String USERS_BASE = VERSION + "/users";
    public static final String USER_BY_ID = "/";
    public static final String USER_BY_USERNAME = "/username/";

    // Exercises
    public static final String EXERCISES_BASE = VERSION + "/exercises";
    public static final String EXERCISE_BY_ID = "/";
    public static final String EXERCISE_BY_NAME = "/name/";

    // Exercise types
    public static final String EXERCISE_TYPES_BASE = VERSION + "/exercise-types";
    public static final String EXERCISE_TYPE_BY_ID = "/";
    public static final String EXERCISE_TYPE_BY_NAME = "/name/";

    // Muscle groups
    public static final String MUSCLE_GROUPS_BASE = VERSION + "/muscle-groups";
    public static final String MUSCLE_GROUP_BY_ID = "/";
    public static final String MUSCLE_GROUP_BY_NAME = "/name/";

    // Muscles
    public static final String MUSCLES_BASE = VERSION + "/muscles";
    public static final String MUSCLE_BY_ID = "/";
    public static final String MUSCLE_BY_NAME = "/name/";
    public static final String MUSCLES_BY_MUSCLE_GROUP = "/group/";

    // Workouts
    public static final String WORKOUTS_BASE = VERSION + "/workouts";
    public static final String WORKOUT_BY_ID = "/";
    public static final String WORKOUTS_BY_USER = "/user/";
    public static final String WORKOUTS_BY_DATE = "/date/";
    public static final String WORKOUTS_BETWEEN_DATES = "/dates";

    // Set types
    public static final String SET_TYPES_BASE = VERSION + "/set-types";
    public static final String SET_TYPE_BY_ID = "/";
    public static final String SET_TYPE_BY_NAME = "/name/";

    // Sets
    public static final String SETS_BASE = VERSION + "/sets";
    public static final String SET_BY_ID = "/";
    public static final String SETS_BY_WORKOUT = "/workout/";

    // Dropsets
    public static final String DROPSETS_BASE = VERSION + "/dropsets";
    public static final String DROPSET_BY_ID = "/";
    public static final String DROPSET_BY_SET = "/set/";

}