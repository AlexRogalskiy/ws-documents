package com.sensiblemetrics.api.ws.document.generator.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheEventEntry implements Serializable {
  /** Default explicit serialVersionUID for interoperability */
  private static final long serialVersionUID = -1679248864531907771L;

  /** Event service code */
  private String serviceCode;
  /** Event error code */
  private String errorCode;
  /** Event max count */
  private int maxCount;
  /** Collection {@link List} of cache subscribers */
  private final List<String> subscribers = new ArrayList<>();

  public void setSubscribers(final Collection<? extends String> subscribers) {
    this.getSubscribers().clear();
    Optional.ofNullable(subscribers).orElseGet(Collections::emptyList).forEach(this::addSubscriber);
  }

  public void addSubscriber(final String subscriber) {
    if (Objects.nonNull(subscriber)) {
      this.getSubscribers().add(subscriber);
    }
  }
}
