package org.inwiss.platform.xml;

import java.io.PrintWriter;
import java.io.Serializable;


/**
 *
 * @author $author$
 * @version $Revision: 1.2 $
 */
public interface XMLizable extends Serializable {
    //~ Static fields/initializers /////////////////////////////////////////////

    public static final String INDENT = "  ";

    //~ Methods ////////////////////////////////////////////////////////////////

    public void writeXML(PrintWriter writer, int indent);
}
