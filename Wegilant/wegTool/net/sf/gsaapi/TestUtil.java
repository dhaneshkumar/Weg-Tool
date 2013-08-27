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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.gsaapi.util.Util;

import junit.framework.TestCase;

public class TestUtil extends TestCase {
	
	public void testAppendMappedQueryParams() {
		Map map = new TreeMap();
		map.put("a", "abc");
		map.put("b", "bcd");
		map.put("c", "cde");
		map.put("d", "def");
		
		StringBuffer sbuf = new StringBuffer();
		Util.appendMappedQueryParams(sbuf, "param", map);
		Util.appendMappedQueryParams(sbuf, "param", map);
		assertEquals("", "param=a%3Aabcb%3Abcdc%3Acded%3Adef&param=a%3Aabcb%3Abcdc%3Acded%3Adef", sbuf.toString());
	}
	
	public void testAppendQueryParam1() {
		StringBuffer sbuf = new StringBuffer();
		Util.appendQueryParam(sbuf, "param1", "value1");
		assertEquals("", "param1=value1", sbuf.toString());
		Util.appendQueryParam(sbuf, "param2", "val&ue2");
		assertEquals("", "param1=value1&param2=val%26ue2", sbuf.toString());
	}

	public void testAppendQueryParam2() {
		StringBuffer sbuf = new StringBuffer();
		Util.appendQueryParam(sbuf, "param1", new String[]{"value1", "value2"});
		assertEquals("", "param1=value1&param1=value2", sbuf.toString());
		Util.appendQueryParam(sbuf, "param2", "val&ue2");
		assertEquals("", "param1=value1&param1=value2&param2=val%26ue2", sbuf.toString());
		Util.appendQueryParam(sbuf, "param3", new String[]{"", "value3"});
		assertEquals("", "param1=value1&param1=value2&param2=val%26ue2&param3=&param3=value3", sbuf.toString());
	}

	public void testEncode() {
		String s = Util.encode("abc&\"-~!@#$%^&*()-_+=def");
		assertEquals("", "abc%26%22-%7E%21%40%23%24%25%5E%26*%28%29-_%2B%3Ddef", s);
	}

	public void testStringSeparated1() {
		List tokens = Arrays.asList(new String[]{"tok1", "tok2", "tok3"});
		String prefix = "pre";
		String separator = "SEP";
		String s = Util.stringSeparated(tokens, prefix, separator);
		assertEquals("", "pretok1SEPpretok2SEPpretok3", s);
	}

	public void testStringSeparated2() {
		String[] tokens = new String[]{"tok1", "tok2", "tok3"};
		String prefix = "pre";
		String separator = "SEP";
		String s = Util.stringSeparated(tokens, prefix, separator);
		assertEquals("", "pretok1SEPpretok2SEPpretok3", s);
	}

	public void testToHtmlCode() {
		String code = Util.toHtmlCode('&');
		assertEquals("", "&#38;", code);
		code = Util.toHtmlCode('!');
		assertEquals("", "&#33;", code);
		code = Util.toHtmlCode('@');
		assertEquals("", "&#64;", code);
		code = Util.toHtmlCode('#');
		assertEquals("", "&#35;", code);
		code = Util.toHtmlCode('$');
		assertEquals("", "&#36;", code);
		code = Util.toHtmlCode('%');
		assertEquals("", "&#37;", code);
		code = Util.toHtmlCode('^');
		assertEquals("", "&#94;", code);
		code = Util.toHtmlCode('*');
		assertEquals("", "&#42;", code);
	}

}
