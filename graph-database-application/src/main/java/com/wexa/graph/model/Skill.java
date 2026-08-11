package com.wexa.graph.model;

public class Skill {

    private String skillId;
    private String name;

    public Skill(String skillId, String name) {
        this.skillId = skillId;
        this.name = name;
    }

    public String getSkillId() {
        return skillId;
    }

    public String getName() {
        return name;
    }
}