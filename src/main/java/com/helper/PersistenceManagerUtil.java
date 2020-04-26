package com.helper;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public final class PersistenceManagerUtil {
	private static final PersistenceManagerFactory pmfInstance =
	        JDOHelper.getPersistenceManagerFactory("transactions-optional");

	    private PersistenceManagerUtil() {}

	    public static PersistenceManagerFactory get() {
	        return pmfInstance;
	    }
	    public static PersistenceManager getPersistenceManager() {
			PersistenceManager persistenceManager = pmfInstance.getPersistenceManager();
			return persistenceManager;
		}
}
