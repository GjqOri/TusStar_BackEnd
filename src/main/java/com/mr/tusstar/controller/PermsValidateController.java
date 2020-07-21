package com.mr.tusstar.controller;

import com.mr.tusstar.common.error.UserErrors;
import org.springframework.web.bind.annotation.*;

/**
 * @author Qi
 * @create 2020-07-13 20:50
 */

@RestController
@RequestMapping("/validate")
public class PermsValidateController {

    @RequestMapping(path="/noLogin")
    public Object noLogin() {
        return UserErrors.NOAUTHC_ERROR;
    }

    @RequestMapping(path="/noAuthz")
    public Object noAuth() {
        return UserErrors.NOAUTHZ_ERROR;
    }

}
