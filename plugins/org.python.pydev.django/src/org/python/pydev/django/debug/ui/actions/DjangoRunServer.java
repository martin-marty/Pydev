package org.python.pydev.django.debug.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.python.pydev.shared_ui.EditorUtils;

public class DjangoRunServer extends DjangoAction {

    @Override
    public void run(IAction action) {
        IInputValidator validator = new IInputValidator() {

            @Override
            public String isValid(String newText) {
                if (newText.trim().length() == 0) {
                    return "Port cannot be empty";
                }
                return null;
            }
        };
        InputDialog d = new InputDialog(EditorUtils.getShell(), "Server port", "Port for the server to listen on",
                "8000",
                validator);

        int retCode = d.open();
        if (retCode == InputDialog.OK) {
            runServer(d.getValue().trim());
        }
    }

    private void runServer(String port) {
        try {
            launchDjangoCommand("runserver " + port + " --noreload", false, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
