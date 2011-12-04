package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SearchTest.class,CalendarEventTest.class,EventTest.class,UserTest.class,SecurityTest.class,
				LocationTest.class,CommentTest.class,ReviewTest.class, DatabaseManagerTest.class})
public class AllTests {

}
