package markz.robot_commander.plugin.toolbar.tree.node;

import markz.robot_commander.sifter.RobotTestSifter;

import java.io.File;
import java.io.IOException;

public class TestSuiteNode extends BaseTestNode {

	public TestSuiteNode( File file ) throws IOException {
		super( file, true );
		for ( String test : RobotTestSifter.getFileTests( file ) ) {
			add( new TestNode( test ) );
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
