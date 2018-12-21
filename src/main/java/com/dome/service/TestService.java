package com.dome.service;

import com.dome.common.ServerResponse;
import com.dome.pojo.Test;

public interface TestService {
    ServerResponse<String> addTestDome(Test test);
}
