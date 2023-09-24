package com.project.ebankingbackend.services;

import java.util.Map;

public interface AuthService {
    Map<String,String> authenticate(String username, String password);
}
