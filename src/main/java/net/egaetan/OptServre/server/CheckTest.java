package net.egaetan.OptServre.server;

@FunctionalInterface
public interface CheckTest {

	public CheckResponse check(String url);
	
}
