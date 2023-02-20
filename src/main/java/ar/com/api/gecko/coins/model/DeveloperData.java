package ar.com.api.gecko.coins.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeveloperData {
 
 @JsonProperty("forks")
 private double forks;

 @JsonProperty("stars")
 private double stars;
 
 @JsonProperty("subscribers")
 private double subscribers;
 
 @JsonProperty("total_issues")
 private double totalIssues;
 
 @JsonProperty("closed_issues")
 private double closedIssues;
 
 @JsonProperty("pull_requests_merged")
 private double pullRequestsMerged;
 
 @JsonProperty("pull_request_contributors")
 private double pullRequestContributors;
 
 @JsonProperty("code_additions_deletions_4_weeks")
 private CodeAdditionsDeletions4Weeks codeAdditionsDeletions4Weeks;
 
 @JsonProperty("commit_count_4_weeks")
 private double commitCount4Weeks;
 
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonProperty("last_4_weeks_commit_activity_series")
 private List<Long> last4WeeksCommitActivitySeries;

}
