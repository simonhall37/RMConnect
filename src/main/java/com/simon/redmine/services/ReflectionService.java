package com.simon.redmine.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionService {

	public ReflectionService() {}
	
	
	public Object getField(Object parent, String path) throws IllegalArgumentException {
		
		String[] parts = path.split("\\.");
		Object current = parent;
		for (String p : parts) {
			
			try {
				current = getLocalField(current, p);
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new IllegalArgumentException("Could not access " + p + " from " + current.getClass().getName() + " - " + e.getMessage() + " --- " + e.getCause());
			} catch (NullPointerException | NoSuchMethodException e) {
				return null;
			}
		}
		
		return current;
	}
	
	
	
	public Object getLocalField(Object parent, String name) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
			name = "get" + name;		
			Method toInvoke = parent.getClass().getDeclaredMethod(name, new Class[] {});
			
			return toInvoke.invoke(parent, new Object[] {});

	}
	
}
