package presentation.frame;

import presentation.panel.BackgroundPanel;
import presentation.panel.MenuPanel;
import presentation.panel.operation.OperationPanel;
import presentation.util.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-3-2.
 * <p>
 * 主界面
 */
public final class MainFrame extends JFrame {

    private static final int width;//683
    private static final int height;//512

    private static final MainFrame frame;

    private static final BackgroundPanel panel;

    private static Dimension screen;

    private static OperationPanel operationPanel;

    private MainFrame() {
    }

    static {
        screen = Toolkit.getDefaultToolkit().getScreenSize();

        width = screen.width / 2;
        height = width * 3 / 4;

        frame = new MainFrame();
        panel = new BackgroundPanel(ImageLoader.background);

        init();
        createUIComponents();
    }

    public static MainFrame getMainFrame() {
        return frame;
    }

    /**
     * 初始化窗体
     */
    private static void init() {
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setLayout(null);
        frame.setIconImage(ImageLoader.icon);

        panel.setLayout(null);

        operationPanel = new OperationPanel();
    }

    private static void createUIComponents() {
        MenuPanel menuPanel = new MenuPanel();
        menuPanel.setBounds(0, 0, frame.getWidth() / 5, frame.getHeight());
        panel.add(menuPanel);

        operationPanel.setBounds(frame.getWidth() / 5, 0, frame.getWidth(), frame.getHeight());
    }

    public void addOperationPanel(OperationPanel o) {
        panel.remove(operationPanel);
        operationPanel = o;
        panel.add(o);

        repaint();
    }
}

