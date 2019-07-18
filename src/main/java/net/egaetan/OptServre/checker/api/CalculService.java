package net.egaetan.OptServre.checker.api;

import net.egaetan.OptServre.model.CalculResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CalculService {

	@GET("calcul/{calcul}/{dataset}")
	Call<CalculResponse> execute(@Path("calcul") String calcul, @Path("dataset") String dataset);
	
}
