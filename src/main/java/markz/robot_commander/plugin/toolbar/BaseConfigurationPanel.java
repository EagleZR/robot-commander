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

package markz.robot_commander.plugin.toolbar;

import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import markz.robot_commander.command.CommandFactoryInterface;
import markz.robot_commander.command.ConfigurationApplicator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class BaseConfigurationPanel extends JBPanel implements ConfigurationApplicator {

	private OptionPanel optionPanel;
	private TestPanel testPanel;

	public BaseConfigurationPanel( File file ) throws IOException {
		super( new BorderLayout() );
		this.optionPanel = new OptionPanel();
		this.add( this.optionPanel, BorderLayout.NORTH );
		this.testPanel = new TestPanel( file );
		this.add( new JBScrollPane( this.testPanel ), BorderLayout.CENTER );
	}

	public void updateWorkingDirectory( File file ) throws IOException {
		this.testPanel.updateWorkingDirectory( file );
	}

	public void applyConfiguration( CommandFactoryInterface factory ) {
		factory.setCommandName( this.optionPanel.getCommand() );
		addCSV2List( this.optionPanel.getExcludedTags(), factory.getExcludedTags() );
		addCSV2List( this.optionPanel.getIncludedTags(), factory.getIncludedTags() );
		// TODO Add others to configuration
	}

	private void addCSV2List( String csv, List<String> list ) {
		list.addAll( Arrays.asList( csv.split( "," ) ) );
	}
}
