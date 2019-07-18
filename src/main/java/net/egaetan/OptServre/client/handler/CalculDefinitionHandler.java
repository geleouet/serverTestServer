package net.egaetan.OptServre.client.handler;

import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiRequestBody;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import net.egaetan.OptServre.model.Calcul;

public class CalculDefinitionHandler implements Handler {

	private List<Calcul> calculs;

	public CalculDefinitionHandler(List<Calcul> calculs) {
		this.calculs = calculs;
	}

	@OpenApi(
			requestBody = @OpenApiRequestBody(content = @OpenApiContent(from = Calcul.class)),
			responses = @OpenApiResponse(status = "200", content = @OpenApiContent(from = Calcul.class, isArray = false)),
			method = HttpMethod.POST)
	
	@Override
	public void handle(Context ctx) throws Exception {
		Calcul body = ctx.bodyAsClass(Calcul.class);
		calculs.add(body);
		ctx.status(200);
		ctx.json(body);
	}



}
