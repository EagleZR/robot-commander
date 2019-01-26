package markz.robot_commander.sifter;

import java.io.IOException;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class InvalidFileSyntaxException extends IOException {
	public InvalidFileSyntaxException( String s ) {
		super( s );
	}
}
