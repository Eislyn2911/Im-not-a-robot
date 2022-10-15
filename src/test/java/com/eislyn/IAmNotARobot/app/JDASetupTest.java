package com.eislyn.IAmNotARobot.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.cdimascio.dotenv.Dotenv;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import net.dv8tion.jda.api.JDA.Status;

@RunWith(JUnitParamsRunner.class)
public class JDASetupTest {
	@Test
	public void testValidGetJDAToken() {
		JDASetup jdaSetup = JDASetup.getInstance();
		String keyName = "DISCORD_JDA_KEY";

		Dotenv dotenv = Dotenv.load();
		String expectedToken = dotenv.get(keyName);
		String actualToken = jdaSetup.getJDAToken(keyName);
		
		assertEquals(expectedToken, actualToken);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidGetJDAToken")
	public void testInvalidGetJDAToken(String keyName) {
		JDASetup jdaSetup = JDASetup.getInstance();
		
		jdaSetup.getJDAToken(keyName);
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidGetJDAToken() {
		return new Object[] {
			new Object[] {null},
			new Object[] {""},
		};
	}
	
	@Test
	public void testValidSetupJDA() throws InterruptedException {
		JDASetup jdaSetup = JDASetup.getInstance();
		String keyName = "DISCORD_JDA_KEY";
		String token = jdaSetup.getJDAToken(keyName);

		Status actualStatus = jdaSetup.setupJDA(token);
		
		Status expectedStatus = Status.CONNECTED;
		assertEquals(expectedStatus, actualStatus);
	}
	
	@Test (expected = IllegalArgumentException.class)
	@Parameters(method = "paramTestInvalidSetupJDA")
	public void testInvalidSetupJDA(String token) throws InterruptedException {
		JDASetup jdaSetup = JDASetup.getInstance();
		jdaSetup.setupJDA(token);
	}
	
	@SuppressWarnings("unused")
	private Object[] paramTestInvalidSetupJDA() {
		return new Object[] {
			new Object[] {null},
			new Object[] {""},
		};
	}

}
