package org.programirame.athena.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "bucketName",
        "bucketCount"
})
public class Bucket {

    @JsonProperty("bucketName")
    private String bucketName;
    @JsonProperty("bucketCount")
    private Long bucketCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Bucket() {
    }

    /**
     *
     * @param bucketCount
     * @param bucketName
     */
    public Bucket(String bucketName, Long bucketCount) {
        this.bucketName = bucketName;
        this.bucketCount = bucketCount;
    }

    /**
     *
     * @return
     * The bucketName
     */
    @JsonProperty("bucketName")
    public String getBucketName() {
        return bucketName;
    }

    /**
     *
     * @param bucketName
     * The bucketName
     */
    @JsonProperty("bucketName")
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     *
     * @return
     * The bucketCount
     */
    @JsonProperty("bucketCount")
    public Long getBucketCount() {
        return bucketCount;
    }

    /**
     *
     * @param bucketCount
     * The bucketCount
     */
    @JsonProperty("bucketCount")
    public void setBucketCount(Long bucketCount) {
        this.bucketCount = bucketCount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}