package com.ifood.controllers;

import com.ifood.vos.GenericResponseVO;
import com.ifood.vos.WeatherResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/about")
public class About {

    public About() {}

    @RequestMapping(method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GenericResponseVO> about() {
        return new ResponseEntity<>(new GenericResponseVO(HttpStatus.OK.value(), "Servidor esta rodando"), HttpStatus.OK);
    }
}
