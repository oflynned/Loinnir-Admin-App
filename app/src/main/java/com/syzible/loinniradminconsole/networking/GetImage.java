package com.syzible.loinniradminconsole.networking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ed on 05/10/2017.
 */

public class GetImage extends GetRequest<Bitmap> {
    public GetImage(NetworkCallback<Bitmap> networkCallback, String url) {
        super(networkCallback, url);
    }

    @Override
    public Bitmap transferData() {
        try {
            switch (getConnection().getResponseCode()) {
                case 200:
                case 304:
                    InputStream is = getConnection().getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    if (bitmap != null)
                        return bitmap;
                case 404:
                case 500:
                    break;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
