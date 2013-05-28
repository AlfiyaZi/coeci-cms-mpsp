/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package gov.medicaid.screening.services.impl;

import gov.medicaid.entities.ChemicalDependencyProgramSearchCriteria;
import gov.medicaid.entities.ProviderProfile;
import gov.medicaid.entities.SearchResult;
import gov.medicaid.screening.dao.ChemicalDependencyProgramDAO;
import gov.medicaid.screening.services.ChemicalDependencyProgramService;
import gov.medicaid.screening.services.ConfigurationException;
import gov.medicaid.screening.services.ParsingException;
import gov.medicaid.screening.services.ServiceException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * This class provides an EJB implementation of the ChemicalDependencyProgramService interface. It
 * is a stateless, remote web service bean.
 *
 * <p>
 * <strong>Thread Safety:</strong> This bean is mutable and not thread-safe as it deals with non-thread-safe
 * entities. However, in the context of being used in a container, it is thread-safe.
 * </p>
 *
 * @author argolite, TCSASSEMBLER
 * @version 1.0
 * @since Organizational Provider Screening External Datasources Services 2
 */
@WebService
@Stateless
@Remote(ChemicalDependencyProgramService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ChemicalDependencyProgramServiceBean extends BaseService implements ChemicalDependencyProgramService {
    /**
     * Represent the name of this class
     */
    private static final String CLASS_NAME = ChemicalDependencyProgramServiceBean.class.getName();
    
    /**
     * Represents the DAO that will back this service. It may have any value. It is fully mutable, but not
     * expected to change after dependency injection.
     */
    @EJB
    private ChemicalDependencyProgramDAO chemicalDependencyProgramDAO;

    /**
     * Empty constructor
     */
    public ChemicalDependencyProgramServiceBean() {
    }

    /**
     * Checks if the container properly initialized the injected fields.
     * 
     * @throws ConfigurationException
     *             if any injected field is null
     */
    @PostConstruct
    protected void init() {
        super.init();
        if (chemicalDependencyProgramDAO == null) {
            throw new ConfigurationException("The chemicalDependencyProgramDAO must be configured.");
        }
    }

    /**
     * This method gets the applicable providers that meet the search criteria. If none available, the search
     * result will be empty.
     * 
     * @param criteria
     *            the search criteria
     * @return the search result with the matched providers
     * @throws IllegalArgumentException
     *             if the criteria is null
     * @throws IllegalArgumentException
     *             if criteria.pageNumber < 0
     * @throws IllegalArgumentException
     *             - if criteria.pageSize < 1 unless criteria.pageNumber <= 0
     * @throws ParsingException
     *             if any parsing errors are encountered
     * @throws ServiceException
     *             for any other exceptions encountered
     */
    @WebMethod
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SearchResult<ProviderProfile> search(ChemicalDependencyProgramSearchCriteria criteria) throws ServiceException {
        String signature = CLASS_NAME + "#search(ChemicalDependencyProgramSearchCriteria criteria)";
        LogUtil.traceEntry(getLog(), signature, new String[] { "criteria" }, new Object[] { criteria });
        try {
            SearchResult<ProviderProfile> results = chemicalDependencyProgramDAO.search(criteria);
            return LogUtil.traceExit(getLog(), signature, results);
        } catch (IllegalArgumentException e) {
            LogUtil.traceError(getLog(), signature, e);
            throw e;
        } catch (ServiceException e) {
            LogUtil.traceError(getLog(), signature, e);
            throw e;
        }
    }
}
