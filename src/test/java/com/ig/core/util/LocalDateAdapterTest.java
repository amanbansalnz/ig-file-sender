package com.ig.core.util;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(ZohhakRunner.class)
public class LocalDateAdapterTest {

    private LocalDateAdapter localDateAdapter;

    @Before
    public void setUp() throws Exception {
        localDateAdapter = new LocalDateAdapter();
    }

    @TestWith({
            "1507060723642, 2017-10-03T20:58:43.642",
            "1507602343642, 2017-10-10T03:25:43.642",
    })
    public void unmarshal(String localDateTimeInMillisec, String expectedUnmarshaledLocalDateTime) throws Exception {
        LocalDateTime actualLocalDateTime = localDateAdapter.unmarshal(localDateTimeInMillisec);
        assertThat(actualLocalDateTime.toString(), is(expectedUnmarshaledLocalDateTime));
    }

    @TestWith({
            "2017, SEPTEMBER, 12, 12, 35, 34, 2017-09-12T12:35:34",
            "2015, MARCH, 23, 15, 45, 23, 2015-03-23T15:45:23",
            "2018, DECEMBER, 10, 2, 45, 59, 2018-12-10T02:45:59",
    })
    public void marshal(int year, String month, int dayOfMonth, int hour, int minute, int sec, String expectedMarshaledLocalDateTime) throws Exception {
        String actualLocalDateTime = localDateAdapter.marshal(LocalDateTime.of(year, Month.valueOf(month), dayOfMonth, hour, minute, sec));
        assertThat(actualLocalDateTime.toString(), is(expectedMarshaledLocalDateTime));
    }


    @TestWith({
            "2017-10-10T03:25:43.642",
            "2017-10-10T03:25:43",
            "2017-10-10T03:25",
    })
    public void marshal(String expectedMarshaledLocalDateTime) throws Exception {
        String actualLocalDateTime = localDateAdapter.marshal(LocalDateTime.parse(expectedMarshaledLocalDateTime));
        assertThat(actualLocalDateTime.toString(), is(expectedMarshaledLocalDateTime));
    }
}