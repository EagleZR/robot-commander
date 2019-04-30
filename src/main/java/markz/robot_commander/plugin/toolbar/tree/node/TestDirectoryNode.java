package markz.robot_commander.plugin.toolbar.tree.node;

import markz.robot_commander.sifter.RobotFileSifter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestDirectoryNode extends BaseTestNode {

	public TestDirectoryNode( File file ) throws IOException {
		super( file, true );
		if ( !file.isDirectory() ) {
			throw new IOException( "File is not a directory: " + file );
		}
		try {
			for ( File child : file.listFiles() ) {
				if ( !Files.isSymbolicLink( child.toPath() ) && child.isDirectory() ) {
					addChild( child );
				}
			}
		} catch ( NullPointerException e ) {
			System.out.println( file.getAbsolutePath() );
			throw e;
		}

		for ( File child : RobotFileSifter.getRobotFiles( file ) ) {
			if ( !Files.isSymbolicLink( child.toPath() ) && child.exists() ) {
				addChild( child );
			}
		}
	}

	private void addChild( File child ) throws IOException {
		if ( child.getName().charAt( 0 ) != '.' ) {
			BaseTestNode childNode;
			if ( child.isDirectory() ) {
				childNode = new TestDirectoryNode( child );
			} else {
				childNode = new TestSuiteNode( child );
			}
			if ( !childNode.isLeaf() ) {
				add( childNode );
			}
		}
	}

	@Override
	public String toString() {
		return ( (File) this.userObject ).getName();
	}

	/**
	 * Determines whether or not this node is allowed to have children.
	 * If <code>allows</code> is false, all of this node's children are
	 * removed.
	 * <p>
	 * Note: By default, a node allows children.
	 *
	 * @param allows true if this node is allowed to have children
	 */
	@Override
	public void setAllowsChildren( boolean allows ) {

	}

	/**
	 * Returns true if this node is allowed to have children.
	 *
	 * @return true if this node allows children, else false
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}

}
