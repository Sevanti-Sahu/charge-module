package com.banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("fxdata")
public class Fxdata {

	private List<Fxcharges> fxcharges = new ArrayList<>();
	
	public static class Fxcharges{
		
		private String country;
		private String language;
		private String link;
		
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		@Override
		public String toString() {
			return "FxCharges [country=" + country + ", language=" + language + ", link=" + link + "]";
		}
		
	}

	public List<Fxcharges> getFxcharges() {
		return fxcharges;
	}

	public void setFxcharges(List<Fxcharges> fxcharges) {
		this.fxcharges = fxcharges;
	}

	@Override
	public String toString() {
		return "FxdataProperties [fxcharges=" + fxcharges + "]";
	}
	
	/*public Fxcharges getChargesFromCountry(String country)
	{
		Map<String,Fxcharges> map = new HashMap<>();
		for(Fxcharges fx : fxcharges)
		{
			map.put(fx.getCountry(),fx);
		}
		return map.get(country);
	}*/
	
}
