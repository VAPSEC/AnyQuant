package presentation.panel.operation;

import po.IndexPO;
import presentation.UltraSwing.UltraButton;
import presentation.frame.MainFrame;
import presentation.graphs.LineChartMarketIndex;
import presentation.panel.info.IndexBriefInfoPanel;
import presentation.panel.info.IndexInfoPanel;
import presentation.util.DateChooser;
import vo.IndexVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by 宋益明 on 16-3-2.
 * <p>
 * 大盘指数面板
 * 包含简要信息（中文名称                        今开     最高     成交量)
 *           (  ID        涨跌额（涨跌幅）      昨收     最低     成交额)
 *    及k-线图
 */
public class MarketIndexPanel extends OperationPanel {

    /**
     * 显示详细数据按钮
     */
    private UltraButton btnData;

    /**
     * 简要信息面板
     */
    private JPanel briefInfoPanel;

    /**
     * 图表面板
     */
    private JPanel chartPanel;

    public MarketIndexPanel() {
        init();
        createUIComponents();
        addListeners();
    }

    protected void init() {
        this.setLayout(null);
    }

    protected void createUIComponents() {
        new DateChooser(this, MARGIN, MARGIN, BUTTON_WIDTH * 2, BUTTON_HEIGHT);

        briefInfoPanel = new IndexBriefInfoPanel(new IndexVO(new IndexPO(3)));
        try {
            chartPanel = new MarketIndexDetailPanel();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.getMainFrame(), "请检查网络链接！");
        }
        btnData = new UltraButton("详细数据");

        add(briefInfoPanel);
        add(chartPanel);
        add(btnData);
    }

    /**
     * 添加事件监听器
     */
    private void addListeners() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                btnData.setBounds(PANEL_WIDTH - MARGIN * 2 - BUTTON_WIDTH, MARGIN,
                        BUTTON_WIDTH + MARGIN, BUTTON_HEIGHT);
                briefInfoPanel.setBounds(MARGIN, MARGIN + BUTTON_HEIGHT + PADDING / 2,
                        PANEL_WIDTH - MARGIN * 2, BUTTON_HEIGHT + PADDING);
                chartPanel.setBounds(MARGIN, briefInfoPanel.getY() + briefInfoPanel.getHeight() + PADDING / 2,
                        PANEL_WIDTH - MARGIN * 2, PANEL_HEIGHT - getX() - MARGIN);

                revalidate();
                repaint();
            }
        });

        btnData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.getMainFrame().addOperationPanel(new IndexInfoPanel(MarketIndexPanel.this));
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
