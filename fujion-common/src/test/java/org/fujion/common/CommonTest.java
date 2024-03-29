/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.common;

import org.fujion.common.DateUtil.TimeUnit;
import org.fujion.common.Version.VersionPart;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class CommonTest {

    private static final String DATE = "21-Nov-1978"; // Reference date

    @Test
    public void testPiece() {
        String text = "pc1^pc2^pc3^^pc5^pc6^^^";
        String delm = "^";
        assertEquals("pc1", StrUtil.piece(text, delm));
        assertEquals("pc2", StrUtil.piece(text, delm, 2));
        assertEquals("pc3^^pc5", StrUtil.piece(text, delm, 3, 5));
        assertEquals("pc6^^^", StrUtil.piece(text, delm, 6, 9999));
        assertEquals("", StrUtil.piece(text, delm, 0));
        assertEquals("", StrUtil.piece(text, delm, 0, 0));
        assertEquals("pc1^pc2^pc3^^pc5", StrUtil.piece(text, delm, 0, 5));
    }

    @Test
    public void testNumUtil() {
        assertEquals("0", NumUtil.toString(0.0));
        assertEquals("1.25", NumUtil.toString(1.25));
        assertEquals("125", NumUtil.toString(125.0));
        assertTrue(NumUtil.compare(1, 2) < 0);
        assertTrue(NumUtil.compare(5, 5) == 0);
        assertTrue(NumUtil.compare(6, 3) > 0);
        assertTrue(NumUtil.compare(1.34, 2.5) < 0);
        assertTrue(NumUtil.compare(3.54, 3.54) == 0);
        assertTrue(NumUtil.compare(5.45, 5.31) > 0);
        assertEquals(5, NumUtil.enforceRange(10, 1, 5));
        assertEquals(10, NumUtil.enforceRange(10, 1, 20));
        assertEquals(5, NumUtil.enforceRange(1, 5, 20));
    }

    @Test
    public void testBooleanUtil() {
        assertTrue(StrUtil.toBoolean("YES"));
        assertTrue(StrUtil.toBoolean("Y"));
        assertTrue(StrUtil.toBoolean("yes"));
        assertTrue(StrUtil.toBoolean("y"));
        assertTrue(StrUtil.toBoolean("TRUE"));
        assertTrue(StrUtil.toBoolean("t"));
        assertTrue(StrUtil.toBoolean("1"));
        assertTrue(StrUtil.toBoolean("100"));
        assertFalse(StrUtil.toBoolean(null));
        assertFalse(StrUtil.toBoolean(""));
        assertFalse(StrUtil.toBoolean("false"));
        assertFalse(StrUtil.toBoolean("0"));
        assertFalse(StrUtil.toBoolean("any old string"));
    }

    @Test
    public void testLocalDateUtil() {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        testLocalDate(now);
        testLocalDate(now);
        testLocalDate("T", today, 0);
        testLocalDate("N", now, 1000);
        testLocalDate("T+30", today.plus(30, ChronoUnit.DAYS), 0);
        testLocalDate("N+30", now.plus(30, ChronoUnit.DAYS), 1000);
        testLocalDate("T-4", today.minus(4, ChronoUnit.DAYS), 0);
        testLocalDate("T-50s", now.minus(50, ChronoUnit.SECONDS), 0);
        testLocalDate("N-50s", now.minus(50, ChronoUnit.SECONDS), 1000);
        testLocalDate("T-50h", now.minus(50, ChronoUnit.HOURS), 0);
        testLocalDate("T-50n", now.minus(50, ChronoUnit.MILLIS), 0);
        Temporal date = DateUtil.parseLocalDate("19880302");
        testLocalDate(date.toString(), date, 0);
    }

    private void testLocalDate(
            String value,
            Temporal expected,
            int threshold) {
        Temporal actual = DateUtil.parseLocalDate(value);
        testLocalDate(actual);
        long diff = threshold == 0 ? 0 : expected.isSupported(ChronoUnit.MILLIS) && actual.isSupported(ChronoUnit.MILLIS)
                ? Math.abs(ChronoUnit.MILLIS.between(expected, actual)) : 0;
        assertTrue("Difference exceeded threshold " + diff + " (" + threshold + ")", diff <= threshold);
    }

    private void testLocalDate(Temporal date) {
        String text = DateUtil.formatDate(date);
        print(text);
        Temporal date2 = DateUtil.parseLocalDate(text);
        String text2 = DateUtil.formatDate(date2);
        assertEquals(text, text2);
    }

    @Test
    public void testDateUtil() {
        testDate(now());
        testDate(today());
        testDate("T", today(), 0);
        testDate("N", now(), 100);
        testDate("T+30", DateUtil.addDays(today(), 30, false), 0);
        testDate("N+30", DateUtil.addDays(now(), 30, false), 100);
        testDate("T-4", DateUtil.addDays(today(), -4, false), 0);
        testDate("T-50s", new Date(today().getTime() - 50000), 0);
        testDate("N-50s", new Date(now().getTime() - 50000), 100);
        testDate("T-50h", new Date(today().getTime() - 50 * 60 * 60 * 1000), 0);
        testDate("T-50n", new Date(today().getTime() - 50 * 60 * 1000), 0);
        Date date = DateUtil.parseDate("19880302");
        testDate(date.toString(), date, 0);
    }

    private Date now() {
        return new Date();
    }

    private Date today() {
        return DateUtil.stripTime(now());
    }

    private void testDate(
            String value,
            Date expected,
            int threshold) {
        Date actual = DateUtil.parseDate(value);
        testDate(actual);
        long diff = Math.abs(expected.getTime() - actual.getTime());
        assertTrue("Difference exceeded threshold " + diff + " (" + threshold + ")", diff <= threshold);
    }

    private void testDate(Date date) {
        testDate(date, true, true);
        testDate(date, false, false);
        testDate(date, true, false);
        testDate(date, false, true);
    }

    private void testDate(
            Date date,
            boolean showTimezone,
            boolean ignoreTime) {
        String text = DateUtil.formatDate(date, showTimezone, ignoreTime);
        print(text);
        Date date2 = DateUtil.parseDate(text);
        String text2 = DateUtil.formatDate(date2, showTimezone, ignoreTime);
        assertEquals(text, text2);
    }

    @Test
    public void testDefaultTimeZone() throws Exception {
        testDateFormat("EST", "13:04");
        testDateFormat("GMT", "18:04");
        testDateFormat("CST", "12:04");
    }

    @Test
    public void testFormatting() throws Exception {
        doTestFormatting("", "");
        doTestFormatting(" 00:00", "");
        doTestFormatting(" 13:24", " 13:24");
        doTestFormatting(" 00:39", " 00:39");
    }

    private void doTestFormatting(
            String time,
            String expected) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy" + (time.length() == 0 ? "" : " HH:mm"));
        Date date = formatter.parse(DATE + time);
        assertEquals(DateUtil.formatDate(date), DATE + expected);
    }

    private void testDateFormat(
            String tz,
            String time) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm zzz");
        Date date = formatter.parse(DATE + " 13:04 EST"); // Reference date/time is 21-Nov-1978 13:04 EST
        TimeZone.setDefault(TimeZone.getTimeZone(tz));
        String DATE_TIME_NOTZ = DATE + " " + time;
        String DATE_TIME_TZ = DATE_TIME_NOTZ + " " + tz;
        assertEquals(DATE_TIME_TZ, DateUtil.formatDate(date, true));
        assertEquals(DATE_TIME_NOTZ, DateUtil.formatDate(date));
        assertEquals(DATE, DateUtil.formatDate(date, true, true));
        assertEquals(DATE_TIME_NOTZ, DateUtil.formatDate(date, false));
        assertEquals(DATE_TIME_NOTZ, DateUtil.formatDate(date, false, false));

        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        date = formatter.parse(DATE);
        assertEquals(DATE, DateUtil.formatDate(date, true));
        assertEquals(DATE, DateUtil.formatDate(date));
        assertEquals(DATE, DateUtil.formatDate(date, true, true));
        assertEquals(DATE, DateUtil.formatDate(date, false));
        assertEquals(DATE, DateUtil.formatDate(date, false, false));
    }

    @Test
    public void testDateRange() {
        DateRange dr = new DateRange("test|12-Jul-2010|15-Aug-2010");
        assertEquals(dr.getStartDate(), DateUtil.toDate(12, 7, 2010));
        assertEquals(dr.getEndDate(), DateUtil.toDate(15, 8, 2010));
        assertTrue(dr.inRange(DateUtil.toDate(1, 8, 2010)));
        assertFalse(dr.inRange(DateUtil.toDate(15, 8, 2010)));
        assertTrue(dr.inRange(DateUtil.toDate(15, 8, 2010), true, true));
        assertFalse(dr.inRange(DateUtil.toDate(15, 8, 2010, 13, 30, 0), true, true));
        assertFalse(dr.inRange(DateUtil.toDate(16, 8, 2010), true, true));
    }

    @Test
    public void testSerializer() {
        JSONUtil.registerAlias("TestPerson", TestPerson.class);
        testSerializer(null);
        testSerializer("resourceType");
    }

    private void testSerializer(String typeProperty) {
        TestPerson obj = new TestPerson();
        String s = JSONUtil.serialize(typeProperty, obj);
        print(s);
        TestPerson obj2 = (TestPerson) JSONUtil.deserialize(typeProperty, s);
        assertEquals(obj, obj2);
        List<TestPerson> list = new ArrayList<>();
        list.add(obj);
        list.add(obj);
        s = JSONUtil.serialize(typeProperty, list);
        print(s);
        List<TestPerson> list2 = JSONUtil.deserializeList(typeProperty, s, TestPerson.class);
        assertEquals(list, list2);
        @SuppressWarnings("unchecked")
        List<TestPerson> list3 = (List<TestPerson>) JSONUtil.deserialize(typeProperty, s);
        assertEquals(list, list3);
    }

    @Test
    public void testCast() {
        List<Object> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        int i = 0;

        for (String item : CollectionUtil.castList(list, String.class)) {
            assertEquals("item" + ++i, item);
        }

        Set<Object> set = new HashSet<>();
        Set<String> set2 = CollectionUtil.castCollection(set, String.class);
        set.add("item1");
        set2.add("item2");
        set2.add("item3");
        assertEquals(3, set.size());
        assertEquals(3, set2.size());
    }

    @Test
    public void testElapsed() {
        assertEquals("0.1 seconds", DateUtil.formatElapsed(100.0, TimeUnit.SECONDS));
        assertEquals("1 second", DateUtil.formatElapsed(1000.0));
        assertEquals("1 minute", DateUtil.formatElapsed(60000.0));
        assertEquals("3.59 days", DateUtil.formatElapsed(309898934.0));
        assertEquals("98.2 years", DateUtil.formatElapsed(3098989343984.0));
        assertEquals("-98.2 years", DateUtil.formatElapsed(-3098989343984.0));

        assertEquals(100.0, DateUtil.parseElapsed("0.1 seconds"), 0.0);
        assertEquals(1000.0, DateUtil.parseElapsed("1 second"), 0.0);
        assertEquals(60000.0, DateUtil.parseElapsed("1 minute"), 0.0);
        assertEquals(310176000.0, DateUtil.parseElapsed("3.59 days"), 0.0);
        assertEquals(3098956320000.0, DateUtil.parseElapsed("98.2 years"), 0.0);
        assertEquals(-3098956320000.0, DateUtil.parseElapsed("-98.2 years"), 0.0);
        assertEquals(98.2, DateUtil.parseElapsed("98.2 years", TimeUnit.YEARS), 0.0);
    }

    @Test
    public void testDuration() {
        assertEquals("0 seconds", DateUtil.formatDuration(100, TimeUnit.SECONDS));
        assertEquals("0 sec", DateUtil.formatDuration(100, TimeUnit.SECONDS, false, true));
        assertEquals("1 second", DateUtil.formatDuration(1000, TimeUnit.SECONDS));
        assertEquals("1 minute", DateUtil.formatDuration(60000, TimeUnit.SECONDS));
        assertEquals("3 days 14 hours 4 minutes 58 seconds", DateUtil.formatDuration(309898934, TimeUnit.SECONDS));
        assertEquals("3 day 14 hour 4 minute 58 second", DateUtil.formatDuration(309898934, TimeUnit.SECONDS, false, false));
        assertEquals("98 years 2 months 1 week 6 days 10 hours 22 minutes 23 seconds",
                DateUtil.formatDuration(3098989343984L, TimeUnit.SECONDS));
        assertEquals("3 days 14 hrs 4 mins 58 secs", DateUtil.formatDuration(309898934, TimeUnit.SECONDS, true, true));
        assertEquals("-98 years 2 months 1 week 6 days 10 hours 22 minutes 23 seconds",
                DateUtil.formatDuration(-3098989343984L, TimeUnit.SECONDS));
    }

    @Test
    public void testAge() {
        Date dob = DateUtil.toDate(27, 7, 1958);
        Date ref = DateUtil.toDate(1, 1, 2013);
        assertEquals("54 yrs", DateUtil.formatAge(dob, true, ref));
        assertEquals("54 yr", DateUtil.formatAge(dob, false, ref));
        dob = DateUtil.toDate(15, 12, 2012);
        assertEquals("17 days", DateUtil.formatAge(dob, true, ref));
        dob = DateUtil.toDate(30, 10, 2012);
        assertEquals("2 mos", DateUtil.formatAge(dob, true, ref));
    }

    private static final String QT_NONE = "This is a test.";

    private static final String QT_DOUBLE = "\"This is a test.\"";

    private static final String QT_SINGLE = "'This is a test.'";

    private static final String QT_OTHER1 = "\"This is a test.'";

    private static final String QT_OTHER2 = "\"This is a test.";

    private static final String QT_OTHER3 = "This is a test.'";

    @Test
    public void testStripQuotes() {
        testStripQuotes(QT_NONE, QT_NONE);
        testStripQuotes(QT_NONE, QT_SINGLE);
        testStripQuotes(QT_NONE, QT_DOUBLE);
        testStripQuotes(QT_OTHER1, QT_OTHER1);
        testStripQuotes(QT_OTHER2, QT_OTHER2);
        testStripQuotes(QT_OTHER3, QT_OTHER3);
    }

    private void testStripQuotes(
            String expected,
            String value) {
        assertEquals(expected, StrUtil.stripQuotes(value));
    }

    @Test
    public void testEnquote() {
        testEnquote(QT_DOUBLE, QT_NONE, false);
        testEnquote(QT_SINGLE, QT_NONE, true);
        testEnquote(QT_DOUBLE, QT_DOUBLE, false);
        testEnquote(QT_SINGLE, QT_SINGLE, true);
    }

    public void testEnquote(
            String expected,
            String value,
            boolean single) {
        assertEquals(expected, single ? StrUtil.enquoteSingle(value) : StrUtil.enquoteDouble(value));
    }

    @Test
    public void testColorUtil() {
        testColorUtil("darkorchid", "#9932CC");
        testColorUtil("azure", "#F0FFFF");
    }

    public void testColorUtil(
            String testColor,
            String testRGB) {
        Color refColor = Color.magenta;
        String rgb = ColorUtil.getRGBFromName(testColor);
        assertEquals(rgb, testRGB);
        String color = ColorUtil.getNameFromRGB(rgb);
        assertEquals(color.toLowerCase(), testColor.toLowerCase());
        Color color1 = ColorUtil.toColor(rgb, refColor);
        Color color2 = ColorUtil.toColor(color, refColor);
        assertEquals(color1, color2);
        Color color3 = ColorUtil.toColor("badvalue", refColor);
        assertEquals(refColor, color3);
    }

    @Test
    public void testQueryStringBuilder() {
        QueryStringBuilder sb = new QueryStringBuilder();
        assertEquals(0, sb.length());
        sb.append("q1", "value 1", "value 2");
        List<String> list = new ArrayList<>();
        list.add("value 1");
        list.add("value 2");
        list.add("value 3");
        sb.append("q2", list);
        sb.append("q3", "single");
        sb.append("q4<>", "escape+name");
        String qs = sb.toString();
        print(qs);
        assertEquals("q1=value+1,value+2&q2=value+1&q2=value+2&q2=value+3&q3=single&q4%3C%3E=escape%2Bname", qs);
        sb.clear();
        assertTrue(sb.toString().isEmpty());
    }

    @Test
    public void testListMethods() {
        String original = "1,2,3,4,5";
        // Basic conversion to and from list
        List<String> strList = StrUtil.toList(original, ",");
        assertEquals(5, strList.size());
        String str = StrUtil.fromList(strList, ",");
        assertEquals(original, str);
        // Behavior with empty elements and trailing delimiter
        strList = StrUtil.toList(",,3,4,5,", ",");
        assertEquals(5, strList.size());
        // Behavior with non-string list
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        str = StrUtil.fromList(intList, ",");
        assertEquals(original, str);
        // Behavior with null list entry, with and without default value
        intList = Arrays.asList(1, 2, null, 4, 5);
        str = StrUtil.fromList(intList, ",");
        assertEquals("1,2,,4,5", str);
        str = StrUtil.fromList(intList, ",", null);
        assertEquals("1,2,4,5", str);
        str = StrUtil.fromList(intList, ",", "3");
        assertEquals(original, str);
        List<Object> iterList = new ArrayList<>();
        iterList.add(1);
        iterList.add("string #1");
        iterList.add(2);
        iterList.add(1L);
        iterList.add("string #2");
        Iterator<Integer> iter = CollectionUtil.iteratorForType(iterList, Integer.class);
        assertTrue(iter.hasNext());
        assertEquals((Integer) 1, iter.next());
        iter.remove();
        assertEquals((Integer) 2, iter.next());
        assertFalse(iter.hasNext());
        assertEquals(4, iterList.size());
        int strCount = 0;

        for (String ele : CollectionUtil.iterableForType(iterList, String.class)) {
            strCount++;
            assertEquals(ele, "string #" + strCount);
        }

        assertEquals(2, strCount);
    }

    @Test
    public void testObservedList() {
        List<String> list = new ArrayList<>();
        final int[] ops = {0, 0};

        ObservableCollection<String> col = new ObservableCollection<>(list,
                new ObservableCollection.IObservableCollectionListener<String>() {

                    @Override
                    public void onAddElement(String element) {
                        ops[0]++;
                    }

                    @Override
                    public void onRemoveElement(String element) {
                        ops[1]++;
                    }

                });

        col.add("ele1"); // ele1
        col.add("ele2"); // ele1, ele2
        col.remove("ele1"); // ele2
        col.addAll(Arrays.asList("ele3", "ele4", "ele5")); // ele2, ele3, ele4, ele5
        col.removeAll(Arrays.asList("ele1", "ele4")); // ele2, ele3, ele5
        col.retainAll(Arrays.asList("ele1", "ele3")); // ele3
        assertEquals("Add count does not match.", 5, ops[0]);
        assertEquals("Remove count does not match.", 4, ops[1]);
        col.clear();
        assertEquals("Add count does not match.", 5, ops[0]);
        assertEquals("Remove count does not match.", 5, ops[1]);
    }

    @Test
    public void testVersion() {
        final Version v1 = new Version(1, 2, 3, 4);
        testVersion(v1, "1.2.3.4", null, 1, 2, 3, 4);

        final Version v2 = new Version("1.2.3.4");
        testVersion(v2, "1.2.3.4", null, 1, 2, 3, 4);
        assertEquals(v1, v2);

        final Version v3 = new Version("1.2.4.5");
        testVersion(v3, "1.2.4.5", null, 1, 2, 4, 5);
        assertNotEquals(v1, v3);
        assertEquals(-1, v1.compareTo(v3));
        assertEquals(-1, v1.compareTo(v3, VersionPart.RELEASE));
        assertEquals(0, v1.compareTo(v3, VersionPart.MINOR));
        assertEquals(0, v1.compareTo(v3, VersionPart.MAJOR));

        final Version v4 = new Version("1.2.3");
        testVersion(v4, "1.2.3", null, 1, 2, 3);
        assertEquals("1.2.3", v4.toString(VersionPart.RELEASE));
        assertEquals("1.2.3.0", v4.toString(VersionPart.BUILD));
        assertTrue(v4.compareTo(v2) < 0);

        final Version v5 = new Version("1.0.3");
        testVersion(v5, "1.0.3", null, 1, 0, 3);
        assertEquals("1.0.3.0", v5.toString(VersionPart.BUILD));
        assertEquals("1.0", v5.toString(VersionPart.MINOR));
        assertEquals("1.0.3", v5.toString(VersionPart.RELEASE));

        final Version v6 = new Version("1.0.3b");
        testVersion(v6, "1.0.3b", "b", 1, 0, 3);
        assertNotEquals(v5, v6);
        assertEquals(-1, v5.compareTo(v6));
        assertEquals(0, v5.compareTo(v6, false));
        assertEquals(0, v5.compareTo(v6, VersionPart.RELEASE));
        assertEquals(-1, v5.compareTo(v6, VersionPart.MINOR, true));

        final Version v7 = new Version(".5");
        testVersion(v7, "0.5", null, 0, 5);

        final Version v8 = new Version("");
        testVersion(v8, "", null);

        final Version v9 = new Version(null);
        testVersion(v9, "", null);
        assertEquals(v8, v9);

        final Version v10 = new Version("1.5.4-SNAPSHOT");
        testVersion(v10, "1.5.4-SNAPSHOT", "-SNAPSHOT", 1, 5, 4);
        assertEquals("1.5.4", v10.toString(VersionPart.RELEASE, false));
        assertEquals("1.5.4-SNAPSHOT", v10.toString(VersionPart.RELEASE, true));
        assertEquals("1.5.4.0-SNAPSHOT", v10.toString(VersionPart.BUILD, true));

        final Version v11 = new Version("1.5.4.SNAPSHOT");
        testVersion(v11, "1.5.4.SNAPSHOT", ".SNAPSHOT", 1, 5, 4);
        assertEquals("1.5.4", v11.toString(VersionPart.RELEASE, false));
        assertEquals("1.5.4.0.SNAPSHOT", v11.toString(VersionPart.BUILD, true));

        final Version v12 = new Version("1.5.4.SNAPSHOT.1234");
        testVersion(v12, "1.5.4.SNAPSHOT.1234", ".SNAPSHOT.1234", 1, 5, 4);
        assertEquals("1.5.4", v12.toString(VersionPart.RELEASE, false));
        assertEquals("1.5.4.0.SNAPSHOT.1234", v12.toString(VersionPart.BUILD, true));

        final Version v13 = new Version("RELEASE");
        testVersion(v13, "RELEASE", "RELEASE");

        final Version v14 = new Version(".RELEASE");
        testVersion(v14, "0.RELEASE", ".RELEASE", 0);
    }

    private void testVersion(
            Version v,
            String toString,
            String classifier,
            int... parts) {
        assertEquals(toString, v.toString());
        assertEquals(classifier, v.getClassifier());
        int max = parts.length - 1;

        for (int i = 0; i < 4; i++) {
            int part = i > max ? -1 : parts[i];
            assertEquals(part, v.getPart(VersionPart.values()[i]));
        }

        assertEquals(max < 0 ? null : VersionPart.values()[max], v.getSpecificity());
    }

    @Test
    public void testBundle() {
        Localizer.registerMessageSource(new BundleMessageSource());
        Locale locale1 = new Locale("en");
        Locale locale2 = new Locale("fr");
        assertEquals("keyboard", StrUtil.getLabel("message.test1", locale1));
        assertEquals("clavier", StrUtil.getLabel("message.test1", locale2));
    }

    @Test
    public void testWeakCollections() {
        List<Object> list = new WeakList<>();
        Map<String, Object> map = new WeakMap<>();

        Object o1 = new Object();
        Object o2 = new Object();
        list.add(o1);
        list.add(o2);
        map.put("o1", o1);
        map.put("o2", o2);
        assertEquals(2, list.size());
        assertEquals(2, map.size());
        o1 = null;
        System.gc();
        wait(3000);
        assertEquals(1, list.size());
        assertEquals(1, map.size());
    }

    private static final String CAMEL_UCASE_RESULT = "TestOfCamelCase";

    private static final String CAMEL_LCASE_RESULT = "testOfCamelCase";

    @Test
    public void testCamelCase() {
        testCamelCase(" test of _camel_case  ");
        testCamelCase("TEST OF CAMEL CASE");
        testCamelCase("test_of_camel_case");
        testCamelCase("test__of_ camel\t\ncase");
        testCamelCase("test of camel case");
    }

    private void testCamelCase(String text) {
        assertEquals(CAMEL_UCASE_RESULT, StrUtil.toCamelCaseUpper(text));
        assertEquals(CAMEL_LCASE_RESULT, StrUtil.toCamelCaseLower(text));
    }

    private static final Integer[] CYCLIC_TEST1 = { 0, 1, 2 };

    private static final Integer[] CYCLIC_TEST2 = {};

    @Test
    public void testCyclicIterator() {
        CyclicIterator<Integer> iter = new CyclicIterator<>(CYCLIC_TEST1);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(j, iter.next().intValue());
            }
        }

        iter.reset();
        assertEquals(0, iter.next().intValue());
        assertEquals(1, iter.next().intValue());
        iter.reset();
        assertEquals(0, iter.next().intValue());
        assertEquals(1, iter.next().intValue());
        iter = new CyclicIterator<>((Iterable<Integer>) null);
        assertFalse(iter.hasNext());
        iter = new CyclicIterator<>(CYCLIC_TEST2);
        assertFalse(iter.hasNext());
    }

    @Test
    public void testRegEx() {
        Pattern p = MiscUtil.globToRegex("*.*");
        assertTrue(p.matcher("test.input").matches());
        assertTrue(p.matcher("test.input.input").matches());
        assertFalse(p.matcher("noperiod").matches());

        p = MiscUtil.globToRegex("*.*.*");
        assertFalse(p.matcher("test.input").matches());
        assertTrue(p.matcher("test.input.input").matches());

        p = MiscUtil.globToRegex("this?is?a?test");
        assertTrue(p.matcher("this is a test").matches());
        assertTrue(p.matcher("this_is a_test").matches());
        assertFalse(p.matcher("this_is  a_test").matches());
        assertFalse(p.matcher("this is a test also").matches());
    }

    private static final String[] ary1 = { "item1", "item2", "item3" };

    private static final String[] ary2 = { "item4", "item5", "item6" };

    private static final String[] ary3 = { "item3", "item6", "item7" };

    @Test
    public void testOverlap() {
        assertFalse(CollectionUtil.intersects(ary1, ary2));
        assertTrue(CollectionUtil.intersects(ary1, ary3));
        assertTrue(CollectionUtil.intersects(ary2, ary3));
    }

    private void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // NOP
        }
    }

    private void print(Object object) {
        System.out.println(object);
        System.out.println("------------------------");
    }

    private static final String cmp1 = "ABC";

    private static final String cmp2 = "abc";

    private static final String cmp3 = "ABC";

    @Test
    public void testStringComparison() {
        assertTrue(StrUtil.areEqual(cmp1, cmp2, false));
        assertFalse(StrUtil.areEqual(cmp1, cmp2, true));
        assertTrue(StrUtil.areEqual(cmp1, cmp3, true));
        assertTrue(StrUtil.areEqual(cmp1, cmp3, false));

        assertTrue(StrUtil.compare(cmp1, cmp2) < 0);
        assertTrue(StrUtil.compare(cmp2, cmp2) == 0);
        assertTrue(StrUtil.compare(cmp2, cmp1) > 0);
        assertTrue(StrUtil.compare(null, cmp2) < 0);
        assertTrue(StrUtil.compare(cmp1, null) > 0);
        assertTrue(StrUtil.compare(cmp1, cmp3) == 0);
        assertTrue(StrUtil.compare(null, null) == 0);

        assertTrue(StrUtil.compareIgnoreCase(cmp1, cmp2) == 0);
        assertTrue(StrUtil.compareIgnoreCase(cmp2, cmp1) == 0);
        assertTrue(StrUtil.compareIgnoreCase(null, cmp2) < 0);
        assertTrue(StrUtil.compareIgnoreCase(cmp1, null) > 0);
        assertTrue(StrUtil.compareIgnoreCase(null, null) == 0);
    }

    private static final Class<?>[] classes = {Integer.class, Number.class, String.class};

    @Test
    public void testClassUtils() {
        assertEquals(Number.class, MiscUtil.firstAssignable(Number.class, classes));
        assertEquals(Number.class, MiscUtil.firstAssignable(Long.class, classes));
        assertEquals(Integer.class, MiscUtil.firstAssignable(Integer.class, classes));
        assertNull(MiscUtil.firstAssignable(Object.class, classes));
    }

    @Test
    public void testAssertions() {
        Assert.isTrue(true, "");
        assertException(() -> Assert.isTrue(false, ""));
        Assert.isFalse(false, "");
        assertException(() -> Assert.isFalse(true, ""));
        Assert.isNull(null, "");
        assertException(() -> Assert.isNull("not null", ""));
        Assert.notNull("not null", "");
        assertException(() -> Assert.notNull(null, ""));
        assertException(() -> Assert.fail(""));
        Assert.state(true, "");
        assertThrows(IllegalStateException.class, () -> Assert.state(false, ""));
    }

    private void assertException(ThrowingRunnable test) {
        assertThrows(IllegalArgumentException.class, test);
    }

    private static final String MSG = "Assertion failed";

    private static final List<String> NULL = null;

    private static final List<String> EMPTY = Collections.emptyList();

    private static final List<String> NOT_EMPTY = Collections.singletonList("TEST");

    @Test
    public void asNullTests() {
        Assert.isNull(MiscUtil.asNull(() -> NULL.get(0).isEmpty()), MSG);
        Assert.isNull(MiscUtil.asNull(() -> EMPTY.get(0).isEmpty()), MSG);
        Assert.notNull(MiscUtil.asNull(() -> NOT_EMPTY.get(0).isEmpty()), MSG);
    }

}
