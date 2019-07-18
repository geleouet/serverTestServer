package net.egaetan.OptServre.checker;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import net.egaetan.OptServre.checker.api.CalculDefinitionService;
import net.egaetan.OptServre.checker.api.CalculService;
import net.egaetan.OptServre.checker.api.DataSetService;
import net.egaetan.OptServre.checker.api.ResetService;
import net.egaetan.OptServre.checker.api.ValueService;
import net.egaetan.OptServre.model.Calcul;
import net.egaetan.OptServre.model.CalculResponse;
import net.egaetan.OptServre.model.DataSet;
import net.egaetan.OptServre.model.OperationType;
import net.egaetan.OptServre.model.Value;
import net.egaetan.OptServre.server.CheckResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Checker {

	private ValueService serviceValue;
	private DataSetService serviceDataSet;
	private CalculDefinitionService serviceCalculDefinition;
	private CalculService serviceCalcul;
	private ResetService serviceReset;
	
	public static Checker create(String url) {
		Retrofit retrofit = new Retrofit
				.Builder()
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl(url.startsWith("http") ? url : ("http://" + url))
				.build();
		return new Checker(
				retrofit.create(ValueService.class),
				retrofit.create(DataSetService.class),
				retrofit.create(CalculDefinitionService.class),
				retrofit.create(CalculService.class),
				retrofit.create(ResetService.class));
	}
	
	public Checker(ValueService serviceValue, DataSetService serviceDataSet, CalculDefinitionService serviceCalculDefinition, CalculService serviceCalcul, ResetService serviceReset) {
		super();
		this.serviceValue = serviceValue;
		this.serviceDataSet = serviceDataSet;
		this.serviceCalculDefinition = serviceCalculDefinition;
		this.serviceCalcul = serviceCalcul;
		this.serviceReset = serviceReset;
	}
	
	public CheckResponse checkReset() {
		reset();
		return CheckResponse.ok();
	}
	
	public CheckResponse checkAddition() {
		return check(this::tryAddition, 3);
	}

	public CheckResponse checkMultiplication() {
		return check(this::tryMultiplication, 6);
	}

	public CheckResponse checkCondition() {
		return check(this::tryCondition, 2);
	}

	public CheckResponse checkCondition2() {
		return check(this::tryDoubleCondition, 3);
	}

	public CheckResponse checkCondition3() {
		return check(this::tryTripleCondition, 5);
	}
	
	public void checkRuns() throws IOException {
		reset();
		System.out.println(tryCondition() + " Should be 2");

		reset();
		System.out.println(tryAddition() + " Should be 3");
		
		reset();
		System.out.println(tryDoubleCondition() + " Should be 3");

		reset();
		System.out.println(tryTripleCondition() + " Should be 5");
	}

	private CheckResponse check(Supplier<Integer> s, int expected) {
		reset();
		Integer res = s.get();
		if (res == expected) {
			return CheckResponse.ok();
		}
		else {
			return CheckResponse.fail("expected " + expected);
		}
	}

	public Integer tryTripleCondition() {
		sendValue("A", 1);
		sendValue("B", 2);
		sendValue("C", 3);
		defineDataSet("Datas", List.of("A", "B", "C"));
		defineCalcul("Calcul", List.of(OperationType.MULTIPLICATION, OperationType.ADDITION));
		Optional<Integer> result = execute("Calcul", "Datas");
		return result.get();
	}

	public Integer tryDoubleCondition() {
		sendValue("A", 1);
		sendValue("B", -1);
		sendValue("C", 1);
		sendValue("D", 2);
		sendValue("E", 3);
		defineDataSet("Datas", List.of("A", "B", "C", "D", "E"));
		defineCalcul("Calcul", List.of(OperationType.CONDITION, OperationType.CONDITION));
		Optional<Integer> result = execute("Calcul", "Datas");
		return result.get();
	}

	public Integer tryCondition() {
		sendValue("A", 1);
		sendValue("B", 2);
		sendValue("C", 3);
		defineDataSet("Datas", List.of("A", "B", "C"));
		defineCalcul("Calcul", List.of(OperationType.CONDITION));
		Optional<Integer> result = execute("Calcul", "Datas");
		return result.get();
	}

	public Integer tryAddition() {
		sendValue("A", 1);
		sendValue("B", 2);
		defineDataSet("Datas", List.of("A", "B"));
		defineCalcul("Calcul", List.of(OperationType.ADDITION));
		Optional<Integer> result = execute("Calcul", "Datas");
		return result.get();
	}

	public Integer tryMultiplication() {
		sendValue("A", 2);
		sendValue("B", 3);
		defineDataSet("Datas", List.of("A", "B"));
		defineCalcul("Calcul", List.of(OperationType.MULTIPLICATION));
		Optional<Integer> result = execute("Calcul", "Datas");
		return result.get();
	}

	public Optional<Integer> execute(String calcul, String dataset) {
		try {
			Call<CalculResponse> execute = serviceCalcul.execute(calcul, dataset);
			Response<CalculResponse> response = execute.execute();
			if (response.isSuccessful()) {
				return Optional.of(response.body().getValue());
			}
			return Optional.empty();
		} catch (IOException e) {
			throw new RuntimeException("Unable to execute calcul", e);
		}
	}

	public void defineCalcul(String name, List<OperationType> operations) {
		try {
			Calcul definition = new Calcul();
			definition.setName(name);
			definition.setOperations(operations);
			Call<Calcul> defineCalcul = serviceCalculDefinition.defineCalcul(definition);
			Response<Calcul> execute = defineCalcul.execute();
			if (execute.isSuccessful()) {
				System.out.println(execute.body().getName());
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to define calcul", e);
		}
	}

	public void defineDataSet(String name, List<String> liste) {
		try {
			DataSet ds = new DataSet();
			ds.setName(name);
			ds.setValues(liste);
			Call<DataSet> defineDataSet = serviceDataSet.defineDataSet(ds);
			Response<DataSet> execute = defineDataSet.execute();
			if (execute.isSuccessful()) {
				System.out.println(execute.body().getName());
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to defineDataset", e);
		}
	}

	public void sendValue(String name, int val) {
		try {
			Value value = new Value();
			value.setName(name);
			value.setValue(val);
			Call<Value> v = serviceValue.addValue(value );
			Response<Value> executeV = v.execute();
			if (executeV.isSuccessful()) {
				System.out.println(executeV.body().getName());
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to sendValue", e);
		}
	}
	
	private void reset() {
		try {
			Call<?> reset = serviceReset.reset();
			reset.execute();
		} catch (Exception e) {
			throw new RuntimeException("Unable to reset", e);
		}
	}

}
