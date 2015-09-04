package com.quickblox.socialmessenger.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrewconcepcion on 8/19/15.
 */
public class DialogResponse {
    @SerializedName("SuccessfullyDeleted")
    @Expose
    String successfullyDeleted;
    @SerializedName("WrongPermissions")
    @Expose
    String wrongPermissions;
    @SerializedName("NotFound")
    @Expose
    String notFound;

    public String getSuccessfullyDeleted() {
        return successfullyDeleted;
    }

    public void setSuccessfullyDeleted(String successfullyDeleted) {
        this.successfullyDeleted = successfullyDeleted;
    }

    public String getWrongPermissions() {
        return wrongPermissions;
    }

    public void setWrongPermissions(String wrongPermissions) {
        this.wrongPermissions = wrongPermissions;
    }

    public String getNotFound() {
        return notFound;
    }

    public void setNotFound(String notFound) {
        this.notFound = notFound;
    }

    @Override
    public String toString() {
        return "DialogResponse{" +
                "successfullyDeleted='" + successfullyDeleted + '\'' +
                ", wrongPermissions='" + wrongPermissions + '\'' +
                ", notFound='" + notFound + '\'' +
                '}';
    }
}
