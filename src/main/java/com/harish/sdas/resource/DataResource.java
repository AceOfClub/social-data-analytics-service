package com.harish.sdas.resource;

import com.codahale.metrics.annotation.Timed;
import com.harish.sdas.dto.Data;
import com.harish.sdas.service.TwitterService;
import lombok.extern.slf4j.Slf4j;
import twitter4j.GeoLocation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("data")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class DataResource {

 /*
    TODO Add dependency injection using google GUICE

    private final TwitterService twitterService;

    @Inject
    public DataResource(TwitterService twitterService) {
        this.twitterService = twitterService;
    }*/


    @GET
    @Path("location/latitude/{latitude}/longitude/{longitude}/radius/{radius}")
    @Timed
    public List<Data> getDataForLocation(@PathParam("latitude") Double latitude, @PathParam("longitude") Double longitude, @PathParam("radius") Double radius) {
        return new TwitterService().getTweetsInLocation(new GeoLocation(latitude, longitude), radius)
                .stream()
                //.filter(status -> status.getHashtagEntities() != null)
                .map(Data::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("hashtag/{hashtag}")
    public List<Data> getDataForHashTag(@PathParam("hashtag") String hashTag) {
        return new TwitterService().getTweetsForHashTag(hashTag)
                .stream()
                .map(Data::new)
                .collect(Collectors.toList());
    }


}
