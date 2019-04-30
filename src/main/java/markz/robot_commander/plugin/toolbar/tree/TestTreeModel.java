package markz.robot_commander.plugin.toolbar.tree;

import markz.robot_commander.plugin.toolbar.tree.node.BaseTestNode;
import markz.robot_commander.plugin.toolbar.tree.node.TestDirectoryNode;
import markz.robot_commander.plugin.toolbar.tree.node.TestSuiteNode;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.IOException;

public class TestTreeModel extends DefaultTreeModel {

	/**
	 * Creates a tree in which any node can have children.
	 *
	 * @param rootFile the robot suite or file that is the root of the tree
	 */
	public TestTreeModel( File rootFile ) throws IOException {
		super( generateRootNode( rootFile ), false );
	}

	private static BaseTestNode generateRootNode( File file ) throws IOException {
		if ( file.isDirectory() ) {
			return new TestDirectoryNode( file );
		} else {
			return new TestSuiteNode( file );
		}
	}

	public void setRoot( File root ) throws IOException {
		super.setRoot( generateRootNode( root ) );
	}
}
