package com.syzible.loinniradminconsole.networking;

/**
 * Created by ed on 05/10/2017.
 */

public interface NetworkCallback<T> {
    void onResponse(T response);
    void onFailure();
}
