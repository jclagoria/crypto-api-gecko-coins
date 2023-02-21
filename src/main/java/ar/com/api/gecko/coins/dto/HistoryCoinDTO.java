package ar.com.api.gecko.coins.dto;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HistoryCoinDTO implements IFilterDTO {

 private String idCoin;
 private String date;
 private Optional<String> location;

 @Override
 public String getUrlFilterString() {
  
  StringBuilder urlBuilder = new StringBuilder();
  urlBuilder.append("?date=").append(date);

  if(location.isPresent())
   urlBuilder.append("&location=").append(location.get());

  return urlBuilder.toString();
 }
 
}
