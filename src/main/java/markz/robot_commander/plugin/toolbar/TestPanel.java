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

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.treeStructure.Tree;
import org.jdesktop.swingx.treetable.FileSystemModel;

import java.io.File;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class TestPanel extends JBPanel {

	private static boolean debugColors = false;   // TODO Set via config file
	private File rootFile;
	private TestTree testTree;

	public TestPanel( File file ) {
		if ( debugColors ) {
			this.setBackground( JBColor.RED );
		}

		this.rootFile = file;

		// Configuration
		// this.setLayout( new BorderLayout() );

		// Initialize components
		testTree = new TestTree();
		this.add( testTree );
	}

	public void updateWorkingDirectory( File workingDirectory ) {
		this.rootFile = workingDirectory;
		FileSystemModel model = (FileSystemModel) this.testTree.getModel();
		model.setRoot( workingDirectory );
	}

	private class TestTree extends Tree {
		private TestTree() {
			super( new FileSystemModel( rootFile ) );
		}
	}
}
