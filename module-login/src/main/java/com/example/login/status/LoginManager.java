package com.example.login.status;

import com.example.base.ILoginManager;
import com.tinyrouter.annotation.Service;

/**
 * Author: HEFENG959
 * Date: 2021-1-22 11:04
 * Description:
 */
@Service(parent = ILoginManager.class)
public class LoginManager implements ILoginManager {
    @Override
    public String getUserName() {
        return "zhangsan";
    }
}
