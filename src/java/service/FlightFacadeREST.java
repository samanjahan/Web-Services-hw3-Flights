/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Flight;
import java.util.*;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author syst3m
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Path("/flight")
public class FlightFacadeREST extends AbstractFacade<Flight> {
    @PersistenceContext(unitName = "Web-Services-hw3-FlightsPU")
    private EntityManager em;
    

    public FlightFacadeREST() {
        super(Flight.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Flight entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Flight entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Flight find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Flight> findAll() {
       return super.findAll();
    }
      
    @GET
    @Path("/findAllFlights/departure={departure}&destination={destination}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Flight> findAllFlights(@PathParam("departure")String departure,@PathParam("destination") String destination ){
      //int id = 1;
      //  StringBuilder st = new StringBuilder();
      TypedQuery<Flight> queryDeparture = em.createNamedQuery("Flight.findByDeparture",Flight.class);        
      List<Flight> finalDest = new ArrayList<Flight>();
      List<Flight> finalList = new ArrayList<Flight>();
      queryDeparture.setParameter("departure", departure);
      List<Flight> listdeparture = (queryDeparture.getResultList());
      TypedQuery<Flight> queryDestination = em.createNamedQuery("Flight.findByDeparture",Flight.class);
      for(int i = 0 ; i< listdeparture.size(); ++i){
        if(!(listdeparture.get(i).getDestination().equals(destination))){
            queryDestination.setParameter("departure", listdeparture.get(i).getDestination());
            List<Flight> listDestination = (queryDestination.getResultList());
            for(int  k= 0 ; k < listDestination.size(); ++k){
                if(listDestination.get(k).getDestination().equals(destination)){
                    finalDest.add(listDestination.get(k));
                }
            }
        }
      }
      for(int i = 0 ; i < listdeparture.size(); ++i){
          if(listdeparture.get(i).getDestination().equals(destination)){
        //      st.append(id + ":" + listdeparture.get(i).getDeparture() + ":" + listdeparture.get(i).getDestination() +":" +
        //              listdeparture.get(i).getFlightNr() + ":" + listdeparture.get(i).getPrice());
        //      st.append("\n");
       //       ++id;
              finalList.add(listdeparture.get(i));
          }else{
              for(int k = 0 ; k < finalDest.size();++k){
                  if(finalDest.get(k).getDeparture().equals(listdeparture.get(i).getDestination())){
         //             st.append(id + ":" +listdeparture.get(i).getDeparture() + ":" + listdeparture.get(i).getDestination() + ":" +
            //                  listdeparture.get(i).getFlightNr() + ":" + listdeparture.get(i).getPrice() + finalDest.get(k).getDeparture() + ":" +
            //                  finalDest.get(k).getDestination() + ":" + finalDest.get(k).getFlightNr() + ":" + finalDest.get(k).getPrice());
                      finalList.add(listdeparture.get(i));
                      finalList.add(finalDest.get(k));
           //           ++id;
                  }
              }
          }
      }
         return finalList;
    //  return st.toString();
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Flight> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
