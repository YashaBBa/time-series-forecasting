package com.example.client.model;

import java.util.HashMap;
import java.util.Map;

public class Roles {
    public static Map<String, Integer> map = new HashMap<>();

    static {
        map.put("ADMIN", 1);
        map.put("USER", 2);
    }

}
