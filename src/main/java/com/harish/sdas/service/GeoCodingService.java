package com.harish.sdas.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.GeoLocation;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class GeoCodingService {

    public Map<String, GeoLocation> getGeoLocationFromAddress(Set<String> distinctAddresses) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(System.getenv("GOOGLE_API_KEY"))
                .build();

        return distinctAddresses.stream()
                .collect(toMap(identity(), address -> {
                    try {
                        GeocodingResult[] results = GeocodingApi.geocode(context,
                                address).await();
                        log.info(Arrays.asList(results).toString());
                        if(results.length > 0 ) {
                            LatLng location = results[0].geometry.location;
                            return new GeoLocation(location.lat, location.lng);
                        }
                    } catch (Exception e) {
                        log.error("unable to fetch location", e);
                    }
                    return new GeoLocation(0, 0);
                }));

    }
}
