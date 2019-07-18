package net.egaetan.OptServre.server;

public class CardDefinition {

	String title;
	String url;
	transient CheckTest test;

	public CardDefinition() {
	}
	
	public CardDefinition(String title, String url, CheckTest test) {
		super();
		this.title = title;
		this.url = url;
		this.test = test;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public CheckTest test() {
		return test;
	}

	public static CardDefinition create(String title, String url, CheckTest test) {
		return new CardDefinition(title, url, test);
	}

}
