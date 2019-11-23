package com.example.userserver.model;

import java.util.List;

public class Caidan {
    private String id;

    private String name;

    private String parentId;

    private List<Caidan> children;

    public List<Caidan> getChildren() {
        return children;
    }

    public void setChildren(List<Caidan> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }
}