package markz.robot_commander.sifter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
class RobotTestSifterTest {

	private static final List<String> TESTS = new LinkedList<>( Arrays.asList( "Test A", "Test B", "Test C" ) );
	private static final List<String> ALL_TESTS = new LinkedList<>(
			Arrays.asList( "Test A", "Test B", "Test C", "Test D", "Test E", "Test F", "Test G", "Test H", "Test I",
					"Test J", "Test K", "Test L" ) );

	@Test void getFileTests() throws Exception {
		// Get test file
		URL resource = getClass().getClassLoader().getResource( "test.robot" );
		File testFile = Paths.get( Objects.requireNonNull( resource ).toURI() ).toFile();
		assertTrue( testFile.exists() );
		// Sift test names
		List<String> tests = RobotTestSifter.getFileTests( testFile );
		for ( String test : tests ) {
			System.out.println( test );
		}
		assertEquals( TESTS.size(), tests.size() );
		for ( String test : TESTS ) {
			assertTrue( tests.contains( test ) );
		}
		for ( String test : tests ) {
			assertTrue( TESTS.contains( test ) );
		}
	}

	@Test void getAllFileTests() throws Exception {
		// Get test file
		URL resource = getClass().getClassLoader().getResource( "test.robot" );
		File testFile = Paths.get( Objects.requireNonNull( resource ).toURI() ).toFile();
		File parentDir = testFile.getParentFile();
		assertTrue( parentDir.exists() );
		// Sift test names
		List<File> testFiles = RobotFileSifter.getRobotFiles( parentDir );
		Map<String, List<String>> testMap = RobotTestSifter.getTestFileMap( testFiles );
		List<String> tests = new LinkedList<>();
		for ( File file : testFiles ) {
			tests.addAll( testMap.get( file.getAbsolutePath() ) );
		}
		for ( String test : tests ) {
			System.out.println( test );
		}
		assertEquals( ALL_TESTS.size(), tests.size() );
		for ( String test : ALL_TESTS ) {
			assertTrue( tests.contains( test ) );
		}
		for ( String test : tests ) {
			assertTrue( ALL_TESTS.contains( test ) );
		}
	}
}
