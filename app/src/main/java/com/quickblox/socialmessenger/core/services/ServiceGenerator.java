package com.quickblox.socialmessenger.core.services;

import android.text.TextUtils;

import com.quickblox.core.utils.ConstsCore;
import com.quickblox.core.utils.PrefsHelper;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by andrewconcepcion on 8/19/15.
 */
public class ServiceGenerator {

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, String sessionToken)  {
        RestAdapter adapter = null;

        if(TextUtils.isEmpty(sessionToken)) {
            PrefsHelper helper = PrefsHelper.getPrefsHelper();
            sessionToken = helper.getPref(PrefsHelper.PREF_SESSION_TOKEN, ConstsCore.EMPTY_STRING);
        }


        final String finalSessionToken = sessionToken;
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("QB-Token", finalSessionToken);
            }
        };
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setRequestInterceptor(requestInterceptor)
                .setClient(new OkClient(new OkHttpClient()));

        adapter = builder.build();

        return adapter.create(serviceClass);
    }
}