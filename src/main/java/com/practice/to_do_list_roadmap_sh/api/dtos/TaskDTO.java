package com.practice.to_do_list_roadmap_sh.api.dtos;

public class TaskDTO {

    private String title;
    private String description;
    private String owner;

    
    public TaskDTO() {
    }
    
    public TaskDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
