package org.python.pydev.poetry.ui.actions.project;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.python.pydev.poetry.ui.actions.PoetryAction;
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
        PoetryAction poetry = new PoetryAction(absPath.toString());
        poetry.sync();
        monitor.worked(50);
        monitor.done();
        try {
            project.refreshLocal(1, monitor);
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        monitor.worked(100);
        return 0;
    }
}
