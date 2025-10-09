/**
 * 
 */
package org.python.pydev.poetry.ui.actions;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.python.pydev.plugin.PydevPlugin;
import org.python.pydev.plugin.preferences.PydevRootPrefs;
import org.python.pydev.shared_ui.utils.UIUtils;

/**
 * Run a poetry command and return the result. Uses a process builder to execute
 * a command using poetry as the executable.
 */
public class PoetryAction {
    public enum Commands {
        INSTALL("install"), ENV("env"), LOCK("lock"), SYNC("sync"), UPDATE("update");

        private String command;

        private Commands(String command) {
            this.command = command;
        }

        public String command() {
            return this.command;
        }
    }

    private String errorTitle;
    private String errorMsg;
    private String projectRoot;
    private String pythonPath;
    public Boolean OK = true;
    public String poetryBin;

    /**
     * 
     * @param projectRoot The path to the directory containing pyproject.toml
     */
    public PoetryAction(String projectRoot) {
        poetryBin = PydevPlugin.getDefault().getPreferenceStore().getString(PydevRootPrefs.POETRY_BIN);
        if (poetryBin == null) {
            errorTitle = "Poetry not configured";
            errorMsg = "The path to poetry could not be found.\n";
            errorMsg += "Please configure the poetry in";
            OK = false;
        }
        this.projectRoot = projectRoot;
    }

    private Boolean checkError() {
        if (errorMsg != null) {
            Shell shell = UIUtils.getActiveShell();
            MessageDialog.openError(shell, errorTitle, errorMsg);
            return false;
        }
        return true;
    }

    /**
     * Returns the path to the python executable of a pyproject virtual environment.
     * 
     * @return the path to python
     */
    public String getPython() {
        if (pythonPath == null) {
            String[] args = { Commands.ENV.command(), "info", "-e" };
            pythonPath = runCommand(args);
        }
        return pythonPath;
    }

    /**
     * Install the project
     * 
     * @return result of `poetry install`
     */
    public String install() {
        return runCommand(new String[] { Commands.INSTALL.command() });
    }

    public String sync() {
        return runCommand(new String[] { Commands.SYNC.command() });
    }

    public String lock() {
        return runCommand(new String[] { Commands.LOCK.command() });
    }

    /**
     * Returns the output of running `poetry args...`
     * 
     * @param args List of arguments to pass to poetry e.g. "[poetry] env info"
     * @return The command output
     */
    public String runCommand(String[] args) {
        String output = null;
        Boolean ok = checkError();
        if (!ok) {
            return output;
        }

        ArrayList<String> poetryArgs = new ArrayList<String>();
        poetryArgs.add(poetryBin);

        for (String a : args) {
            poetryArgs.add(a);
        }
        poetryArgs.add("-C");
        poetryArgs.add(projectRoot);

        ProcessBuilder pb = new ProcessBuilder(poetryArgs);
        Process p;
        try {
            p = pb.start();
            output = new String(p.getInputStream().readAllBytes()).strip();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return output;
    }

}
