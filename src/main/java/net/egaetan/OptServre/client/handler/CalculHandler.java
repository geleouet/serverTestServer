package net.egaetan.OptServre.client.handler;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import net.egaetan.OptServre.model.Calcul;
import net.egaetan.OptServre.model.CalculResponse;
import net.egaetan.OptServre.model.DataSet;
import net.egaetan.OptServre.model.OperationType;
import net.egaetan.OptServre.model.Value;

public class CalculHandler implements Handler {

	private List<Value> values;
	private List<DataSet> datasets;
	private List<Calcul> calculs;

	public CalculHandler(List<Value> values, List<DataSet> datasets, List<Calcul> calculs) {
		this.values = values;
		this.datasets = datasets;
		this.calculs = calculs;
	}

	@OpenApi(pathParams = { @OpenApiParam(name = "calcul"), @OpenApiParam(name = "dataset") }, 
			responses = @OpenApiResponse(status = "200", content = @OpenApiContent(from = CalculResponse.class, isArray = false)), 
			method = HttpMethod.POST)

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.status(200);
		String cl = ctx.pathParam("calcul");
		String ds = ctx.pathParam("dataset");
		System.out.println("Compute" + cl + " with " + ds);
		Calcul calcul = calculs.stream().filter(c -> c.getName().equals(cl)).findAny().get();
		DataSet dataset = datasets.stream().filter(d -> d.getName().equals(ds)).findAny().get();
		Queue<Integer> queue = new ArrayDeque<>();
		for (String s : dataset.getValues()) {
			Value val = values.stream().filter(v -> v.getName().equals(s)).findAny().get();
			queue.add(val.getValue());
		}

		for (OperationType o : calcul.getOperations()) {
			switch (o) {
			case ADDITION: {
				int a = queue.poll();
				int b = queue.poll();
				queue.add(a + b);
				System.out.println(a +" + " + b);
				break;
			}
			case MULTIPLICATION: {
				int a = queue.poll();
				int b = queue.poll();
				queue.add(a * b);
				System.out.println(a +" * " + b);
				break;
			}
			case NEGATION: {
				int a = queue.poll();
				queue.add(-a);
				break;
			}
			case CONDITION: {
				int a = queue.poll();
				int b = queue.poll();
				int c = queue.poll();
				queue.add(a > 0 ? b : c);
				System.out.println(a +" ? " + b + " : " + c);
				break;
			}
			}
		}

		CalculResponse response = new CalculResponse();
		response.setValue(queue.poll());
		ctx.json(response);
	}
}
