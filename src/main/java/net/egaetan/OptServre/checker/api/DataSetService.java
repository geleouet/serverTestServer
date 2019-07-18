package net.egaetan.OptServre.checker.api;

import net.egaetan.OptServre.model.DataSet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DataSetService {

	@POST("/dataSet/")
	Call<DataSet> defineDataSet(@Body DataSet value);

}
