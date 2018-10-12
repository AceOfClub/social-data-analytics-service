package com.harish.sdas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TwitterService {

    public List<Status> getTweetsInLocation(GeoLocation geoLocation, Double radius) {
        return getTweetsForQuery(new Query().geoCode(geoLocation, radius, Query.KILOMETERS.toString()));
    }

    public List<Status> getTweetsForHashTag(String hashTag) {
        return getTweetsForQuery(new Query(hashTag));
    }


    private List<Status> getTweetsForQuery(Query query) {
        List<Status> tweets = new ArrayList<>();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(System.getenv("TWITTER_O_AUTH_CONSUMER_KEY")) // TODO: Integragte env with drop wizard
                .setOAuthConsumerSecret(System.getenv("TWITTER_O_AUTH_CONSUMER_SECRET"))
                .setOAuthAccessToken(System.getenv("TWITTER_O_AUTH_ACCESS_TOKEN"))
                .setOAuthAccessTokenSecret(System.getenv("TWITTER_O_AUTH_ACCESS_TOKEN_SECRET"))
                .setTweetModeExtended(true); // When free read about 12-factor app https://12factor.net/
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        try {
            QueryResult result;
            do {
                result = twitter.search(query);
                tweets.addAll(result.getTweets()); // TODO: Handle huge data that might go beyond RAM size
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
            log.error("Unable to contact twitter", te);
        }
        return tweets;
    }
}
