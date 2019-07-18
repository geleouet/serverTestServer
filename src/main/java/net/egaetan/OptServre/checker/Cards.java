package net.egaetan.OptServre.checker;

import static net.egaetan.OptServre.server.CardDefinition.create;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.egaetan.OptServre.server.CardDefinition;
import net.egaetan.OptServre.server.CheckResponse;
import net.egaetan.OptServre.server.CheckTest;

public enum Cards {
	
	Connection("Connection", "connection", Checker::checkReset),
	Addition("Addition", "addition", Checker::checkAddition),
	Multiplication("Multiplication", "multiplication", Checker::checkMultiplication),
	Condition("Condition", "condition", Checker::checkCondition),
	Condition2("Double Condition", "condition2", Checker::checkCondition2),
	Condition3("Triple Condition", "condition3", Checker::checkCondition3),
	;
	
	String title;
	String url;
	Function<Checker, CheckResponse> test;
	
	public static List<CardDefinition> all() {
		return Arrays.asList(Cards.values()).stream().map(Cards::toDefinition).collect(Collectors.toList());
	}

	private Cards(String title, String url, Function<Checker, CheckResponse> test) {
		this.title = title;
		this.url = url;
		this.test = test;
	}

	private CardDefinition toDefinition() {
		return create(title, url, test());
	}

	private CheckTest test() {
		return url -> test.apply(Checker.create(url));
	}

}
