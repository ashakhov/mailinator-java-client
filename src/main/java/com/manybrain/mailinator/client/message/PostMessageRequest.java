package com.manybrain.mailinator.client.message;

import static com.manybrain.mailinator.client.JerseyClient.CLIENT;
import static com.manybrain.mailinator.client.Utils.emptyIfNull;
import static jakarta.ws.rs.core.HttpHeaders.AUTHORIZATION;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import com.manybrain.mailinator.client.Request;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostMessageRequest implements Request<PostedMessage> {

  private static final String URL =
      "https://api.mailinator.com/v2/domains/{domain}/inboxes/{inbox}/messages";

  private static final WebTarget WEB_TARGET = CLIENT.target(URL);

  @NonNull
  private final String domain;
  @NonNull
  private final String inbox;
  @NonNull
  private final MessageToPost message;

  @Override
  public PostedMessage execute(String apiToken) {
    WebTarget webTarget = WEB_TARGET.resolveTemplate("domain", emptyIfNull(domain))
        .resolveTemplate("inbox", emptyIfNull(inbox));

    return webTarget.request(MediaType.APPLICATION_JSON_TYPE).header(AUTHORIZATION, apiToken)
        .post(Entity.json(message), PostedMessage.class);
  }


}
