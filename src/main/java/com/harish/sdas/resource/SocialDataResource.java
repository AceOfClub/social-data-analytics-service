package com.harish.sdas.resource;

import com.harish.sdas.dto.KeywordData;
import com.harish.sdas.repository.TwitterRepository;
import com.harish.sdas.dto.LocationData;
import com.harish.sdas.model.Twitter;
import com.harish.sdas.service.SocialDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("data")
@Slf4j
@RestController
public class SocialDataResource {

 /*
    TODO Add dependency injection using google GUICE

    private final TwitterService twitterService;

    @Inject
    public SocialDataResource(TwitterService twitterService) {
        this.twitterService = twitterService;
    }*/

    private final TwitterRepository twitterDao;

    public SocialDataResource(TwitterRepository twitterDao) {
        this.twitterDao = twitterDao;
    }


    @GetMapping("location/latitude/{latitude}/longitude/{longitude}/radius/{radius}")
    public List<LocationData> getDataForLocation(@PathVariable Double latitude, @PathVariable Double longitude, @PathVariable("radius") Double radius) {
        return new SocialDataService(twitterDao).getDataForLocation(latitude, longitude, radius);
    }

    @GetMapping("hashtag/{hashtag}")
    public List<KeywordData> getDataForHashTag(@PathVariable("hashtag") String hashTag) {
        return new SocialDataService(twitterDao).getDataForHashTag(hashTag);
    }


}
