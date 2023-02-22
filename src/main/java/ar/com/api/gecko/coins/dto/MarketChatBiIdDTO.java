package ar.com.api.gecko.coins.dto;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MarketChatBiIdDTO implements IFilterDTO {

 private String idCoin;
 private String vsCurrency;
 private long days;
 private Optional<String> interval;

 @Override
 public String getUrlFilterString() {
  
  StringBuilder uBuilder = new StringBuilder();
  uBuilder.append("?vs_currency=").append(vsCurrency)
          .append("&days=").append(days);

  if(interval.isPresent())
   uBuilder.append("&interval=").append(interval.get());

  return uBuilder.toString();
 }
 
}
