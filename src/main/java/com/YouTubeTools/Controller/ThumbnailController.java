package com.YouTubeTools.Controller;

import com.YouTubeTools.Service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThumbnailController {

    @Autowired
    ThumbnailService service;

    @GetMapping("/thumbnail")
    public String getThumbnails() {
        return "thumbnails";
    }

    @PostMapping("/get-thumbnail")
    public String showThumbnails(@RequestParam ("videoUrlOrId")  String videoUrlOrId, Model model) {
        String videoId = service.extractVideoId(videoUrlOrId);
        if(videoId == null) {
            model.addAttribute("error", "Invalid YouTube URL");
            return "thumbnails";
        }
        String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";
        model.addAttribute("thumbnailUrl", thumbnailUrl);
        model.addAttribute("videoId", videoId);
        return "thumbnails";
    }
}
