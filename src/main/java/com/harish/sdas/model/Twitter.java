package com.harish.sdas.model;

import lombok.*;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "sdc_twitter_main")
public class Twitter {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "is_truncated")
    private Boolean isTruncated;
    @Column(name = "in_reply_to_user_id")
    private Long inReplyToUserId;
    @Column(name = "in_reply_to_status_id")
    private Long inReplyToStatusId;
    @Column(name = "is_favorite")
    private Boolean isFavorite;
    @Column(name = "source")
    private String source;
    @Column(name = "in_reply_to_screen_name")
    private String inReplyToScreenName;
    @Column(name = "retweeted")
    private Boolean retweeted;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "retweeted_count")
    private Long retweetedCount;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "name")
    private String name;
    @Column(name = "hash_tags")
    private String hashTags;
    @Column(name = "email")
    private String email;
    @Column(name = "location")
    private String location;
    @OneToMany(mappedBy = "twitter" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<TwitterHashtag> hashtagList;
    @OneToOne(mappedBy = "twitter" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private TwitterPlace twitterPlace;

    public Twitter(Status twitterStatus) {
        this.text = twitterStatus.getText();
        GeoLocation g = twitterStatus.getGeoLocation();
        if (g != null) {
            this.latitude = g.getLatitude();
            this.longitude = g.getLongitude();
        } else {
            this.latitude = 0.00;
            this.longitude = 0.00;
        }
        this.id = twitterStatus.getId();
        this.name = twitterStatus.getUser().getName();
        this.hashTags = Arrays.stream(twitterStatus.getHashtagEntities()).map(HashtagEntity::getText).collect(Collectors.joining(","));
        this.email = twitterStatus.getUser().getEmail();
        this.location = twitterStatus.getUser().getLocation();
        this.isTruncated = twitterStatus.isTruncated();
        this.inReplyToUserId = twitterStatus.getInReplyToUserId();
        this.inReplyToStatusId = twitterStatus.getInReplyToStatusId();
        this.isFavorite = twitterStatus.isFavorited();
        this.source = twitterStatus.getSource();
        this.inReplyToScreenName = twitterStatus.getInReplyToScreenName();
        this.retweeted = twitterStatus.isRetweeted();
        this.createdAt = twitterStatus.getCreatedAt();
        this.retweetedCount = (long) twitterStatus.getRetweetCount();
        if(twitterStatus.getPlace()!=null) {

             twitterPlace = new TwitterPlace();
            twitterPlace.setCountry(twitterStatus.getPlace().getCountry());
            twitterPlace.setCountryCode(twitterStatus.getPlace().getCountryCode());
            twitterPlace.setFullName(twitterStatus.getPlace().getFullName());
            twitterPlace.setPlaceType(twitterStatus.getPlace().getPlaceType());
            twitterPlace.setStreetAddress(twitterStatus.getPlace().getStreetAddress());
            twitterPlace.setUrl(twitterStatus.getPlace().getURL());
            twitterPlace.setTweetId(twitterStatus.getId());
            twitterPlace.setTwitter(this);
        }
        this.hashtagList = new ArrayList<TwitterHashtag>();
        for (HashtagEntity hash : twitterStatus.getHashtagEntities()){
            TwitterHashtag tht = new TwitterHashtag();
            tht.setHashtag(hash.getText());
            tht.setTweetId(twitterStatus.getId());
            tht.setTwitter(this);
            hashtagList.add(tht);
        }


    }

    public Twitter withGeoLocation(GeoLocation geoLocation) {
        return this.toBuilder()
                .latitude(geoLocation.getLatitude())
                .longitude(geoLocation.getLongitude())
                .build();
    }

    public List<String> getHashTags() {
        return Arrays.asList(hashTags.split(","));
    }
}
