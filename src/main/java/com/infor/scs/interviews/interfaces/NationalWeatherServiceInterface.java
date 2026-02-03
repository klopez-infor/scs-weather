package com.infor.scs.interviews.interfaces;

import com.infor.scs.interviews.domain.GlossaryResponse;
import com.infor.scs.interviews.domain.IconsResponse;
import com.infor.scs.interviews.transformers.GlossaryTransformer;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Fetches weather data from the US National Weather Service @ https://www.weather.gov/
 *
 * The complete API is documented and can be interacted with @ https://www.weather.gov/documentation/services-web-api#/
 *
 * Authentication is not required to use this API and only GET commands should be executed against this URL.
 *
 * OkHttp is used for network requests to external APIs, documentation can be found @ https://square.github.io/okhttp/
 */
public class NationalWeatherServiceInterface {

    private static final String ROOT_URL = "https://api.weather.gov/";

    private static final String ENTITY_GLOSSARY = "glossary";
    private static final String ENTITY_ICONS = "icons";

    // TODO: Should be a singleton for all interfaces (https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch04s04.html)
    private static final OkHttpClient httpClient = new OkHttpClient();

    // TODO: Should be a singleton for all interfaces (https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch04s04.html)
    private static final Moshi moshi = new Moshi.Builder().build();

    private static final JsonAdapter<IconsResponse> iconsResponseAdapter = moshi.adapter( IconsResponse.class );
    private static final JsonAdapter<GlossaryResponse> glossaryResponseAdapter = moshi.adapter( GlossaryResponse.class );


    public IconsResponse getIcons() throws IOException {

        // TODO: Is there a Spring solution that we could use instead of depending on OkHttp? Does it matter?
        Request request = new Request.Builder()
                .url( ROOT_URL + ENTITY_ICONS )
                .build();

        Call requestCall = httpClient.newCall( request );
        Response response = requestCall.execute();

        if( response.isSuccessful() ) {
            return iconsResponseAdapter.fromJson( response.body().string() );
        }
        else {
            throw new IOException( "Invalid response from National Weather Service: " + response.message() );
        }
    }

    public GlossaryResponse getGlossary() throws IOException {

        Request request = new Request.Builder()
                .url( ROOT_URL + ENTITY_GLOSSARY )
                .build();

        Call requestCall = httpClient.newCall( request );
        Response response = requestCall.execute();

        if( response.isSuccessful() ) {
            GlossaryResponse glossary = glossaryResponseAdapter.fromJson( response.body().string() );
            GlossaryTransformer.transform( glossary );
            return glossary;
        }
        else {
            throw new IOException( "Invalid response from National Weather Service: " + response.message() );
        }
    }

}
