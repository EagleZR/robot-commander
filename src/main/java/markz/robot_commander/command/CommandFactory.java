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
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class CommandFactory implements CommandFactoryInterface {

	private String workingDirectory;
	private String commandName;
	private List<String> includedTags = new LinkedList<>();
	private List<String> excludedTags = new LinkedList<>();
	private List<String> tests = new LinkedList<>();
	private List<String> suites = new LinkedList<>();

	public CommandFactory() {
		workingDirectory = new File( "" ).getAbsolutePath();
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public void setCommandName( String commandName ) {
		this.commandName = commandName;
	}

	public String generateCommand() {
		StringBuilder command = new StringBuilder();
		command.append( this.commandName );
		addIncludedTags( command );
		addExcludedTags( command );
		addTests( command );
		addSuites( command );
		command.append( " " ).append( workingDirectory );
		return command.toString();
	}

	private void addIncludedTags( StringBuilder s ) {
		for ( String includedTag : this.includedTags ) {
			s.append( " -i " ).append( includedTag );
		}
	}

	private void addExcludedTags( StringBuilder s ) {
		for ( String excludedTag : this.excludedTags ) {
			s.append( " -e " ).append( excludedTag );
		}
	}

	private void addTests( StringBuilder s ) {
		for ( String test : this.tests ) {
			s.append( " -t " ).append( test );
		}
	}

	private void addSuites( StringBuilder s ) {
		for ( String suite : this.suites ) {
			s.append( " -s " ).append( suite );
		}
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory( String workingDirectory ) {
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
}
