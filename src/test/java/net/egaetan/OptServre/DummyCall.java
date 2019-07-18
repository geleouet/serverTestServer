package net.egaetan.OptServre;

import java.io.IOException;
import java.util.function.Supplier;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DummyCall<T> implements Call<T> {

	Supplier<T> response;
	
	public DummyCall(Supplier<T> response) {
		super();
		this.response = response;
	}

	@Override
	public Response<T> execute() throws IOException {
		return Response.success(response.get());
	}

	@Override
	public void enqueue(Callback<T> callback) {
	}

	@Override
	public boolean isExecuted() {
		return true;
	}

	@Override
	public void cancel() {
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public Call<T> clone() {
		return null;
	}

	@Override
	public Request request() {
		return null;
	}

}
