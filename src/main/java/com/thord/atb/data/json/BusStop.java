package com.thord.atb.data.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStop {
  @JsonAlias({"nomeAzienda", "nome_Az"})
  private Integer id;
  @JsonProperty("codAzNodo")
  private Integer nodeID;
  @JsonAlias({"nomeNodo", "name"})
  private String name;
  @JsonAlias({"descrNodo", "descrizione"})
  private String description;
  @JsonProperty("bitMaskProprieta")
  private String bitMaskProperties;
  @JsonProperty("codeMobile")
  private String mobileCode;
  @JsonProperty("nomeMobile")
  private String mobileName;
  @JsonAlias({"coordLon", "lon"})
  private Double longitude;
  @JsonAlias({"coordLat", "lat"})
  private Double latitude;

  @JsonCreator
  public BusStop() {
    // Jackson Creator
  }

  public Integer getId() {
    return id;
  }

  public Integer getNodeID() {
    return nodeID;
  }

  public String getName() {
    return name;
  }

  public String getNameNormalized() {
    String[] split = description.split("  ", 2);
    return split[0];
  }

  public String getDescription() {
    return description;
  }

  public String getDescriptionNormalized() {
    String[] split = description.split("  ", 2);
    return split[0] + " " + split[1].trim();
  }

  public String getBitMaskProperties() {
    return bitMaskProperties;
  }

  public String getMobileCode() {
    return mobileCode;
  }

  public Double getLongitude() {
    return longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("id", id).add("nodeID", nodeID).add("name", name)
        .add("description", description).add("bitMaskProperties", bitMaskProperties)
        .add("mobileCode", mobileCode).add("longitude", longitude).add("latitude", latitude)
        .toString();
  }

}
