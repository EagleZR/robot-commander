/*
 * Copyright (c) 2019. Mark Zeagler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package markz.robot_commander.sifter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
class RobotFileSifterTest {

	//	private static RobotFileSifter retriever;
	private List<String> tests = new LinkedList<>( Arrays.asList( "test.robot", "test1.robot", "test2.robot", "test3.robot" ) );

	@BeforeAll static void beforeAll() {
		// Set up the retriever
		//		retriever = RobotFileSifter.getInstance();

		// Make sure that the current directory exists
		File currentDirectory = new File( "" ).getAbsoluteFile();
		System.out.println( currentDirectory.getAbsolutePath() + ":" + currentDirectory.exists() );

		// Print all child files and directories
		for ( File child : Objects.requireNonNull( currentDirectory.listFiles() ) ) {
			System.out.println( "\t" + child.getAbsolutePath() + ( child.isDirectory() ? "\\" : "" ) );
		}
	}

	//	@BeforeEach void beforeEach() throws FileNotFoundException {
	//		retriever.setWorkingDirectory( "" );
	//	}

	//	@Test void setWorkingDirectoryUsingStrings() throws FileNotFoundException {
	//		// Test that a file cannot be set
	//		final String badFileName = new File(
	//				Objects.requireNonNull( this.getClass().getClassLoader().getResource( "test.robot" ) ).getPath() )
	//				.getAbsolutePath();
	//		System.out.println( badFileName );
	//		assertThrows( FileNotFoundException.class, () -> retriever.setWorkingDirectory( badFileName ) );
	//
	//		// TODO Test that a non-existent directory cannot be set
	//
	//		// Test that the directory is correctly set
	//		String filePath = "";
	//		File file = new File( filePath ).getAbsoluteFile();
	//		System.out.println( file.getAbsolutePath() );
	//		//		retriever.setWorkingDirectory( filePath );
	//		//		assertEquals( file, retriever.getWorkingDirectory() );
	//	}

	//	@Test void setWorkingDirectoryUsingFiles() throws FileNotFoundException {
	//		// Test that a file cannot be set
	//		final File badFile = new File(
	//				Objects.requireNonNull( this.getClass().getClassLoader().getResource( "test.robot" ) ).getPath() );
	//		System.out.println( badFile.getAbsolutePath() );
	//		assertThrows( FileNotFoundException.class, () -> retriever.setWorkingDirectory( badFile ) );
	//
	//		// TODO Test that a non-existent directory cannot be set
	//
	//		// Test that the directory is correctly set
	//		String filePath = "";
	//		File file = new File( filePath ).getAbsoluteFile();
	//		System.out.println( file.getAbsolutePath() );
	//		retriever.setWorkingDirectory( file );
	//		assertEquals( file, retriever.getWorkingDirectory() );
	//	}

	@Test void getRobotFiles() throws URISyntaxException {
		// Set the working directory
		URL resource = getClass().getClassLoader().getResource( "test.robot" );
		File testFile = Paths.get( Objects.requireNonNull( resource ).toURI() ).toFile();
		assertTrue( testFile.exists() );
		File parentFile = testFile.getParentFile().getAbsoluteFile();
		assertTrue( parentFile.exists() );
		// Get the files
		List<File> fileList = RobotFileSifter.getRobotFiles( parentFile );
		// Assert that the number of files is correct
		assertEquals( tests.size(), fileList.size() );
		// Assert that each of the test names match a file name
		for ( String testName : tests ) {
			boolean matches = false;
			for ( File file : fileList ) {
				if ( testName.equals( file.getName() ) ) {
					matches = true;
					break;
				}
			}
			assertTrue( matches );
		}
		// Assert that each of the file names match a test name
		for ( File file : fileList ) {
			assertTrue( tests.contains( file.getName() ) );
		}
	}
}
