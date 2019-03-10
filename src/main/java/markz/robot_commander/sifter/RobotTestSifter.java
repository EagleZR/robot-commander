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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public final class RobotTestSifter {

	private static final String TEST_SECTION_PATTERN_TEXT = "(\\*{3} Test Cases \\*{3}(?:[\\s\\S^](?!\\*\\*\\*))*)";
	private static final String TEST_NAME_PATTERN_TEXT = "\\n([a-zA-Z].*)";
	private static final Pattern TEST_SECTION_PATTERN = Pattern.compile( TEST_SECTION_PATTERN_TEXT );
	private static final Pattern TEST_NAME_PATTERN = Pattern.compile( TEST_NAME_PATTERN_TEXT );

	private RobotTestSifter() {

	}

	public static Map<String, List<String>> getTestFileMap( List<File> testFiles ) throws IOException {
		Map<String, List<String>> testFileMap = new HashMap<>();
		for ( File file : testFiles ) {
			testFileMap.put( file.getAbsolutePath(), getFileTests( file ) );
		}
		return testFileMap;
	}

	public static List<String> getFileTests( File file ) throws IOException {
		// Make sure file exists
		if ( !file.exists() ) {
			throw new FileNotFoundException( "The given file does not exist" );
		}
		// Read file
		System.out.println( "Reading file: " + file.getAbsolutePath() );
		String fileText = new String( Files.readAllBytes( file.toPath() ), StandardCharsets.UTF_8 );
		// Get test section
		String testSectionText = getTestSection( fileText );
		// Extract test names
		List<String> returnList = new LinkedList<>();
		Matcher matcher = TEST_NAME_PATTERN.matcher( testSectionText );
		while ( matcher.find() ) {
			returnList.add( matcher.group().trim() );
		}
		return returnList;
	}

	private static String getTestSection( String str ) throws InvalidFileSyntaxException {
		Matcher matcher = TEST_SECTION_PATTERN.matcher( str );
		if ( matcher.find() ) {
			if ( matcher.groupCount() == 0 ) {
				return "";
			}
			if ( matcher.groupCount() > 1 ) {
				throw new InvalidFileSyntaxException( "The file has too many \"*** Test Cases ***\" tags" );
			}
			return matcher.group();
		} else {
			return "";
		}
	}
}
