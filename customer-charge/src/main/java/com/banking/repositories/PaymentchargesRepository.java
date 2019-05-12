package com.banking.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.banking.Chargesdata;
import com.banking.Chargesdata.Normalcharges;
import com.banking.Countrycurr;
import com.banking.Countrycurr.Mappinglist;
import com.banking.Fxdata;
import com.banking.Fxdata.Fxcharges;
import com.banking.model.response.ChargesResponse.Link;
import com.banking.model.request.ChargesRequestModel;
import com.banking.model.response.ChargesResponse;

@Repository
public class PaymentchargesRepository {
	

	private Fxdata fxdata;
	private Chargesdata chargesdata;
	private Countrycurr countrycurr;	
	private Map<String,Fxcharges> fxchargesmap ;
	private Map<String,String> countrycurrmap;
	private Map<String,String> countrylanguagemap;
	private Map<CountryLanguageOrderType,Normalcharges> normalchargesmap;
	
	@Autowired
	PaymentchargesRepository(Fxdata fxdata,Chargesdata chargesdata,Countrycurr countrycurr)
	{
		this.fxdata = fxdata;
		this.chargesdata = chargesdata;
		this.countrycurr = countrycurr;
	}
	
	
	@PostConstruct
	public void init()
	{
		this.fxchargesmap = preparefxmap(fxdata);
		this.countrycurrmap = prepareCountryCurrMap(countrycurr);
		this.countrylanguagemap = mappingDefaultLanguageOfCountry(chargesdata);
		this.normalchargesmap = prepareNormalChargesMap(chargesdata);
		//normalchargesmap.forEach((k,v)->System.out.println("key: " + k + ", value: " + v));
	
	}
	
	private Map<String,Fxcharges> preparefxmap(Fxdata fxdata)
	{
		Map<String,Fxcharges> fxmap = new HashMap<>();
		
		for(Fxcharges fx : fxdata.getFxcharges())
		{
			fxmap.put((fx.getCountry()).toUpperCase(),fx);
	
		}
		return fxmap;
	}
	
	private Map<String,String> prepareCountryCurrMap(Countrycurr countrycurr)
	{
		Map<String,String> currmap = new HashMap<>();
		for(Mappinglist ml : countrycurr.getMappinglist())
	  	  {
			currmap.put((ml.getCountry()).toUpperCase(),ml.getCurrency());
		  }
		return currmap;
	}
	
	private Map<String,String> mappingDefaultLanguageOfCountry(Chargesdata chargesdata)
	{
		Map<String,String> countrylanguagemap = new HashMap<>();
		for(Normalcharges nm : chargesdata.getNormalcharges())
		{
			if(nm.getDefaultLanguage() == true){
				countrylanguagemap.put((nm.getCountry()).toUpperCase(),nm.getLanguage());
			}
			
		}
		return countrylanguagemap;
	}
	 
	private Map<CountryLanguageOrderType,Normalcharges> prepareNormalChargesMap(Chargesdata chargesdata)
	{
		List<Normalcharges> normalcharges = chargesdata.getNormalcharges();
		Map<CountryLanguageOrderType,Normalcharges> normalchargesmap = new HashMap<>();
		for(Normalcharges nm : normalcharges)
		{	
			normalchargesmap.put(CountryLanguageOrderType.of(nm.getCountry().toUpperCase(), nm.getLanguage().toUpperCase(), nm.getOrderType().toUpperCase()), nm);
		}
		return normalchargesmap;
	}
	
	//Method for retrieving currency of Country
	public String getCurrencyOfCountry(String country)
	{
		  return countrycurrmap.get(country.toUpperCase());
	}
		
	//Method to get FX charges details
	public Fxcharges getFxCharges(String country)
	{
		return fxchargesmap.get(country.toUpperCase());
	}
	
	private String getLanguageOfCountry(String country)
	{
		return countrylanguagemap.get(country.toUpperCase());
	}
	
	//Method determining order type of transaction(POT API)
	public String getPaymentType(String dracct,String cracct,String curr)
	{
		if((dracct.substring(0,2)) == (cracct.substring(0,2)))
		{
			if(curr == "EUR")
				return "INST" ;
			else
				return "DOMCT" ;
		}
		else
			return "TIP";
	}
	
	//Method to get Normal charges details
	public Normalcharges getNormalCharges(String paymentType,String country)
	{
		String language = getLanguageOfCountry(country);
		return normalchargesmap.get(CountryLanguageOrderType.of(country.toUpperCase(), language.toUpperCase(), paymentType.toUpperCase()));
	}
		
	public static class CountryLanguageOrderType{
		private String country;
		private String language;
		private String orderType;
		public CountryLanguageOrderType(String country, String language, String orderType) {
			super();
			this.country = country;
			this.language = language;
			this.orderType = orderType;
		}
		
		@Override
		public String toString() {
			return "CountryLanguageOrderType [country=" + country + ", language=" + language + ", orderType="
					+ orderType + "]";
		}

		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((country == null) ? 0 : country.hashCode());
			result = prime * result + ((language == null) ? 0 : language.hashCode());
			result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CountryLanguageOrderType other = (CountryLanguageOrderType) obj;
			if (country == null) {
				if (other.country != null)
					return false;
			} else if (!country.equals(other.country))
				return false;
			if (language == null) {
				if (other.language != null)
					return false;
			} else if (!language.equals(other.language))
				return false;
			if (orderType == null) {
				if (other.orderType != null)
					return false;
			} else if (!orderType.equals(other.orderType))
				return false;
			return true;
		}

		public static CountryLanguageOrderType of(String country,String language,String orderType)
		{
			return new CountryLanguageOrderType(country,language,orderType);
		}
	}
}
