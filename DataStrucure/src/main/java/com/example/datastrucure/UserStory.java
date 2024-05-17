package com.example.datastrucure;

public class UserStory {
    String id;
    String level;
    public String acceptanceCriteria;
    public String story;

    public UserStory(String id, String level, String acceptanceCriteria, String story) {
        this.id = id;
        this.level = level;
        this.acceptanceCriteria = acceptanceCriteria;
        this.story = story;
    }
}
