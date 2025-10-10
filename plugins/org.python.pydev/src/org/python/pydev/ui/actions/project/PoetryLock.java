package org.python.pydev.ui.actions.project;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.python.pydev.core.log.Log;
import org.python.pydev.core.package_manager.PoetryPackageManager;
import org.python.pydev.ui.actions.container.PyContainerAction;

public class PoetryLock extends PyContainerAction {

    @Override
    protected boolean confirmRun() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void afterRun(int resourcesAffected) {
        // TODO Auto-generated method stub

    }

    @Override
    protected int doActionOnContainer(IContainer container, IProgressMonitor monitor) {
        monitor.beginTask("Locking project dependencies...", 100);
        monitor.worked(1);
        if (monitor.isCanceled()) {
            return 0;
        }

        IProject project = container.getProject();
        IPath absPath = project.getLocation();
        PoetryPackageManager pm = new PoetryPackageManager(absPath.toString());
        pm.lock();
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
