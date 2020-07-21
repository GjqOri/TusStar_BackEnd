package com.mr.tusstar.controller;

import com.mr.tusstar.common.error.AccessResourceErrors;
import com.mr.tusstar.common.error.LoginErrors;
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
        return LoginErrors.NOAUTHC_ERROR;
    }

    @RequestMapping(path="/noAuthz")
    public Object noAuth() {
        return AccessResourceErrors.NOAUTHZ_ERROR;
    }

}
