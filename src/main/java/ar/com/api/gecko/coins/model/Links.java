package ar.com.api.gecko.coins.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {
 
 @ToString.Exclude
 @JsonProperty("homepage")
 private List<String> homepage;

 @ToString.Exclude
 @JsonProperty("blockchain_site")
 private List<String> blockchainSite;

 @ToString.Exclude
 @JsonProperty("official_forum_url")
 private List<String> officialForumUrl;

 @ToString.Exclude
 @JsonProperty("chat_url")
 private List<String> chatUrl;

 @ToString.Exclude
 @JsonProperty("announcement_url")
 private List<String> announcementUrl;

 @JsonProperty("twitter_screen_name")
 private String twitterScreenName;

 @JsonProperty("current_price")
 private String facebookUsername;

 @JsonProperty("facebook_username")
 private String bitcointalkThreadIdentifier;

 @JsonProperty("telegram_channel_identifier")
 private String telegramChannelIdentifier;

 @JsonProperty("subreddit_url")
 private String subredditUrl;

 @JsonProperty("repos_url")
 private ReposUrl reposUrl;

}
