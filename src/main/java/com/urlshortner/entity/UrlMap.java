package com.urlshortner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String shortUrl;
    private String longUrl;


}
