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
import com.intellij.ui.treeStructure.Tree;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValues;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.treetable.FileSystemModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class TestPanel extends JPanel {

	private static boolean debugColors = false;   // TODO Set via config file
	private File rootFile;
	private TestTree testTree;

	public TestPanel( File file ) {
		if ( debugColors ) {
			this.setBackground( JBColor.RED );
		}

		this.rootFile = file;

		// Configuration
		this.setLayout( new BorderLayout() );

		// Initialize components
		TreePanel treePanel = new TreePanel();
		this.add( treePanel, BorderLayout.CENTER );
	}

	public void updateWorkingDirectory( File workingDirectory ) {
		this.rootFile = workingDirectory;
		FileSystemModel model = (FileSystemModel) this.testTree.getModel();
		model.setRoot( workingDirectory );
	}

	private class TestTree extends Tree {
		private TestTree() {
			super( new FileSystemModel( rootFile ) );
			this.setCellRenderer( new DefaultTreeRenderer( IconValues.FILE_ICON, StringValues.FILE_NAME ) );
		}
	}

	// Wrapper for the TestTree
	private class TreePanel extends ScrollPane {
		private TreePanel() {
			super();
			testTree = new TestTree();
			this.add( testTree );
		}
	}

}
