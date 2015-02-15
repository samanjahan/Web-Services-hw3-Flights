/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author syst3m
 */
public class Test {
    @PersistenceContext(unitName = "Web-Services-hw3-FlightsPU")
    private EntityManager em;
    
}
