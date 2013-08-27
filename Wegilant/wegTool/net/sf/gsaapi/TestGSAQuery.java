/********************************************************************************
 *  
 *  Product: GSA-JAPI
 *  Description: A Java API for programmatically accessing the Google Search
 *               Appliance.
 *
 *  (c) Copyright 2006 Inxight Software, Inc.
 *  
 *  Licensed under the Inxight Software, Inc., GSA-JAPI License (the "License").
 *  You may not use this file except in compliance with the License. You should
 *  have received a copy of the License with this distribution. If not, you may
 *  obtain a copy by contacting:
 *
 *      Inxight Software, Inc.
 *      500 Macara Ave.
 *      Sunnyvale, CA 94085
 *
 *  Unless required by applicable law or agreed to in writing, software distributed
 *  under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *  CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations under the License.
 ********************************************************************************/
package net.sf.gsaapi;

import net.sf.gsaapi.constants.Access;
import net.sf.gsaapi.constants.Filter;
import net.sf.gsaapi.constants.OutputFormat;
import net.sf.gsaapi.constants.SearchScope;
import junit.framework.TestCase;

/**
 * simple tests to see that the underlying query is 
 * formed correctly
 * @author Amol S Deshmukh
 *
 */
public class TestGSAQuery extends TestCase {


    public void testAccess() {
        GSAQuery query = new GSAQuery();
        query.setAccess(Access.ALL);
        String value = query.getValue();
        assertTrue(value.indexOf("access=a") >= 0);
        assertTrue(value.indexOf("access=s") < 0);
        assertTrue(value.indexOf("access=p") < 0);

        query.setAccess(Access.PUBLIC);
        value = query.getValue();
        assertTrue(value.indexOf("access=a") < 0);
        assertTrue(value.indexOf("access=s") < 0);
        assertTrue(value.indexOf("access=p") >= 0);

        query.setAccess(Access.SECURE);
        value = query.getValue();
        assertTrue(value.indexOf("access=a") < 0);
        assertTrue(value.indexOf("access=s") >= 0);
        assertTrue(value.indexOf("access=p") < 0);

    }
    public void testSearchScope() {
        GSAQuery query = new GSAQuery();
        String value = null;
        query.setSearchScope(SearchScope.ENTIRE_PAGE);
        value = query.getValue();
        assertTrue(value.indexOf("as_occt=any") >= 0);
        assertTrue(value.indexOf("as_occt=title") < 0);
        assertTrue(value.indexOf("as_occt=url") < 0);

        query.setSearchScope(SearchScope.URL);
        value = query.getValue();
        assertTrue(value.indexOf("as_occt=any") < 0);
        assertTrue(value.indexOf("as_occt=title") < 0);
        assertTrue(value.indexOf("as_occt=url") >= 0);

        query.setSearchScope(SearchScope.TITLE);
        value = query.getValue();
        assertTrue(value.indexOf("as_occt=any") < 0);
        assertTrue(value.indexOf("as_occt=title") >= 0);
        assertTrue(value.indexOf("as_occt=url") < 0);

    }
    public void testFilter() {
        GSAQuery query = new GSAQuery();
        String value = null;
        query.setFilter(Filter.FULL_FILTER);
        value = query.getValue();
        assertTrue(value.indexOf("filter=1") >= 0);
        assertTrue(value.indexOf("filter=p") < 0);
        assertTrue(value.indexOf("filter=s") < 0);
        assertTrue(value.indexOf("filter=0") < 0);

        query.setFilter(Filter.DUPLICATE_DIRECTORY_FILTER);
        value = query.getValue();
        assertTrue(value.indexOf("filter=1") < 0);
        assertTrue(value.indexOf("filter=p") >= 0);
        assertTrue(value.indexOf("filter=s") < 0);
        assertTrue(value.indexOf("filter=0") < 0);

        query.setFilter(Filter.DUPLICATE_SNIPPET_FILTER);
        value = query.getValue();
        assertTrue(value.indexOf("filter=1") < 0);
        assertTrue(value.indexOf("filter=p") < 0);
        assertTrue(value.indexOf("filter=s") >= 0);
        assertTrue(value.indexOf("filter=0") < 0);

        query.setFilter(Filter.NO_FILTER);
        value = query.getValue();
        assertTrue(value.indexOf("filter=1") < 0);
        assertTrue(value.indexOf("filter=p") < 0);
        assertTrue(value.indexOf("filter=s") < 0);
        assertTrue(value.indexOf("filter=0") >= 0);

    }
    public void testOutputFormat() {
        GSAQuery query = new GSAQuery();
        String value = null;
        query.setOutputFormat(OutputFormat.XML_NO_DTD);
        value = query.getValue();
        assertTrue(value.indexOf("output=xml_no_dtd") >= 0);
        assertTrue(value.indexOf("output=xml&") < 0);
        assertTrue(value.indexOf("output=xml") != value.length()-10);

        query.setOutputFormat(OutputFormat.XML);
        value = query.getValue();
        assertTrue(value.indexOf("output=xml_no_dtd") < 0);
        assertTrue(value.indexOf("output=xml") >= 0);
    }
    public void testMaxResults() {
        GSAQuery query = new GSAQuery();
        String value = null;
        query.setMaxResults(100);
        value = query.getValue();
        assertTrue(value.indexOf("num=100") >= 0);
    }
}
