package com.hbnu.pojo;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private int pid;//玩家id
    private String name;//玩家姓名
    private String gender;//玩家性别

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    Set<Role> roles = new HashSet<>();



    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
