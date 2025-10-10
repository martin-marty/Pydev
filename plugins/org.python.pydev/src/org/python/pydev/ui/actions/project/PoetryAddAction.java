/**
 * 
 */
package org.python.pydev.ui.actions.project;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.python.pydev.core.package_manager.PoetryPackageManager;
import org.python.pydev.shared_ui.EditorUtils;
import org.python.pydev.ui.actions.container.PyContainerAction;

/**
 * 
 */
public class PoetryAddAction extends PyContainerAction {
    private IProject project;

    @Override
    protected boolean confirmRun() {
        return true;
    }

    @Override
    protected void afterRun(int resourcesAffected) {
    }

    @Override
    protected boolean needsUIThread() {
        return true;
    }

    @Override
    protected int doActionOnContainer(IContainer container, IProgressMonitor monitor) {
        monitor.beginTask("Installing python package...", 100);
        monitor.worked(50);
        project = container.getProject();
        String path = project.getLocation().toString();
        PoetryPackageManager pm = new PoetryPackageManager(path);
//        pm.Commands;
        InputDialog dialog = new InputDialog(EditorUtils.getShell(), "App to install",
                "Name of the python App to install", null, null);
        int open = dialog.open();
        if (open != Window.OK) {
            return -1;
        }
        String appName = dialog.getValue();
        pm.add(appName);
        monitor.worked(100);
        monitor.done();
        return 0;
    }
}
