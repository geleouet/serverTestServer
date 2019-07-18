package net.egaetan.OptServre.checker.api;

import retrofit2.Call;
import retrofit2.http.DELETE;

public interface ResetService {

	
	@DELETE("/reset")
	Call<Void> reset();
	
}
