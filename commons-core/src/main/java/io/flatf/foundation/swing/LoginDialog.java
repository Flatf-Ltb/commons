package io.flatf.foundation.swing;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.BiConsumer;

public final class LoginDialog extends JDialog {

    private JPanel contentPane;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JTextField username;
    private JPasswordField password;
    private JButton buttonOK;
    private JButton buttonCancel;

    private final LoginHandler handler;

    public LoginDialog(String title, LoginHandler handler) {
        this.handler = handler;
        setTitle(title);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onClear());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                onClear();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onClear(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        handler.onLogin(username.getText(), String.valueOf(password.getPassword()));
    }

    private void onClear() {
        dispose();
    }

    public void showDialog() {
        this.pack();
        this.setVisible(true);
    }

    @FunctionalInterface
    public interface LoginHandler extends BiConsumer<String, String> {

        void onLogin(String username, String password);

        @Override
        default void accept(String s0, String s1) {
            onLogin(s0, s1);
        }
    }

}
