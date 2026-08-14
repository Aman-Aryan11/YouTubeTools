package com.YouTubeTools.DTO;

import com.YouTubeTools.Service.YouTubeService;
import lombok.Data;

import java.util.List;

@Data
public class VideoApiResponse {
    public List<VideoItem> items;
}
