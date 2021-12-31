package org.agilekip.tutorials.travelemsg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.agilekip.tutorials.travelemsg.TravelTutorialEmsgApp;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = TravelTutorialEmsgApp.class)
public @interface IntegrationTest {
}
