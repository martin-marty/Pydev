package org.python.pydev.ui.actions.project;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.python.pydev.core.log.Log;
import org.python.pydev.core.package_manager.PoetryPackageManager;
import org.python.pydev.ui.actions.container.PyContainerAction;

public class PoetrySync extends PyContainerAction {
    private IPath absPath;
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
        monitor.beginTask("Syncronising project dependencies...", 100);
        monitor.worked(1);
        if (monitor.isCanceled()) {
            return 0;
        }

        project = container.getProject();
        absPath = project.getLocation();
        PoetryPackageManager pm = new PoetryPackageManager(absPath.toString());
        pm.sync();
        monitor.worked(50);
        try {
            project.refreshLocal(1, monitor);
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            Log.log(e);
        }
        monitor.worked(100);
        monitor.done();
        return 0;
    }
}
