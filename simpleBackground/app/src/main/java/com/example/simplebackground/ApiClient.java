package com.example.simplebackground;

public class ApiCliet {
    private static final String url = "https://gorest.co.in/public/v2/";

    private static Retrofit retrofit;
    public static ApiInterface getAPI() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit.create(ApiInterface.class);
    }
}

public interface ApiInterface {
    @GET("users")
    Call<ArrayList<User>> getAllUsers();
}
