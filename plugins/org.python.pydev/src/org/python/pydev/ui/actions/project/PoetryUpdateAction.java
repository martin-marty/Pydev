/**
 * 
 */
package org.python.pydev.ui.actions.project;

import org.python.pydev.ui.actions.container.PyPoetryAction;

/**
 * 
 */
public class PoetryUpdateAction extends PyPoetryAction {

    @Override
    protected String getTaskName() {
        return "Updating python packages...";
    }

    @Override
    protected String runCommand() {
        return pm.update();
    }
}
