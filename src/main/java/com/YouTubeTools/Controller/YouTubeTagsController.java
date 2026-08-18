package com.YouTubeTools.Controller;

import com.YouTubeTools.Model.SearchVideo;
import com.YouTubeTools.Model.Video;
import com.YouTubeTools.Service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/youtube")
public class YouTubeTagsController {

    @Autowired
    private YouTubeService youTubeService;

    @Value("${youtube.api.key}")
    private String apiKey;

    private boolean isApiKeyConfigured() {
        return apiKey != null &&  !apiKey.isEmpty();
    }

    @PostMapping("/search")
    public String videoTags(@RequestParam ("videoTitle") String videoTitle, Model model) {

        if (!isApiKeyConfigured()) {
            model.addAttribute("error", "YouTube API Key is not configured");
            return "home";
        }

        if(videoTitle == null || videoTitle.isEmpty()) {
            model.addAttribute("error", "Video Title is Required");
            return "home";
        }

        try {
            SearchVideo result = youTubeService.searchVideos(videoTitle);
            Video primaryVideo = result.getPrimaryVideo();
            List<Video> relatedVideos = result.getRelatedVideos();

            model.addAttribute("primaryVideo", primaryVideo);
            model.addAttribute("relatedVideos", relatedVideos);

            if (primaryVideo != null) {
                model.addAttribute("primaryVideoTagsAsString",
                        primaryVideo.getTags() == null ? "" :
                                String.join(", ", primaryVideo.getTags()));
            }

            if (relatedVideos != null && !relatedVideos.isEmpty()) {
                String allTags = relatedVideos.stream()
                        .filter(v -> v.getTags() != null)
                        .flatMap(v -> v.getTags().stream())
                        .distinct()
                        .collect(Collectors.joining(", "));
                model.addAttribute("allTagsAsString", allTags);
            }

            return "home";
        } catch(Exception e) {
            model.addAttribute("error", e.getMessage());
            return "home";
        }
    }
}
