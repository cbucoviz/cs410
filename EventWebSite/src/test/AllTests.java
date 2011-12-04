package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DatabaseManagerTest.class, SearchTest.class,CalendarEventTest.class,EventTest.class,UserTest.class,UserUpdatesTest.class,SecurityTest.class,
				LocationTest.class,CommentTest.class})
public class AllTests {

}
