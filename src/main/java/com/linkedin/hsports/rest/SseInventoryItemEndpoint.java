package com.linkedin.hsports.rest;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.Random;

@Singleton
@Path("/sse/inventoryitems")
@Produces("application/json")
@Consumes("application/json")
public class SseInventoryItemEndpoint {

    @GET
    @Path("/{inventoryItemId}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void sseItemQuantity(@PathParam("inventoryItemId") Long inventoryItemId,
                                @Context SseEventSink sseEventSink, @Context Sse sse) {

        try(SseEventSink sink = sseEventSink) {

            for (int x = 0; x < 30; x++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                OutboundSseEvent event = sse.newEvent(Integer.toString(new Random().nextInt(50)));
                sseEventSink.send(event);
            }
        }
    }
}
