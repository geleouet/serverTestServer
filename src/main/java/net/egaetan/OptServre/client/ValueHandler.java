package net.egaetan.OptServre.client;

import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiRequestBody;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import net.egaetan.OptServre.model.Value;

public class ValueHandler implements Handler {

	private List<Value> values;

	public ValueHandler(List<Value> values) {
		this.values = values;
	}

	@OpenApi(
			requestBody = @OpenApiRequestBody(content = @OpenApiContent(from = Value.class)),
			responses = @OpenApiResponse(status = "200", content = @OpenApiContent(from = Value.class, isArray = false)),
			method = HttpMethod.POST)
	
	@Override
	public void handle(Context ctx) throws Exception {
		Value body = ctx.bodyAsClass(Value.class);
		values.add(body);
		ctx.status(200);
		ctx.json(body);
	}

}
