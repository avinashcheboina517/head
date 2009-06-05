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

package org.mifos.framework.components.tabletag;

import org.mifos.framework.MifosIntegrationTest;
import org.mifos.framework.exceptions.ApplicationException;
import org.mifos.framework.exceptions.SystemException;
import org.mifos.framework.exceptions.TableTagException;
import org.mifos.framework.security.util.UserContext;
import org.mifos.framework.util.helpers.TestObjectFactory;

public class TableTagExceptionIntegrationTest extends MifosIntegrationTest {

    public TableTagExceptionIntegrationTest() throws SystemException, ApplicationException {
        super();
    }

    public void testTableTagException() throws Exception {
        try {
            UserContext userContext = TestObjectFactory.getContext();
            Text.getImage(this, "name", userContext.getPreferredLocale());
            fail();
        } catch (TableTagException tte) {
            assertEquals("exception.framework.TableTagException", tte.getKey());
        }
    }
}
