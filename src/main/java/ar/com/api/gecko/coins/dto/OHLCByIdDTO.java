package ar.com.api.gecko.coins.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OHLCByIdDTO implements IFilterDTO {

 private String idCoin;
 private String vsCurrency;
 private String days;

 @Override
 public String getUrlFilterString() {
  
  StringBuilder uBuilder = new StringBuilder();
  uBuilder.append("?vs_currency=").append(vsCurrency)
          .append("&days=").append(days);

  return uBuilder.toString();
 }
 
}
