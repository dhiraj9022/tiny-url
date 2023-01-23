package com.urlshortner.service;

import com.urlshortner.dto.UrlCreateDto;
import com.urlshortner.entity.UrlMap;
import com.urlshortner.repository.UrlRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class UrlService {

    public HashMap<String, String> urlMap = new HashMap<>();
    @Autowired
    private UrlRepository urlRepo;

    @PostConstruct
    private void init() {
        urlRepo.findAll().forEach(u -> {
            urlMap.get(u.getLongUrl());
        });
    }

    public String saveUrl(UrlCreateDto urlCreateDto){

         String shortUrl = UUID.randomUUID().toString().subSequence(0,7).toString();
         urlMap.put(shortUrl, urlCreateDto.getUrl());
         saveUrlDb(urlCreateDto, shortUrl);

        return shortUrl;
    }

    private void saveUrlDb(UrlCreateDto urlCreateDto, String shortUrl){

        UrlMap urlMap =  new UrlMap();
        urlMap.setShortUrl(shortUrl);
        urlMap.setLongUrl(urlCreateDto.getUrl());
        urlRepo.save(urlMap);
    }

    public HashMap<String, String> getAllUrls(){
        return urlMap;
    }
}
