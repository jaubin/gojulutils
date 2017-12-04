package org.gojul.gojulutils.filter;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GojulFilterCompositeTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullArgThrowsException() {
        new GojulFilterComposite<String, Date>(null);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testAcceptWithNullValueThrowsException() {
        GojulFilter<Date, String> f1 = mock(GojulFilter.class);
        GojulFilter<Date, String> f2 = mock(GojulFilter.class);
        GojulFilter<Date, String> f3 = mock(GojulFilter.class);

        GojulFilterComposite<Date, String> composite = new GojulFilterComposite<>(Arrays.asList(f1, f2, f3));

        composite.accept(null, "hello");
    }

    @Test
    public void testAcceptWithOneFilterNotPassingReturnFalseAndSkipOtherFilters() {
        Date d = new Date();

        GojulFilter<Date, String> f1 = mock(GojulFilter.class);
        GojulFilter<Date, String> f2 = mock(GojulFilter.class);
        GojulFilter<Date, String> f3 = mock(GojulFilter.class);

        when(f1.accept(d, "hello")).thenReturn(true);
        when(f2.accept(d, "hello")).thenReturn(false);
        when(f3.accept(d, "hello")).thenReturn(true);


        GojulFilterComposite<Date, String> composite = new GojulFilterComposite<>(Arrays.asList(f1, f2, f3));

        assertFalse(composite.accept(d, "hello"));

        verifyNoMoreInteractions(f3);
    }

    @Test
    public void testAccept() {
        Date d = new Date();

        GojulFilter<Date, String> f1 = mock(GojulFilter.class);
        GojulFilter<Date, String> f2 = mock(GojulFilter.class);
        GojulFilter<Date, String> f3 = mock(GojulFilter.class);

        when(f1.accept(d, "hello")).thenReturn(true);
        when(f2.accept(d, "hello")).thenReturn(true);
        when(f3.accept(d, "hello")).thenReturn(true);


        GojulFilterComposite<Date, String> composite = new GojulFilterComposite<>(Arrays.asList(f1, f2, f3));

        assertTrue(composite.accept(d, "hello"));
    }
}