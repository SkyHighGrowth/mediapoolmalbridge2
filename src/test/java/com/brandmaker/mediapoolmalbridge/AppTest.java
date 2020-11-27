package com.brandmaker.mediapoolmalbridge;

import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetAssetsRequest;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AppTest {

    @Test
    public void testAppHasAGreeting()
    {
        App app = new App();
        assertTrue( true );
    }

    @Test
    public void test_01()
    {
        System.out.println((new Gson()).toJson(new MALGetAssetsRequest()));
        assertTrue( true );
    }

}
