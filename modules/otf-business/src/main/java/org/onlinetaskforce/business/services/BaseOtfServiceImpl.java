package org.onlinetaskforce.business.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.onlinetaskforce.business.handlers.BaseExceptionHandler;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.exceptions.SystemException;
import org.onlinetaskforce.common.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Base class for al Service implementations of the otf project.
 *
 * @author jordens
 * @since 15/03/13
 */
public class BaseOtfServiceImpl {

    private AbstractPlatformTransactionManager otfTransactionManager;
    private BaseExceptionHandler baseExceptionHandler;
    /**
     * The session factory that is used for new transactions.
     */
    private SessionFactory sessionFactory;


    /**
     * Execute the logic implemented in the given TransactionCallback in a new transaction.
     * Not that this implementation does NOT throw a BusinessException. All BusinessExceptions will be
     * converted to SystemExceptions.
     * As<code>tc</code> you can:
     * <ul>
     *
     * <li>extend OtfTxCallback </li>
     * <li>extend OtfTxCallbackWithoutResult</li>
     * <li>implement TransactionCallback</li>
     * <li>extend TransactionCallbackWithoutResult</li>
     * </ul>
     *
     * @param tc        See description.
     * @return The return value of the callback implementation.
     */
    public Object executeInNewTransactionNoBusinessException(TransactionCallback tc) {
        try {
            return executeInNewTransaction(tc);
        } catch (BusinessException e) {
            throw new SystemException(e);
        }
    }

    /**
     * Execute the logic implemented in the given TransactionCallback in a new transaction.
     * As<code>tc</code> you can:
     * <ul>
     *
     * <li>extend OtfTxCallback </li>
     * <li>extend OtfTxCallbackWithoutResult</li>
     * <li>implement TransactionCallback</li>
     * <li>extend TransactionCallbackWithoutResult</li>
     * </ul>
     *
     * @param tc        See description.
     * @return The return value of the callback implementation.
     * @throws BusinessException In case of a functional problem. (In this case, the transaction will be rollbacked)
     */
    public Object executeInNewTransaction(TransactionCallback tc) throws BusinessException {
        /**
         *  Subclass of the TransactionCallback, in order to keep track of the Hibernate Session.
         */
        class WrappingTransactionCallback implements TransactionCallback {

            private Session sessionUsedInTxCallback;
            private TransactionCallback targetTxCallback;

            public WrappingTransactionCallback(TransactionCallback targetTxCallback) {
                this.targetTxCallback = targetTxCallback;
                this.sessionUsedInTxCallback = sessionFactory.openSession();
            }

            public Object doInTransaction(TransactionStatus status) {
                return targetTxCallback.doInTransaction(status);
            }

            public Session getSessionUsedInTxCallback() {
                return sessionUsedInTxCallback;
            }
        }
        // wrap target tc
        WrappingTransactionCallback wtc = new WrappingTransactionCallback(tc);
        // create the tx template and execute in new trx
        TransactionTemplate txTemplate = new TransactionTemplate(otfTransactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        try {
            Log.debug(this, "Start executeInNewTransaction");
            return txTemplate.execute(wtc);
        } catch (Exception e) {
            // We try to cut needless long exception chains
            //noinspection ThrowableResultOfMethodCallIgnored
            Throwable eToRethrow = baseExceptionHandler.getNestedExceptionOfType(BusinessException.class, e);
            if (eToRethrow != null) {
                throw (BusinessException) eToRethrow;
            }
            //noinspection ThrowableResultOfMethodCallIgnored
            eToRethrow = baseExceptionHandler.getNestedExceptionOfType(SystemException.class, e);
            if (eToRethrow != null) {
                throw (SystemException) eToRethrow;
            }
            // Nothing to cut
            baseExceptionHandler.throwAsSystemException(e);
            return null;
        } finally {
            // Close the current session, this way no objects are bound to the session. This to prevent problems with reusing these objects in an other session.
            Session session = wtc.getSessionUsedInTxCallback();
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    // We do not throw this exception in order not to loose the original exception.
                    baseExceptionHandler.handle(e);
                }
            }
            Log.debug(this, "Einde executeInNewTransaction");
        }
    }

    // Dependencies
    protected AbstractPlatformTransactionManager getOtfTransactionManager() {
        return otfTransactionManager;
    }

    @Autowired
    public void setOtfTransactionManager(AbstractPlatformTransactionManager otfTransactionManager) {
        this.otfTransactionManager = otfTransactionManager;
    }

    protected BaseExceptionHandler getBaseExceptionHandler() {
        return baseExceptionHandler;
    }

    @Autowired
    public void setBaseExceptionHandler(BaseExceptionHandler baseExceptionHandler) {
        this.baseExceptionHandler = baseExceptionHandler;
    }

    @Autowired
    @Qualifier("otfSessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
