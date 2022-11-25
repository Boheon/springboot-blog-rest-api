package com.module.blog.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter

public enum Permit {
    CONSENT("CONSENT"),
    DENIED("DENIED");

    String permit;

    Permit(String permit){
        this.permit=permit;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Permit getParsePermit(String permit) {
        for (Permit p : Permit.values()) {

            if (p.getPermit().equals(permit)) {
                return p;
            }
        }
        return null;
    }
}
