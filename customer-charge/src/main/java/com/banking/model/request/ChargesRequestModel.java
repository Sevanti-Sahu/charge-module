package com.banking.model.request;


import javax.validation.constraints.NotNull;



public class ChargesRequestModel {

	private Debitoragent debitoragent;
	private Instructedamount instructedamount;
	private Creditoracct creditoracct;
	private String chargebearer;
	private String language;
	private String loginmeanscountry;
	
	public static class Debitoragent{
		
		@NotNull(message="dracct no is mandatory")
		private String dracct;
		private String drcurr;
		public String getDracct() {
			return dracct;
		}
		public void setDracct(String dracct) {
			this.dracct = dracct;
		}
		public String getDrcurr() {
			return drcurr;
		}
		public void setDrcurr(String drcurr) {
			this.drcurr = drcurr;
		}
		@Override
		public String toString() {
			return "Debitoragent [dracct=" + dracct + ", drcurr=" + drcurr + "]";
		}
		
	}
	
	public static class Instructedamount{
		
		@NotNull
		private double amount;
		
		@NotNull(message="currency is mandatory")
		private String currency;
		
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		@Override
		public String toString() {
			return "Instructedamount [amount=" + amount + ", currency=" + currency + "]";
		}
		
	}
	
	public static class Creditoracct{
		
		@NotNull(message="cracct no is mandatory")
		private String cracct;
		private String crcurr;
		public String getCracct() {
			return cracct;
		}
		public void setCracct(String cracct) {
			this.cracct = cracct;
		}
		public String getCrcurr() {
			return crcurr;
		}
		public void setCrcurr(String crcurr) {
			this.crcurr = crcurr;
		}
		@Override
		public String toString() {
			return "Creditoracct [cracct=" + cracct + ", crcurr=" + crcurr + "]";
		}
		
	}

	public Debitoragent getDebitoragent() {
		return debitoragent;
	}

	public void setDebitoragent(Debitoragent debitoragent) {
		this.debitoragent = debitoragent;
	}

	public Instructedamount getInstructedamount() {
		return instructedamount;
	}

	public void setInstructedamount(Instructedamount instructedamount) {
		this.instructedamount = instructedamount;
	}

	public Creditoracct getCreditoracct() {
		return creditoracct;
	}

	public void setCreditoracct(Creditoracct creditoracct) {
		this.creditoracct = creditoracct;
	}

	public String getChargebearer() {
		return chargebearer;
	}

	public void setChargebearer(String chargebearer) {
		this.chargebearer = chargebearer;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLoginmeanscountry() {
		return loginmeanscountry;
	}

	public void setLoginmeanscountry(String loginmeanscountry) {
		this.loginmeanscountry = loginmeanscountry;
	}

	@Override
	public String toString() {
		return "ChargesRequestModel [debitoragent=" + debitoragent + ", instructedamount=" + instructedamount
				+ ", creditoracct=" + creditoracct + ", chargebearer=" + chargebearer + ", language=" + language
				+ ", loginmeanscountry=" + loginmeanscountry + "]";
	}
	
	
}
