package it.ariadne.BookingManagement;

import it.ariadne.BookingManagement.resorces.Car;
import it.ariadne.BookingManagement.resorces.Projector;

public class ResourceFactory {
	//use getShape method to get object of type shape 
	   public Resource getResource(String ResourceType){
	      if(ResourceType == null){
	         return null;
	      }		
	      if(ResourceType.equalsIgnoreCase("PROJECTOR")){
	         return new Projector();
	         
	      } else if(ResourceType.equalsIgnoreCase("CAR")){
	         return new Car();
	         
	      } 
	      
	      return null;
	   }

}
