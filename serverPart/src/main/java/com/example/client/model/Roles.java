package com.example.client.model;

import java.util.HashMap;
import java.util.Map;

public class Roles {
    public static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(1, "ADMIN");
        map.put(2, "USER");
    }

}
