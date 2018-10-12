package com.harish.sdas.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "sdc_twitter_place")
public class TwitterPlace {

    @SequenceGenerator(name = "TwitterPlace",sequenceName = "SDC_TWITTER_PLACE_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "TwitterPlace")
    @Id
    @Column(name = "ID",nullable = false)
    private Long id;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "country")
    private String country;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "name")
    private String name;
    @Column(name = "place_type")
    private String placeType;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "url")
    private String url;
    @Column(name = "TWEET_ID",insertable = false ,updatable = false)
    private Long tweetId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TWEET_ID", referencedColumnName = "ID")
    private Twitter twitter;

}
