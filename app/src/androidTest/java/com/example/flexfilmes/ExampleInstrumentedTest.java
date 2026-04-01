package com.example.flexfilmes;

// Imports

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

// Teste instrumentado
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    // Teste de contexto
    @Test
    public void useAppContext() {

        // Contexto do app
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Verificação
        assertEquals("com.example.flexfilmes", appContext.getPackageName());
    }
}