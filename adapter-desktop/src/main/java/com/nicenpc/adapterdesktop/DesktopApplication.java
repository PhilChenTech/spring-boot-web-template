package com.nicenpc.adapterdesktop;

import com.formdev.flatlaf.FlatLightLaf;
import com.nicenpc.application.UserService;
import com.nicenpc.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 桌面應用程式主窗口
 * 只有在 desktop 模式下才會啟動
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.type", havingValue = "desktop")
public class DesktopApplication implements CommandLineRunner {
    
    private final UserService userService;
    private JFrame mainFrame;
    private JTextArea outputArea;
    private JTextField nameField;
    private JTextField emailField;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting Desktop Application...");
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }
    
    private void createAndShowGUI() {
        try {
            // 設定現代化的 Look and Feel
            FlatLightLaf.setup();
        } catch (Exception e) {
            log.warn("Failed to set FlatLaf, using default Look and Feel", e);
        }
        
        mainFrame = new JFrame("Nice NPC Desktop Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        
        // 建立主要面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 建立頂部控制面板
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        
        // 建立輸出區域
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("輸出"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // 建立狀態列
        JLabel statusLabel = new JLabel("就緒");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        
        log.info("Desktop application window created successfully");
        appendOutput("桌面應用程式已啟動！\n");
        appendOutput("您可以使用上方的控制面板來管理用戶。\n\n");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("用戶管理"));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // 名稱輸入
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("姓名:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);
        
        // 電子郵件輸入
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        // 按鈕面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton createButton = new JButton("建立用戶");
        createButton.addActionListener(this::createUser);
        buttonPanel.add(createButton);
        
        JButton listButton = new JButton("列出所有用戶");
        listButton.addActionListener(this::listUsers);
        buttonPanel.add(listButton);
        
        JButton clearButton = new JButton("清除輸出");
        clearButton.addActionListener(e -> outputArea.setText(""));
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private void createUser(ActionEvent e) {
        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            
            if (name.isEmpty() || email.isEmpty()) {
                appendOutput("錯誤：姓名和電子郵件都是必填項目\n");
                return;
            }
            
            User user = userService.createUser(name, email);
            appendOutput(String.format("成功建立用戶：ID=%d, 姓名=%s, Email=%s\n", 
                user.getId(), user.getName(), user.getEmail()));
            
            // 清除輸入欄位
            nameField.setText("");
            emailField.setText("");
            
        } catch (Exception ex) {
            log.error("建立用戶時發生錯誤", ex);
            appendOutput("建立用戶失敗：" + ex.getMessage() + "\n");
        }
    }
    
    private void listUsers(ActionEvent e) {
        try {
            List<User> users = userService.getAllUsers();
            appendOutput("\n=== 所有用戶列表 ===\n");
            
            if (users.isEmpty()) {
                appendOutput("目前沒有用戶記錄\n");
            } else {
                for (User user : users) {
                    appendOutput(String.format("ID: %d | 姓名: %s | Email: %s\n", 
                        user.getId(), user.getName(), user.getEmail()));
                }
            }
            appendOutput("==================\n\n");
            
        } catch (Exception ex) {
            log.error("取得用戶列表時發生錯誤", ex);
            appendOutput("取得用戶列表失敗：" + ex.getMessage() + "\n");
        }
    }
    
    private void appendOutput(String text) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(text);
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }
}
