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
@Table(name = "sdc_twitter_hastags")

public class TwitterHashtag {
    @SequenceGenerator(name = "TwitterHashtag",sequenceName = "SDC_TWITTER_HASHTAGS_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "TwitterHashtag")
    @Id
    @Column(name = "ID",nullable = false)
    private Long id;
    @Column(name = "tweet_id",insertable = false ,updatable = false)
    private Long tweetId;
    @Column(name = "hashtag")
    private String hashtag;
    @Column(name = "indices")
    private String indices;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TWEET_ID",referencedColumnName = "ID")
    private Twitter twitter;


}
