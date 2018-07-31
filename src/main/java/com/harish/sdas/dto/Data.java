package com.harish.sdas.dto.out;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Status;

@Getter // This auto magically adds getters
@ToString // adds to string
@EqualsAndHashCode  // adds equals and hashcode Really :-D This works :-D for more info look at projectlombok.org B-) B-)
public class Data {
    private final String text;
    private final Double latitude;
    private final Double longiturde;
    private final long id;
    private final String name ;
    private final String hashTags;
    private final String eMail;


    public Data(Status twitterStatus) {
        this.text = twitterStatus.getText();
        //this.latitude = 0.00;//twitterStatus.getGeoLocation().getLatitude();
        GeoLocation g = twitterStatus.getGeoLocation();
        if(g!= null){
            this.latitude = g.getLatitude();
            this.longiturde = g.getLongitude();
        }else{
            this.latitude = 0.00;
            this.longiturde = 0.00;
        }
        this.id=twitterStatus.getId();
        this.name = twitterStatus.getUser().getName();
        //this.longiturde = 0.00;//twitterStatus.getGeoLocation().getLongitude();
        String hash ="";
        for (HashtagEntity he :twitterStatus.getHashtagEntities()){
           hash =  hash+he.getText()+",";
        }
        this.hashTags = hash;
        this.eMail = twitterStatus.getUser().getEmail();

    }
}
