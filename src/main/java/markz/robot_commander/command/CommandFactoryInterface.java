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
import java.util.List;

/**
 * Configures and generates a Robot Framework command for test execution.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public interface CommandFactoryInterface {

	/**
	 * Retrieves the name of the command which will be used when generating the CLI command.
	 *
	 * @return The name of the command to be executed.
	 */
	String getCommandName();

	/**
	 * Sets the name of the command to be executed. Possible examples include "robot" or "pybot", though custom commands
	 * may be created and used.
	 *
	 * @param commandName The name of the command which will be used in the CLI command.
	 */
	void setCommandName( String commandName );

	/**
	 * Generates the command to be executed. With Robot Framework installed an the path set, this String could be pasted
	 * into the terminal and executed.
	 *
	 * @return An executable String corresponding to the current factory configuration.
	 */
	String generateCommand();

	/**
	 * Retrieves the working directory. This is the directory in which the Robot command should be executed.
	 *
	 * @return The working directory which contains the Robot tests.
	 */
	File getWorkingDirectory();

	/**
	 * Sets the new working directory where the Robot tests are located.
	 *
	 * @param workingDirectory The new directory of Robot tests.
	 */
	void setWorkingDirectory( File workingDirectory );

	/**
	 * Retrieves the list of included tags. This list is editable and is thread safe to allow editing.
	 * <p>
	 * Due to the nature of Robot Framework, these tags are included inclusively.
	 *
	 * <pre>{@code
	 * Test 1
	 *     [Tags]    A
	 *     ...
	 *
	 * Test 2
	 *     [Tags]    A    B
	 *     ...
	 *
	 * Test 3
	 *     [Tags]    B
	 *     ...
	 * }</pre>
	 * <p>
	 * In the above example, if Tags {@code A} and {@code B} are included, all 3 tests will be included. To only run
	 * tests that have <b>both</b> {@code A} and {@code B} (in this case, only Test 2), a third tag would need to be
	 * added and included (perhaps {@code AB}), or the test would need to be explicitly specified in the tests using
	 * {@link CommandFactoryInterface#getTests()}.
	 *
	 * @return An editable list of the tags to include.
	 */
	List<String> getIncludedTags();

	/**
	 * Retrieves the list of excluded tags. This list is editable and is thread safe to allow editing.
	 * <p>
	 * Due to the nature of Robot Framework, these tags are excluded inclusively.
	 *
	 * <pre>{@code
	 * Test 1
	 *     [Tags]    A
	 *     ...
	 *
	 * Test 2
	 *     [Tags]    A    B
	 *     ...
	 *
	 * Test 3
	 *     [Tags]    B
	 *     ...
	 * }</pre>
	 * <p>
	 * In the above example, if Tags {@code A} and {@code B} are excluded, all 3 tests will be excluded. To only exclude
	 * tests that have <b>both</b> {@code A} and {@code B} (in this case, only Test 2), a third tag would need to be
	 * added and excluded (perhaps {@code AB}), or the tests would need to be explicitly specified in the tests using
	 * {@link CommandFactoryInterface#getTests()}.
	 *
	 * @return An editable list of the tags to exclude.
	 */
	List<String> getExcludedTags();

	/**
	 * The tests to be executed.
	 *
	 * @return
	 */
	List<String> getTests();

	/**
	 * The suites to be executed.
	 *
	 * @return
	 */
	List<String> getSuites();

}
