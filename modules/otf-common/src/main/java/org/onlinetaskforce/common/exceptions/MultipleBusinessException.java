package org.onlinetaskforce.common.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is initially created from a corresponding class of the HP Techstack.
 * MultipleBusinessException is a collection of BusinessException
 */
public class MultipleBusinessException extends Exception {

    private static final long serialVersionUID = -5118546668268017412L;

    private List<BusinessException> businessExceptions;
    private List<BusinessException> acceptedBusinessExceptions;

    /**
     * default constructor
     */
    public MultipleBusinessException() {
        super();
        businessExceptions = new ArrayList<BusinessException>();
        acceptedBusinessExceptions = new ArrayList<BusinessException>();
    }

    /**
     * adds the given BusinessException to the collection
     *
     * @param be the BusinessException to add
     * @return true if the businessexception was added otherwise false
     * @throws IllegalArgumentException if the argument is null
     */
    public boolean addBusinessException(BusinessException be) throws IllegalArgumentException {
        if (be == null) {
            throw new IllegalArgumentException("BusinessException cannot be null");
        }
        return businessExceptions.add(be);
    }

    /**
     * accepts the given BusinessException to the accepted businessexception collection
     *
     * @param be the BusinessException to accept
     * @return true if the businessexception could be accepted otherwise false
     * @throws IllegalArgumentException if the argument is null
     */
    public boolean acceptBusinessException(BusinessException be) throws IllegalArgumentException {
        if (be == null) {
            throw new IllegalArgumentException("BusinessException cannot be null");
        }
        if (!existsBusinessException(be)) {
            throw new SystemException("you cannot accept a businessexception that is not registered");
        }
        return acceptedBusinessExceptions.add(be);
    }

    /**
     * Checks if the given businessexception exists
     *
     * @param be the BusinessException to evaluate
     * @return @see description
     */
    private boolean existsBusinessException(BusinessException be) {
        boolean bussinessexceptionFound = false;
        for (BusinessException businessException : businessExceptions) {
            if (businessException.equals(be)) {
                bussinessexceptionFound = true;
            }
        }
        return bussinessexceptionFound;
    }

    /**
     * accepts all the business exception
     *
     * @return true if all businessExceptions are accepted otherwise false
     */
    public boolean acceptAllbusinessExceptions() {
        acceptedBusinessExceptions.clear();
        return acceptedBusinessExceptions.addAll(businessExceptions);
    }

    /**
     * Loops over all the acceptedBusinessExceptions and checks if the given businessexceptionKey matches to one of
     * the acceptedBusinessException key
     *
     * @param businessexceptionKey the businessexceptionKey to check if it is accepted
     * @return @see description
     */
    public boolean isBusinessexceptionAccepted(String businessexceptionKey) {
        for (BusinessException acceptedBusinessException : acceptedBusinessExceptions) {
            if (acceptedBusinessException.getMessageKey().equals(businessexceptionKey)) {
                return true;
            }
        }
        return false;
    }

    /**
     * rejects the given businessexception. return true if the businessexception was found as accepted and could be
     * successfully be rejected
     *
     * @param be the BusinessException to be rejected
     * @return @see description
     * @throws IllegalArgumentException if the argument is null
     */
    public boolean rejectBusinessexception(BusinessException be) throws IllegalArgumentException {
        return acceptedBusinessExceptions.remove(be);
    }

    /**
     * tries to remove the given businessExceptions from the list
     * if the businessexception was accepted it will also be not accepted anymore
     *
     * @param be the BusinessException to remove
     * @return true if remove succeeded otherwise false
     */
    public boolean removeBusinessException(BusinessException be) {
        acceptedBusinessExceptions.remove(businessExceptions);
        return businessExceptions.remove(be);
    }

    /**
     * removes all BusinessException that were present in the list
     * as a result there will also be no more accepted businessexceptions
     */
    public void removeAllBusinessException() {
        acceptedBusinessExceptions.clear();
        businessExceptions.clear();
    }

    /**
     * return true if the number of acceptedBusinessExceptions differes from the number of businessExceptions
     *
     * @return @see description
     */
    public boolean hasUnacceptedBusinessExceptions() {
        return acceptedBusinessExceptions.size() != businessExceptions.size();
    }

    /**
     * returns a List<String> containing all the businessexceptionkeys or an empty list
     *
     * @return @see description
     */
    public List<String> retrieveAllBusinessKeys() {
        List<String> allBusinessKeys = new ArrayList<String>();

        for (BusinessException businessException : businessExceptions) {
            allBusinessKeys.add(businessException.getMessageKey());
        }
        return allBusinessKeys;
    }

    public List<BusinessException> getBusinessExceptions() {
        return businessExceptions;
    }

    public void setBusinessExceptions(List<BusinessException> businessExceptions) {
        this.businessExceptions = businessExceptions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        List<String> keys = retrieveAllBusinessKeys();
        boolean addComma = false;
        for (String key : keys) {
            if (addComma) {
                result.append(", ");
            } else {
                addComma = true;
            }
            result.append(key);
        }
        return result.toString();
    }
}

