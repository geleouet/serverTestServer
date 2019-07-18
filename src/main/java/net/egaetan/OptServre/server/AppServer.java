package net.egaetan.OptServre.server;

import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class AppServer {

	private final int port;
	private final int portClient;

	public AppServer(int port, int portClient) {
		super();
		this.port = port;
		this.portClient = portClient;
	}

	public void startServer(List<CardDefinition> cards) {
		Javalin server = Javalin.create(config -> {
            config.addStaticFiles("/public");
        }).start(port);
		server.get("/simple", ctx -> ctx.render("index.html"));
		server.get("/client/swagger", ctx -> ctx.redirect("http://localhost:" + portClient + "/swagger"));
		server.get("/cards", ctx -> ctx.json(cards));
		server.get("/shutdown", ctx -> {
			ctx.redirect("/");
			shutdown();
		});
		
		cards.stream().forEach( c -> server.get("/api/" + c.getUrl(), ctx -> perform(ctx, c.test())));
	}

	private static void perform(Context ctx, CheckTest test) {
		try {
			String url = ctx.queryParam("url");
			ctx.json(test.check(url));
		} catch (Exception e) {
			ctx.json(CheckResponse.fail(e.getMessage()));
		}
	}
	
	private static void shutdown() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				System.exit(0);						
			}}).start();
	}
	
	
}
