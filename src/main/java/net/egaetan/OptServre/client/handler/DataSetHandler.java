package net.egaetan.OptServre.client.handler;

import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiRequestBody;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import net.egaetan.OptServre.model.DataSet;

public class DataSetHandler implements Handler {
	
	private List<DataSet> datasets;

	public DataSetHandler(List<DataSet> datasets) {
		this.datasets = datasets;
	}

	@OpenApi(
			//pathParams = {@OpenApiParam(name = "name"), @OpenApiParam(name = "datas")},
			requestBody = @OpenApiRequestBody(content = @OpenApiContent(from = DataSet.class)),
			responses = @OpenApiResponse(status = "200", content = @OpenApiContent(from = DataSet.class, isArray = false)),
			method = HttpMethod.POST)
	
	@Override
	public void handle(Context ctx) throws Exception {
		DataSet body = ctx.bodyAsClass(DataSet.class);
		datasets.add(body);
		ctx.status(200);
		ctx.json(body);
	}


}
