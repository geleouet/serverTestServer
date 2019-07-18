package net.egaetan.OptServre;

import java.io.IOException;
import java.util.List;

import net.egaetan.OptServre.checker.Cards;
import net.egaetan.OptServre.client.AppClient;
import net.egaetan.OptServre.desktop.DesktopApp;
import net.egaetan.OptServre.server.AppServer;
import net.egaetan.OptServre.server.CardDefinition;

public class App {
	
	private static final int SERVER_PORT = 7777;
	private static final int CLIENT_PORT = 7750;

	public static void main(String[] args) throws IOException {
		List<CardDefinition> allCards = Cards.all();
		
		new AppServer(SERVER_PORT, CLIENT_PORT).startServer(allCards);
		new AppClient(CLIENT_PORT).startClient();
		
		new DesktopApp("http://localhost:" + SERVER_PORT).launch();
	}
	
	
}
