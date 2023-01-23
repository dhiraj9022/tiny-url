package com.urlshortner.controller;

import com.urlshortner.dto.RespMsg;
import com.urlshortner.dto.UrlCreateDto;
import com.urlshortner.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/url-shortner")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/home")
    public ResponseEntity<RespMsg> home(){
        return ResponseEntity.ok(new RespMsg("Welcome Url Shortner Application"));
    }

    @PostMapping
    public ResponseEntity<RespMsg> createShortUrl(@RequestBody UrlCreateDto urlCreateDto, HttpServletRequest httpServletRequest){
        if(urlCreateDto.getUrl() == ""){
            return ResponseEntity.badRequest().body(new RespMsg("invalid url"));
        }
        System.out.println(httpServletRequest.getRequestURI());
        return ResponseEntity.ok(new RespMsg(httpServletRequest.getRequestURL() + "/" + urlService.saveUrl(urlCreateDto)));
    }

    @GetMapping
    public ResponseEntity<HashMap<String, String>> getRoutes(){
        return ResponseEntity.ok(urlService.getAllUrls());
    }

    @GetMapping("/{url}")
    public void redirect(@PathVariable String url, HttpServletResponse httpServletResponse){
        if(!urlService.urlMap.containsKey(url)){
            httpServletResponse.setStatus(404);
        }else{
            httpServletResponse.setHeader("Location", urlService.urlMap.get(url));
            httpServletResponse.setStatus(302);
        }
    }
}
