package com.linkedin.hsports.rest;

import com.linkedin.hsports.jpa.InventoryItem;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequestScoped
@Path("/inventoryitems")
@Produces("application/json")
@Consumes("application/json")
public class InventoryItemEndpoint {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @POST
    public Response create(final InventoryItem inventoryItem) {
        this.entityManager.persist(inventoryItem);
        return Response
                .created(UriBuilder.fromResource(InventoryItemEndpoint.class)
                .path(String.valueOf(inventoryItem.getInventoryItemId())).build())
                .build();
    }

    @GET
    @Path("/{id: [0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {

        InventoryItem inventoryItem = this.entityManager.find(InventoryItem.class, id);

        if (inventoryItem == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        inventoryItem.setQuantity(ThreadLocalRandom.current().nextLong(1, 100));
        return Response.ok(inventoryItem).build();
    }

//    @GET
//    @Path("/catalog/{catalogItemId}")
//    public void asyncFindByCatalogId(@NotNull @PathParam("catalogItemId") Long catalogItemId,
//                                     @Suspended AsyncResponse asyncResponse) {
//
//       new Thread(() -> {
//           try {
//               Thread.sleep(5000);
//           } catch (InterruptedException e) {
//               throw new RuntimeException(e);
//           }
//           asyncResponse.resume(findByCatalogId(catalogItemId));
//       }).start();
//    }


    @GET
    @Path("/catalog/{catalogItemId}")
    public InventoryItem findByCatalogId(@NotNull @PathParam("catalogItemId") Long catalogItemId) {

        TypedQuery<InventoryItem> query = this.entityManager
                .createQuery("SELECT i FROM InventoryItem i WHERE i.catalogItemId = :catalogItemId", InventoryItem.class)
                .setParameter("catalogItemId", catalogItemId);

        InventoryItem inventoryItem = query.getSingleResult();
        inventoryItem.setQuantity(ThreadLocalRandom.current().nextLong(1, 100));

        return inventoryItem;
    }

    @GET
    public List<InventoryItem> listAll(@QueryParam("start") final Integer startPosition,
                                       @QueryParam("max") final Integer maxResult) {

        TypedQuery<InventoryItem> query = this.entityManager.createQuery("SELECT i FROM  InventoryItem i", InventoryItem.class);
        final List<InventoryItem> inventoryitems = query.getResultList();

        return inventoryitems;
    }

    @Transactional
    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") Long id, final InventoryItem inventoryitem) {

        this.entityManager.merge(inventoryitem);

        return Response.noContent().build();
    }

    @Transactional
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Long id) {

        this.entityManager.remove(this.entityManager.find(InventoryItem.class, id));

        return Response.noContent().build();
    }
}
