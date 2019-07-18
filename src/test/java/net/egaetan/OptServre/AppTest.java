package net.egaetan.OptServre;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import net.egaetan.OptServre.checker.Checker;
import net.egaetan.OptServre.checker.api.CalculDefinitionService;
import net.egaetan.OptServre.checker.api.CalculService;
import net.egaetan.OptServre.checker.api.DataSetService;
import net.egaetan.OptServre.checker.api.ResetService;
import net.egaetan.OptServre.checker.api.ValueService;
import net.egaetan.OptServre.model.CalculResponse;
import net.egaetan.OptServre.model.OperationType;

public class AppTest {
	
	@Test
	public void addition() throws IOException {
		// GIVEN
		int expected = 3;
		Checker app = createApp(expected);
		app.sendValue("A", 1);
		app.sendValue("B", 2);
		app.defineDataSet("Datas", List.of("A", "B"));
		app.defineCalcul("Calcul", List.of(OperationType.ADDITION));
		
		// WHEN
		Optional<Integer> execute = app.execute("Calcul", "Dataset");
		
		// THEN
		Assertions.assertThat(execute).isNotEmpty();
		Assertions.assertThat(execute.get()).isEqualTo(expected);
	}

	@Test
	public void condition() throws IOException {
		// GIVEN
		int expected = 2;
		Checker app = createApp(expected);
		app.sendValue("A", 1);
		app.sendValue("B", 2);
		app.sendValue("C", 3);
		app.defineDataSet("Datas", List.of("A", "B", "C"));
		app.defineCalcul("Calcul", List.of(OperationType.CONDITION));
		
		// WHEN
		Optional<Integer> execute = app.execute("Calcul", "Dataset");
		
		// THEN
		Assertions.assertThat(execute).isNotEmpty();
		Assertions.assertThat(execute.get()).isEqualTo(expected);
	}

	@Test
	public void app_condition() throws IOException {
		// GIVEN
		int expected = 2;
		Checker app = createApp(expected);
		
		// WHEN
		int result = app.tryCondition();
		
		// THEN
		Assertions.assertThat(result).isEqualTo(expected);
	}

	private Checker createApp(int expected) {
		ValueService serviceValue = v -> new DummyCall<>(() -> v);
		DataSetService serviceDataSet = v -> new DummyCall<>(() -> v);
		CalculDefinitionService serviceCalculDefinition = v -> new DummyCall<>(() -> v);
		CalculService serviceCalcul = (a, b)  -> new DummyCall<>(() -> new CalculResponse(expected));
		ResetService serviceReset = () -> new DummyCall<>(() -> null);
		return new Checker(serviceValue, serviceDataSet, serviceCalculDefinition, serviceCalcul, serviceReset);
	}
}
