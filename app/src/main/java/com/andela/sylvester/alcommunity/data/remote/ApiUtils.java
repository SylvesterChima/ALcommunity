package com.andela.sylvester.alcommunity.data.remote;

/**
 * Created by Sylvester on 02/05/2017.
 */
public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://letshelpworld.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
