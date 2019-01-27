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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RobotFileSifter {

	private final static String[] fileExtensions;
	private final static List<String> IGNORED_FILE_NAMES;

	static {
		fileExtensions = new String[] { ".robot" };  // TODO Move to config file
		IGNORED_FILE_NAMES = new LinkedList<>( Arrays.asList( "__init__.robot" ) );  // TODO Move to config file
	}

	public static List<File> getRobotFiles( File workingDirectory ) {
		List<File> returnList = new LinkedList<>();
		for ( File child : Objects.requireNonNull( workingDirectory.listFiles() ) ) {
			if ( child.isDirectory() ) {
				returnList.addAll( getRobotFiles( child ) );
			} else {
				for ( String fileExtension : fileExtensions ) {
					if ( child.getName().endsWith( fileExtension ) && !IGNORED_FILE_NAMES.contains( child.getName() ) ) {
						returnList.add( child );
					}
				}
			}
		}
		return returnList;
	}
}
