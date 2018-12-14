package com.dome.service.Imp;

import com.dome.pojo.User;
import com.dome.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: dome2
 * @description:
 * @author: Mr.Zhou
 * @create: 2018-12-14 09:47
 **/

@Service
public class UserServiceImp implements UserService {


    @Transactional // 配置事务的使用
    public List<User> findALL(){
        return null;
    }
}
