package com.hbnu.pojo;

import java.util.HashSet;
import java.util.Set;

public class Role {
    private int rid;//角色id
    private String name;//角色名称
    private String description;//角色描述信息

    Set<Player> players = new HashSet<>();

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
