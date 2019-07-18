package net.egaetan.OptServre.client.handler;

import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import net.egaetan.OptServre.model.Calcul;
import net.egaetan.OptServre.model.DataSet;
import net.egaetan.OptServre.model.Value;

public class ResetHandler implements Handler {

	private List<Value> values;
	private List<DataSet> datasets;
	private List<Calcul> calculs;

	public ResetHandler(List<Value> values, List<DataSet> datasets, List<Calcul> calculs) {
		this.values = values;
		this.datasets = datasets;
		this.calculs = calculs;
	}

	@OpenApi(
			responses = @OpenApiResponse(status = "200"),
			method = HttpMethod.DELETE)
	
	@Override
	public void handle(Context ctx) throws Exception {
		values.clear();
		datasets.clear();
		calculs.clear();
		ctx.status(200);
	}

}
