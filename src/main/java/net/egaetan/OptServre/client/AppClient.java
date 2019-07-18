package net.egaetan.OptServre.client;

import java.util.ArrayList;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import net.egaetan.OptServre.client.handler.CalculDefinitionHandler;
import net.egaetan.OptServre.client.handler.CalculHandler;
import net.egaetan.OptServre.client.handler.DataSetHandler;
import net.egaetan.OptServre.client.handler.ResetHandler;
import net.egaetan.OptServre.model.Calcul;
import net.egaetan.OptServre.model.DataSet;
import net.egaetan.OptServre.model.Value;

public class AppClient {

	private final int port;
	
	public AppClient(int port) {
		super();
		this.port = port;
	}

	public void startClient() {
		List<Value> values = new ArrayList<>();
		List<Calcul> calculs = new ArrayList<>();
		List<DataSet> datasets = new ArrayList<>();
		
		Javalin app = Javalin.create(config -> {
			config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
		}).start(port);
		app.delete("/reset", new ResetHandler(values, datasets, calculs));
		app.post("/value/", new ValueHandler(values));
		app.post("/dataSet/", new DataSetHandler(datasets));
		app.post("/calculDefinition/", new CalculDefinitionHandler(calculs));
		app.get("/calcul/:calcul/:dataset", new CalculHandler(values, datasets, calculs));
	}

	private static OpenApiOptions getOpenApiOptions() {
		Info applicationInfo = new Info().version("1.0").description("OptServer documentation").title("Kata Opt Server");
		return new OpenApiOptions(applicationInfo)
				.path("/swagger-docs")
				.activateAnnotationScanningFor(ValueHandler.class.getPackageName(), Value.class.getPackageName())
				.swagger(new SwaggerOptions("/swagger").title("OptClient Documentation"));
	}
	
}
