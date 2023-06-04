package com.example.book.domain;

import com.example.book.system.ValidImageURL;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class PostHandling {
    @JsonProperty
    private UUID id;

    @JsonProperty
    @NotEmpty
    private String title;

    @JsonProperty
    @NotEmpty
    private String content_text;

    @JsonProperty
    @ValidImageURL
    private String content_image;
}
