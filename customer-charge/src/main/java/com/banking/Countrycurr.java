package com.banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("countrycurr")
public class Countrycurr {
	
	private List<Mappinglist> mappinglist = new ArrayList<>();
	
	
	public static class Mappinglist{
		
		private String country;
		private String currency;
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		@Override
		public String toString() {
			return "Mappinglist [country=" + country + ", currency=" + currency + "]";
		}
	}

	public List<Mappinglist> getMappinglist() {
		return mappinglist;
	}

	public void setMappinglist(List<Mappinglist> mappinglist) {
		this.mappinglist = mappinglist;
	}

	@Override
	public String toString() {
		return "CountrycurrProperties [mappinglist=" + mappinglist + "]";
	}
	
	/* public String getCurrencyOfCountry(String cntry)
	{
	  Map<String,String> map = new HashMap<>();
	  for(Mappinglist ml : mappinglist)
  	  {
		 map.put(ml.getCountry(),ml.getCurrency());
	  }
	  return map.get(cntry);
	} */
}