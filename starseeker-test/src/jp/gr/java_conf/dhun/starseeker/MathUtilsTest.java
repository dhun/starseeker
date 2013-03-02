package jp.gr.java_conf.dhun.starseeker;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import jp.gr.java_conf.dhun.starseeker.util.MathUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MathUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_floor() {
        assertThat(+0.0, is(MathUtils.floor(+0.0)));
        assertThat(+0.0, is(MathUtils.floor(+0.1)));
        assertThat(+0.0, is(MathUtils.floor(+0.2)));
        assertThat(+0.0, is(MathUtils.floor(+0.3)));
        assertThat(+0.0, is(MathUtils.floor(+0.4)));
        assertThat(+0.0, is(MathUtils.floor(+0.5)));
        assertThat(+0.0, is(MathUtils.floor(+0.6)));
        assertThat(+0.0, is(MathUtils.floor(+0.7)));
        assertThat(+0.0, is(MathUtils.floor(+0.8)));
        assertThat(+0.0, is(MathUtils.floor(+0.9)));

        assertThat(-0.0, is(MathUtils.floor(-0.0)));
        assertThat(-0.0, is(MathUtils.floor(-0.1)));
        assertThat(-0.0, is(MathUtils.floor(-0.2)));
        assertThat(-0.0, is(MathUtils.floor(-0.3)));
        assertThat(-0.0, is(MathUtils.floor(-0.4)));
        assertThat(-0.0, is(MathUtils.floor(-0.5)));
        assertThat(-0.0, is(MathUtils.floor(-0.6)));
        assertThat(-0.0, is(MathUtils.floor(-0.7)));
        assertThat(-0.0, is(MathUtils.floor(-0.8)));
        assertThat(-0.0, is(MathUtils.floor(-0.9)));

        assertThat(+1.0, is(MathUtils.floor(+1.0)));
        assertThat(+1.0, is(MathUtils.floor(+1.1)));
        assertThat(+1.0, is(MathUtils.floor(+1.2)));
        assertThat(+1.0, is(MathUtils.floor(+1.3)));
        assertThat(+1.0, is(MathUtils.floor(+1.4)));
        assertThat(+1.0, is(MathUtils.floor(+1.5)));
        assertThat(+1.0, is(MathUtils.floor(+1.6)));
        assertThat(+1.0, is(MathUtils.floor(+1.7)));
        assertThat(+1.0, is(MathUtils.floor(+1.8)));
        assertThat(+1.0, is(MathUtils.floor(+1.9)));

        assertThat(-1.0, is(MathUtils.floor(-1.0)));
        assertThat(-1.0, is(MathUtils.floor(-1.1)));
        assertThat(-1.0, is(MathUtils.floor(-1.2)));
        assertThat(-1.0, is(MathUtils.floor(-1.3)));
        assertThat(-1.0, is(MathUtils.floor(-1.4)));
        assertThat(-1.0, is(MathUtils.floor(-1.5)));
        assertThat(-1.0, is(MathUtils.floor(-1.6)));
        assertThat(-1.0, is(MathUtils.floor(-1.7)));
        assertThat(-1.0, is(MathUtils.floor(-1.8)));
        assertThat(-1.0, is(MathUtils.floor(-1.9)));
    }

    @Test
    public void test_round() {
        assertThat(+0.0, is(MathUtils.round(+0.0)));
        assertThat(+0.0, is(MathUtils.round(+0.1)));
        assertThat(+0.0, is(MathUtils.round(+0.2)));
        assertThat(+0.0, is(MathUtils.round(+0.3)));
        assertThat(+0.0, is(MathUtils.round(+0.4)));
        assertThat(+1.0, is(MathUtils.round(+0.5)));
        assertThat(+1.0, is(MathUtils.round(+0.6)));
        assertThat(+1.0, is(MathUtils.round(+0.7)));
        assertThat(+1.0, is(MathUtils.round(+0.8)));
        assertThat(+1.0, is(MathUtils.round(+0.9)));

        assertThat(+0.0, is(MathUtils.round(-0.0)));
        assertThat(-0.0, is(MathUtils.round(-0.1)));
        assertThat(-0.0, is(MathUtils.round(-0.2)));
        assertThat(-0.0, is(MathUtils.round(-0.3)));
        assertThat(-0.0, is(MathUtils.round(-0.4)));
        assertThat(-1.0, is(MathUtils.round(-0.5)));
        assertThat(-1.0, is(MathUtils.round(-0.6)));
        assertThat(-1.0, is(MathUtils.round(-0.7)));
        assertThat(-1.0, is(MathUtils.round(-0.8)));
        assertThat(-1.0, is(MathUtils.round(-0.9)));

        assertThat(+1.0, is(MathUtils.round(+1.0)));
        assertThat(+1.0, is(MathUtils.round(+1.1)));
        assertThat(+1.0, is(MathUtils.round(+1.2)));
        assertThat(+1.0, is(MathUtils.round(+1.3)));
        assertThat(+1.0, is(MathUtils.round(+1.4)));
        assertThat(+2.0, is(MathUtils.round(+1.5)));
        assertThat(+2.0, is(MathUtils.round(+1.6)));
        assertThat(+2.0, is(MathUtils.round(+1.7)));
        assertThat(+2.0, is(MathUtils.round(+1.8)));
        assertThat(+2.0, is(MathUtils.round(+1.9)));

        assertThat(-1.0, is(MathUtils.round(-1.0)));
        assertThat(-1.0, is(MathUtils.round(-1.1)));
        assertThat(-1.0, is(MathUtils.round(-1.2)));
        assertThat(-1.0, is(MathUtils.round(-1.3)));
        assertThat(-1.0, is(MathUtils.round(-1.4)));
        assertThat(-2.0, is(MathUtils.round(-1.5)));
        assertThat(-2.0, is(MathUtils.round(-1.6)));
        assertThat(-2.0, is(MathUtils.round(-1.7)));
        assertThat(-2.0, is(MathUtils.round(-1.8)));
        assertThat(-2.0, is(MathUtils.round(-1.9)));
    }

}
