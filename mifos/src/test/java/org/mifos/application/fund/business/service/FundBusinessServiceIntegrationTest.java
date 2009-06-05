/*
 * Copyright (c) 2005-2009 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.application.fund.business.service;

import java.util.List;

import org.mifos.application.fund.business.FundBO;
import org.mifos.application.master.business.FundCodeEntity;
import org.mifos.framework.MifosIntegrationTest;
import org.mifos.framework.exceptions.ApplicationException;
import org.mifos.framework.exceptions.ServiceException;
import org.mifos.framework.exceptions.SystemException;
import org.mifos.framework.hibernate.helper.StaticHibernateUtil;
import org.mifos.framework.util.helpers.TestObjectFactory;

public class FundBusinessServiceIntegrationTest extends MifosIntegrationTest {

    public FundBusinessServiceIntegrationTest() throws SystemException, ApplicationException {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        StaticHibernateUtil.closeSession();
        super.tearDown();
    }

    public void testGetFundCodesForInvalidConnection() throws Exception {
        TestObjectFactory.simulateInvalidConnection();
        try {
            assertEquals(5, new FundBusinessService().getFundCodes().size());
            assertTrue(false);
        } catch (ServiceException e) {
            assertTrue(true);
        } finally {
            StaticHibernateUtil.closeSession();
        }
    }

    public void testGetFundCodes() throws Exception {
        List<FundCodeEntity> funds = new FundBusinessService().getFundCodes();
        assertEquals(5, funds.size());
    }

    public void testGetSourcesOfFund() throws Exception {
        List<FundBO> funds = new FundBusinessService().getSourcesOfFund();
        assertNotNull(funds);
        assertEquals(5, funds.size());
    }

    public void testGetSourcesOfFundForInvalidConnection() {
        TestObjectFactory.simulateInvalidConnection();
        try {
            new FundBusinessService().getSourcesOfFund();
            assertTrue(false);
        } catch (ServiceException e) {
            assertTrue(true);
        } finally {
            StaticHibernateUtil.closeSession();
        }
    }

    public void testGetFund() throws Exception {
        FundCodeEntity fundCodeEntity = (FundCodeEntity) StaticHibernateUtil.getSessionTL().get(FundCodeEntity.class,
                (short) 1);
        FundBO fund = TestObjectFactory.createFund(fundCodeEntity, "Fund1");
        assertEquals("Fund1", new FundBusinessService().getFund("Fund1").getFundName());
        TestObjectFactory.removeObject(fund);
    }

    public void testGetFundForInvalidConnection() throws Exception {
        FundCodeEntity fundCodeEntity = (FundCodeEntity) StaticHibernateUtil.getSessionTL().get(FundCodeEntity.class,
                (short) 1);
        FundBO fund = TestObjectFactory.createFund(fundCodeEntity, "Fund1");
        TestObjectFactory.simulateInvalidConnection();
        try {
            new FundBusinessService().getFund("Fund1").getFundName();
            assertTrue(false);
        } catch (ServiceException e) {
            assertTrue(true);
        } finally {
            StaticHibernateUtil.closeSession();
        }
        TestObjectFactory.removeObject(fund);
    }

    public void testGetFundById() throws Exception {
        FundCodeEntity fundCodeEntity = (FundCodeEntity) StaticHibernateUtil.getSessionTL().get(FundCodeEntity.class,
                (short) 1);
        FundBO fund = TestObjectFactory.createFund(fundCodeEntity, "Fund1");
        StaticHibernateUtil.closeSession();

        fund = new FundBusinessService().getFund(fund.getFundId());
        assertNotNull(fund);
        assertEquals("Fund1", fund.getFundName());
        assertEquals(1, fund.getFundCode().getFundCodeId().intValue());
        TestObjectFactory.removeObject(fund);
    }

    public void testGetFundByIdForInvalidConnection() throws Exception {
        FundCodeEntity fundCodeEntity = (FundCodeEntity) StaticHibernateUtil.getSessionTL().get(FundCodeEntity.class,
                (short) 1);
        FundBO fund = TestObjectFactory.createFund(fundCodeEntity, "Fund1");
        TestObjectFactory.simulateInvalidConnection();
        try {
            new FundBusinessService().getFund(fund.getFundId());
            assertTrue(false);
        } catch (ServiceException e) {
            assertTrue(true);
        } finally {
            StaticHibernateUtil.closeSession();
        }
        TestObjectFactory.removeObject(fund);
    }
}
