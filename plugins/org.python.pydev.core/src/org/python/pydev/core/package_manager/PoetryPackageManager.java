package org.python.pydev.core.package_manager;

import java.util.ArrayList;

public class PoetryPackageManager extends BasePackageManager {
    final private static String PREFS_NAME = "POETRY_BIN";
    private String pythonPath;

    public PoetryPackageManager(String projectRoot) {
        super(projectRoot, "poetry", PREFS_NAME, "-C");
    }

    @Override
    protected ArrayList<String> getCommandArgs(String args) {
        ArrayList<String> parsedArgs = new ArrayList<String>();
        parsedArgs.add(args);
        return parsedArgs;
    }

    @Override
    protected ArrayList<String> getCommandArgs(String[] args) {
        ArrayList<String> parsedArgs = new ArrayList<String>();
        for (String arg : args) {
            parsedArgs.add(arg);
        }
        return parsedArgs;
    }

    public String install() {
        return runCommand(getCommandArgs("install"));
    }

    public String add(String appName) {
        ArrayList<String> args = getCommandArgs("add");
        String[] apps = appName.split(" ");
        for (String app : apps) {
            args.add(app);
        }
        return runCommand(args);
    }

    public String lock() {
        return runCommand(getCommandArgs("lock"));
    }

    public String sync() {
        return runCommand(getCommandArgs("sync"));
    }

    /**
     * Returns the path to the python executable of a pyproject virtual environment.
     * 
     * @return the path to python
     */
    public String getPython() {
        if (pythonPath == null) {
            pythonPath = runCommand(getCommandArgs(new String[] { "env", "info", "-e" }));
        }
        return pythonPath;
    }

}
