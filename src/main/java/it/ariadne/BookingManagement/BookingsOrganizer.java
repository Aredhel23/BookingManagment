package it.ariadne.BookingManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import it.ariadne.BookingManagement.people.User;

public class BookingsOrganizer {
	private Map<Resource, List> organizer= new LinkedHashMap<>();

	public BookingsOrganizer() {
		
	}

	public Map<Resource, List> getOrganizer() {		
		return organizer;
	}
	
	public boolean bookingRequest(Resource r, DateTime start, DateTime end) {

		List<Booking> bookings = organizer.get(r);
		Interval interval = new Interval(start, end);
		for (Booking i:bookings) {
			if(i.getInterval().overlaps(interval)) {
				return false;
			}
		}
		return true;		 
	}
	
	public int addBooking(Person u, Resource r, String name, DateTime start, DateTime end) {
		
    	List<Booking> bookings = organizer.get(r);
		if(bookingRequest(r, start, end)) {
			Interval interval = new Interval(start, end);
			Booking p = new Booking(u, name, interval);
			bookings.add(p);
			return 0;
		}
		else 
			return 1;
	}
	
	public int deleteBooking(Resource r, String name, DateTime start, DateTime end) {	
		
    	List<Booking> bookings = organizer.get(r);
		for (Booking b:bookings) {
			if(b.getName().equals(name))
				if(b.getI().getStart().equals(start) && b.getI().getEnd().equals(end)) {
					bookings.remove(b);
					return 0;
				}
		}
		return 1;
	}
	
	public DateTime firstAvailability(Resource r, Period p) {
    	boolean ret = true;
    	DateTime start = new DateTime();
    	Period defPeriod = new Period().withHours(1);
    	while(ret) {
    		DateTime end = start.plus(p); 
    		if (bookingRequest(r, start, end))
    			return start;
    		ret = !bookingRequest(r, start, end);
    		start = start.plus(defPeriod);
    		
    	}
		return null;
	}
	
	public List<DateTime> firstAvailability(Resource r, Period p, DateTime start, DateTime end) {
		List<DateTime> dates = new ArrayList<>();
    	boolean ret = true;
    	Period defPeriod = new Period().withHours(1);
    	DateTime start1 = start;
    	while(ret) {
    		DateTime end1 = start1.plus(p); 
    		if (end1.isBefore(end)) {
	    		if (bookingRequest(r, start1, end1)) {
	    			dates.add(start1);
	    			dates.add(end1);
	    			return dates;
	    		}
	    		ret = !bookingRequest(r, start1, end1);
	    		start1 = start1.plus(defPeriod);
    		}
    		else {
    			return Collections.emptyList();
    		}
    		
    	}
			    
			    
		 
		return  Collections.emptyList();
	}
	
	public List<DateTime> firstAvailability(String string, Period p, int limit) {
		Iterator it = organizer.entrySet().iterator();
		List<DateTime> dates = new ArrayList<>();
		 while (it.hasNext()) {
			    Map.Entry entry = (Map.Entry)it.next();
			    Resource r = ((Resource) entry.getKey());
			    if((r.getType()).equals(string) && r.getLimit() >= limit) {
			    	DateTime start = firstAvailability((Resource)entry.getKey(), p);
			    	DateTime end = start.plus(p);
		    		dates.add(start);
		    		dates.add(end);
			    	return dates;	
			    	}
			    }		 
		return  Collections.emptyList();
	}


	
}

