package com.banking.model.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ChargesResponse {

	private List<Link> link = new ArrayList<>();
	
	public static class Link{
		private String hrref;
		private String rel;
		private String language;
		private String media;
		private String description;
		
		public Link()
		{
			
		}
		public Link(String hrref, String rel, String language, String media, String description) {
			super();
			this.hrref = hrref;
			this.rel = rel;
			this.language = language;
			this.media = media;
			this.description = description;
		}
		public String getHrref() {
			return hrref;
		}
		public void setHrref(String hrref) {
			this.hrref = hrref;
		}
		public String getRel() {
			return rel;
		}
		public void setRel(String rel) {
			this.rel = rel;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getMedia() {
			return media;
		}
		public void setMedia(String media) {
			this.media = media;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		@Override
		public String toString() {
			return "Link [hrref=" + hrref + ", rel=" + rel + ", language=" + language + ", media=" + media
					+ ", description=" + description + "]";
		}
		
	}

	public List<Link> getLink() {
		return link;
	}

	public void setLink(List<Link> link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "ChargesResponse [link=" + link + "]";
	}
	
}
