package com.jotte.common.web;

import com.jotte.common.vo.JsonMsgVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController {

    @RequestMapping(value = "/error/{code}", method = RequestMethod.GET)
    public String errorPageGet(@PathVariable("code") String code) {
        return "error/" + code;
    }

    @RequestMapping(value = "/error/{code}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String errorPagePost(@PathVariable("code") String code) {
        return new JsonMsgVO("error", code).toString();
    }

    @RequestMapping(value = "/error/test/403")
    public String errorPageTest403() {
        return "error/403";
    }

    @RequestMapping(value = "/error/test/404")
    public String errorPageTest404() {
        return "not_existing_view";
    }

    @RequestMapping(value = "/error/test/500")
    public String errorPageTest500() {
        throw new RuntimeException("Test exception");
    }


}
