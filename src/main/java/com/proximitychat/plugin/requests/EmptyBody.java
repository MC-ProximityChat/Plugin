package com.proximitychat.plugin.requests;

public class EmptyBody implements RequestBody, ResponseBody {
    @Override
    public String toJson() {
        return "";
    }
}
