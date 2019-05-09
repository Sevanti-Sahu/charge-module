package com.banking;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("chargesdata")
public class Chargesdata {

    private List<Normalcharges> normalcharges = new ArrayList<>();

    public static class Normalcharges {
    
        private String country;
        private String language;
        private Boolean defaultLanguage;
        private String orderType;
        private List<String> links;
      

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

		public Boolean getDefaultLanguage() {
			return defaultLanguage;
		}

		public void setDefaultLanguage(Boolean defaultLanguage) {
			this.defaultLanguage = defaultLanguage;
		}

		public String getOrderType() {
			return orderType;
		}

		public void setOrderType(String orderType) {
			this.orderType = orderType;
		}     

		public List<String> getLinks() {
			return links;
		}

		public void setLinks(List<String> links) {
			this.links = links;
		}

		@Override
		public String toString() {
			return "Normalcharges [country=" + country + ", language=" + language + ", defaultLanguage="
					+ defaultLanguage + ", orderType=" + orderType + ", links=" + links + "]";
		}

	
    }

	public List<Normalcharges> getNormalcharges() {
		return normalcharges;
	}

	public void setNormalcharges(List<Normalcharges> normalcharges) {
		this.normalcharges = normalcharges;
	}

	@Override
	public String toString() {
		return "Chargesdata [normalcharges=" + normalcharges + "]";
	}

}