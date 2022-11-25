package com.module.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.module.group.dto.GroupAuthorizationInGroupDto;

import java.io.IOException;

public class UserAuthorizationJsonHandler extends StdDeserializer<GroupAuthorizationInGroupDto> {

    public UserAuthorizationJsonHandler(){
        super(GroupAuthorizationInGroupDto.class);
    }

    public UserAuthorizationJsonHandler(Class<?> vc) {
        super(vc);
    }

    public UserAuthorizationJsonHandler(JavaType valueType) {
        super(valueType);
    }

    public UserAuthorizationJsonHandler(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public GroupAuthorizationInGroupDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        String groupName = node.get("groupName").asText();
        String adminNickName = node.get("myNickName").asText();

        return new GroupAuthorizationInGroupDto(groupName,adminNickName);
    }


}
