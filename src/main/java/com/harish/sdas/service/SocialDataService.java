package com.harish.sdas.service;

import com.harish.sdas.dto.KeywordData;
import com.harish.sdas.repository.TwitterRepository;
import com.harish.sdas.model.Twitter;
import com.harish.sdas.dto.LocationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.GeoLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SocialDataService {

    private final TwitterRepository twitterRepository;

    @Autowired
    public SocialDataService(TwitterRepository twitterRepository) {
        this.twitterRepository = twitterRepository;
    }


    public List<LocationData> getDataForLocation(Double latitude, Double longitude, Double radius) {
        List<Twitter> twitterData = new TwitterService().getTweetsInLocation(new GeoLocation(latitude, longitude), radius)
                .stream()
                .map(Twitter::new)
                .collect(toList());

        List<Twitter> twitterDataWithLocation = populateWithGeoLocation(twitterData);

        twitterRepository.saveAll(twitterDataWithLocation);

        Map<String, Long> hashTagCount = twitterDataWithLocation.stream()
                .flatMap(d -> d.getHashTags().stream())
                .filter(hashTag -> hashTag != null && !hashTag.trim().isEmpty())
                .collect(Collectors.groupingBy(identity(), counting()));

        List<LocationData> locationData = hashTagCount.entrySet().stream()
                .map(kv -> new LocationData(kv.getKey(), kv.getValue()))
                .collect(toList());

        return locationData.stream().sorted(comparingLong(LocationData::getCount).reversed()).limit(20).collect(toList());

    }

    public List<KeywordData> getDataForHashTag(String hashTag) {
        List<Twitter> twitterData = new TwitterService().getTweetsForHashTag(hashTag)
                .stream()
                .map(Twitter::new)
                .collect(toList());

        List<Twitter> twitterDataWithLocation = populateWithGeoLocation(twitterData);

        twitterRepository.saveAll(twitterDataWithLocation);

        return twitterDataWithLocation.stream()
                .map(tw -> new KeywordData(tw.getLatitude(), tw.getLongitude()))
                .collect(toList());
    }

    private List<Twitter> populateWithGeoLocation(List<Twitter> twitterData) {
        Set<String> distinctAddresses = twitterData.stream()
                .map(Twitter::getLocation)
                .filter(location -> !isBlank(location)).collect(toSet());

        Map<String, GeoLocation> addressLocation = new GeoCodingService().getGeoLocationFromAddress(distinctAddresses);

        return twitterData.stream().map(data -> {
            GeoLocation defaultLocation = new GeoLocation(0, 0);
            if (data.getLatitude() == 0 && data.getLongitude() == 0) {
                return data.withGeoLocation(addressLocation.getOrDefault(data.getLocation(), defaultLocation));
            }
            return data;
        }).collect(toList());
    }
}
