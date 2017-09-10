package com.syzible.loinniradminconsole;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.syzible.loinniradminconsole.helpers.JSONUtils;
import com.syzible.loinniradminconsole.helpers.LocalPrefs;
import com.syzible.loinniradminconsole.networking.Endpoints;
import com.syzible.loinniradminconsole.networking.RestClient;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);

        setLoginListener();

        if (LocalPrefs.isAuthenticated(this))
            startMainActivity(this);
    }

    private void setLoginListener() {
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameView.getText().toString().trim();
                final String secret = passwordView.getText().toString().trim();

                if (validateFields(username, secret)) {
                    final Context context = LoginActivity.this;
                    JSONObject payload = JSONUtils.getAuthPayload(context, username, secret);
                    RestClient.post(context, Endpoints.AUTHENTICATE, payload, new BaseJsonHttpResponseHandler<JSONObject>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                            LocalPrefs.login(context, username, secret);
                            startMainActivity(context);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONObject errorResponse) {
                            System.out.println(rawJsonData);
                        }

                        @Override
                        protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return new JSONObject(rawJsonData);
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Cuireadh sonraí míchearta isteach. Bain triail as arís.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        LoginActivity.this.finish();
    }

    private static boolean validateFields(String username, String secret) {
        return username.length() > 0 && secret.length() > 0;
    }
}

