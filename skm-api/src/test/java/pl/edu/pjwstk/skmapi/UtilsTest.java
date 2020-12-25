package pl.edu.pjwstk.skmapi;

import org.junit.Assert;
import org.junit.Test;
import pl.edu.pjwstk.skmapi.util.Utils;

public class UtilsTest {


    @Test
    public void mainChoiceTest(){
        Object a = new Object();
        Object b = null;
        Assert.assertEquals(a,Utils.fallbackIfNull(a,b));
    }

    @Test
    public void alternativeChoiceTest(){
        Object a = new Object();
        Object b = null;
        Assert.assertEquals(a,Utils.fallbackIfNull(b,a));
    }
}
