package net.egaetan.OptServre.checker.api;

import net.egaetan.OptServre.model.Calcul;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CalculDefinitionService {

	@POST("/calculDefinition/")
	Call<Calcul> defineCalcul(@Body Calcul value);

}
