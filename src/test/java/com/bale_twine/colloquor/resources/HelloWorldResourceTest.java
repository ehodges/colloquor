package com.bale_twine.colloquor.resources;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.google.common.base.Optional;

import com.bale_twine.colloquor.core.Saying;

public class HelloWorldResourceTest {

    private String template = "Aloha, %s!";
	private String defaultName = "Bob";
	private final HelloWorldResource resource = new HelloWorldResource(template, defaultName);
	
	@Test
	public void testDefaultName() {
        Optional name = mock(Optional.class);
		when(name.or(defaultName)).thenReturn(defaultName);
		Saying returnedSaying = resource.sayHello(name);
        assertTrue(returnedSaying.getContent().equals(String.format(template, defaultName)));
    }
	
	@Test
	public void testName() {
		String requestName = "Dave";
        Optional name = mock(Optional.class);
		when(name.or(defaultName)).thenReturn(requestName);
		Saying returnedSaying = resource.sayHello(name);
        assertTrue(returnedSaying.getContent().equals(String.format(template, requestName)));
    }

}
