package com.linkedin.hsports.cdi;

import com.linkedin.hsports.cdi.qualifiers.RemoteService;
import com.linkedin.hsports.jpa.InventoryItem;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Random;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

//@Named("remoteInventoryService")      // Qualifier
@ApplicationScoped
@RemoteService                          // Custom Qualifier
//@Alternative                            // Factory
public class RemoteInventoryService implements InventoryService {

    private String apiUrl = "http://localhost:8080/hsports-catalog/hsports/api/";

    @Override
    public void createItem(Long catalogItemId, String name) {

        Client client = ClientBuilder.newClient();

        Response response = client.target(apiUrl)
                .path("inventoryitems")
                .request()
                .post(Entity.json(new InventoryItem(null, catalogItemId, name, new Random().nextLong(10))));

        System.out.println(response.getStatus());
        System.out.println(response.getLocation().getPath());
    }

    @Override
    public Long getQuantity(Long catalogItemId) {
        Client client = ClientBuilder.newClient();
        InventoryItem inventoryItem =
                client.target(apiUrl).path("inventoryitems").path("catalog").path("{catalogItemId}")
                .resolveTemplate("catalogItemId", catalogItemId.toString())
                .request()
                .get(InventoryItem.class);

        return inventoryItem.getQuantity();
    }

    @Override
    public Future<InventoryItem> asyncGetQuantity(Long catalogItemId) {
        Client client = ClientBuilder.newClient();
        return client.target(apiUrl).path("inventoryitems").path("catalog").path("{catalogItemId}")
                .resolveTemplate("catalogItemId", catalogItemId.toString())
                .request()
                .async()
                .get(InventoryItem.class);
    }

    @Override
    public CompletionStage<InventoryItem> reactiveGetQuantity(Long catalogItemId) {
        Client client = ClientBuilder.newClient();
        return client.target(apiUrl).path("inventoryitems").path("catalog").path("{catalogItemId}")
                .resolveTemplate("catalogItemId", catalogItemId.toString())
                .request()
                .rx()
                .get(InventoryItem.class);
    }
}
