package org.sharding.router;


import java.util.Collection;

/**
 * 
 * @author wenlong.liu
 *
 */
public interface Router {

	/**
	 * 
	 * @return
	 */
	Collection<RouteUnit> route();
}
