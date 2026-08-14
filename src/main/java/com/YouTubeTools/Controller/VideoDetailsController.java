package com.YouTubeTools.Controller;

import com.YouTubeTools.Model.Video;
import com.YouTubeTools.Service.ThumbnailService;
import com.YouTubeTools.Service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VideoDetailsController {

    @Autowired
    private ThumbnailService thumbnailService;

    @Autowired
    private YouTubeService youTubeService;

    @PostMapping("/video-details")
    public String fetchDetails(@RequestParam("videoUrlOrId") String videoUrlOrId, Model model) {
        String videoId = thumbnailService.extractVideoId(videoUrlOrId);

        if (videoId == null) {
            model.addAttribute("error", "Invalid YouTube URL or ID");
            model.addAttribute("videoUrlOrId", videoUrlOrId);
            return "video-details";
        }

        try {
            Video video = youTubeService.getVideoDetailsById(videoId);
            if (video == null) {
                model.addAttribute("error", "Could not fetch video details. Please check the video ID.");
                model.addAttribute("videoUrlOrId", videoUrlOrId);
                return "video-details";
            }
            // Fallback thumbnail from YouTube CDN if API didn't return one
            if (video.getThumbnailUrl() == null || video.getThumbnailUrl().isEmpty()) {
                video.setThumbnailUrl("https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg");
            }
            model.addAttribute("videoDetails", video);
            model.addAttribute("videoUrlOrId", videoUrlOrId);
            return "video-details";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch video details: " + e.getMessage());
            model.addAttribute("videoUrlOrId", videoUrlOrId);
            return "video-details";
        }
    }
}
