package com.YouTubeTools.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SearchApiResponse {
    public List<SearchItem> items;
}
