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

package markz.robot_commander.command;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class CommandFactory implements CommandFactoryInterface {

	private File workingDirectory;
	private List<String> includedTags;
	private List<String> excludedTags;
	private List<String> tests;
	private List<String> suites;

	public CommandFactory() {
		workingDirectory = new File( "" ).getAbsoluteFile();
	}

	public String generateCommand() {
		return "robot " + this.workingDirectory.getAbsolutePath();
	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory( File workingDirectory ) {
		this.workingDirectory = workingDirectory;
	}

	public List<String> getIncludedTags() {
		return Collections.synchronizedList( includedTags );
	}

	public List<String> getExcludedTags() {
		return Collections.synchronizedList( this.excludedTags );
	}

	public List<String> getTests() {
		return Collections.synchronizedList( this.tests );
	}

	public List<String> getSuites() {
		return Collections.synchronizedList( this.suites );
	}

	private String generateIncludedTags() {
		if ( this.includedTags == null ) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for ( String tag : this.includedTags ) {
			stringBuilder.append( " -i " ).append( tag );
		}
		return stringBuilder.toString();
	}

	private String generateExcludedTags() {
		if ( this.excludedTags == null ) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for ( String tag : this.excludedTags ) {
			stringBuilder.append( " -e " ).append( tag );
		}
		return stringBuilder.toString();
	}
}
