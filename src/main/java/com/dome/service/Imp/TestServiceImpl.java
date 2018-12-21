package com.dome.service.Imp;

import com.dome.common.ServerResponse;
import com.dome.dao.TestMapper;
import com.dome.pojo.Test;
import com.dome.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: dome2
 * @description:
 * @author: Mr.Zhou
 * @create: 2018-12-19 21:51
 **/

@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestMapper testMapper;

    @Override
    public ServerResponse<String> addTestDome(Test test) {
        int n = testMapper.insert(test);
        if (n == 0){
            ServerResponse.createByErrorMessage("失败！");
        }
        return ServerResponse.createBySuccessMessage("成功！");
    }
}
