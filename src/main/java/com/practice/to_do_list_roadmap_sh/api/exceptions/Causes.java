package com.practice.to_do_list_roadmap_sh.api.exceptions;

public enum Causes {
    USER_NOT_FOUND("User has not been found in the database"),
    USER_ALREADY_EXISTS("User already exists in the database"),
    TASK_NOT_FOUND("Task has not been found in the database"),
    THE_FOLLOWING_FIELDS_ARE_EMPTY("The following fields are empty: "),;

    public final String label;

    Causes(String label) {
        this.label = label;
    }

}
