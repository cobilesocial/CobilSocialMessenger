package com.quickblox.socialmessenger.core.interfaces;

import com.quickblox.socialmessenger.core.models.DialogResponse;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Path;

/**
 * Created by andrewconcepcion on 8/19/15.
 */
public interface DialogsInterface {
    @DELETE("/chat/Dialog/{id}")
    void deleteDialog(
        @Path("id") String dialogId,
        Callback<DialogResponse> response
    );
}
