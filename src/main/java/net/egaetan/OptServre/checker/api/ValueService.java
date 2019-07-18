package net.egaetan.OptServre.checker.api;

import net.egaetan.OptServre.model.Value;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ValueService {

	@POST("/value/")
	Call<Value> addValue(@Body Value value);
	
}
