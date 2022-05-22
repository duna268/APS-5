/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import static chat.chat_server.dis;
import static chat.chat_server.dout;
import static chat.chat_server.s;
import static chat.chat_server.ss;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class chat_client extends javax.swing.JFrame {

//    static Socket s;
//    static DataInputStream dis;
//    static DataOutputStream dout;
    
    PreparedStatement ps = null;
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    
    /**
     * Creates new form chat_client
     */
    public chat_client() {
        initComponents();
    }
    
    String usuario;
    int id_criador;
    String nome_contato;
    int id_contato;
    
    String ultima_mensagem;
    int id_ultimo_envio;
    
    chat_client(String usuario, int id_criador, String nome_contato) {
        initComponents();
        this.usuario = usuario;
        this.id_criador = id_criador;
        this.nome_contato = nome_contato;
        
        lbl_usuario.setText(nome_contato);
        
        try{
            conn = dbConnections.getConnection();
            //Busca das mensagem q o contato enviou
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM CONTATOS WHERE NOME_CONTATO = '" + nome_contato + "'");
            if (rs.next()){
                id_contato = rs.getInt("ID_CONTATO");
                System.out.println(id_contato);
                System.out.println(id_criador);

            }
            rs2 = st.executeQuery("SELECT * FROM MENSAGEM WHERE (ID_ENVIO = " + id_criador + " AND ID_CONTATO = "+ id_contato +") OR (ID_ENVIO = "+ id_contato +" AND ID_CONTATO = "+ id_criador +") ORDER BY ID_MENSAGEM");
            while(rs2.next()){
                System.out.println(rs2.getInt("ID_MENSAGEM"));
                if(rs2.getInt("ID_ENVIO") == id_criador){
                    msg_area.setText(msg_area.getText()+"\n " + usuario + ": " + rs2.getString("CONTEUDO"));
                    id_ultimo_envio = id_criador;
                }
                else{
                    msg_area.setText(msg_area.getText()+"\n " + nome_contato + ": " + rs2.getString("CONTEUDO"));
                    id_ultimo_envio = id_contato;
                }
                ultima_mensagem = rs2.getString("CONTEUDO");
            }
            
        Timer timer = new Timer();
        timer.schedule(new VerificaMensagemNova(), 0, 4000);

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    class VerificaMensagemNova extends TimerTask {
        public void run() {
            try{
                st = conn.createStatement();
                rs = st.executeQuery("SELECT * FROM MENSAGEM WHERE (ID_ENVIO = " + id_criador + " AND ID_CONTATO = "+ id_contato +") OR (ID_ENVIO = "+ id_contato +" AND ID_CONTATO = "+ id_criador +") ORDER BY ID_MENSAGEM DESC LIMIT 1");
                if(rs.next()){
                    System.out.println(rs.getString("CONTEUDO"));
                    System.out.println(ultima_mensagem);
                    if(rs.getString("CONTEUDO").equals(ultima_mensagem) && rs.getInt("ID_ENVIO") == id_ultimo_envio){
//                    msg_area.setText(msg_area.getText()+"\n " + usuario + ": " + rs2.getString("CONTEUDO"));
//                    id_ultimo_envio = usuario;
                    } 
                    else{
                        try{
                            
                            rs2 = st.executeQuery("SELECT * FROM MENSAGEM WHERE (ID_ENVIO = " + id_criador + " AND ID_CONTATO = "+ id_contato +") OR (ID_ENVIO = "+ id_contato +" AND ID_CONTATO = "+ id_criador +") ORDER BY ID_MENSAGEM");
                            msg_area.setText("");
                            while(rs2.next()){
                                System.out.println(rs2.getInt("ID_MENSAGEM"));
                                if(rs2.getInt("ID_ENVIO") == id_criador){
                                    msg_area.setText(msg_area.getText()+"\n " + usuario + ": " + rs2.getString("CONTEUDO"));
                                    id_ultimo_envio = id_criador;
                                }
                                else{
                                    msg_area.setText(msg_area.getText()+"\n " + nome_contato + ": " + rs2.getString("CONTEUDO"));
                                    id_ultimo_envio = id_contato;
                                }
                                ultima_mensagem = rs2.getString("CONTEUDO");
                            }
                        }
                        catch(SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
                   
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        lbl_usuario = new javax.swing.JLabel();
        msg_send = new javax.swing.JButton();
        msg_text = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        lbl_usuario.setText("Cliente");

        msg_send.setText("Enviar");
        msg_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_sendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(msg_text)
                        .addGap(18, 18, 18)
                        .addComponent(msg_send)
                        .addGap(32, 32, 32))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_usuario)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_usuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg_send)
                    .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_sendActionPerformed
        try{
            
            conn = dbConnections.getConnection();
            ps = conn.prepareStatement("INSERT INTO MENSAGEM "
                                     + "(CONTEUDO,ID_ENVIO,ID_CONTATO) "
                                     + "VALUES "
                                     + "(?,?,?)");
            ps.setString(1, msg_text.getText().toLowerCase());
            ps.setInt(2, id_criador);
            ps.setInt(3, id_contato);
            ps.executeUpdate();
            msg_area.setText(msg_area.getText()+"\n " + usuario + ": " + msg_text.getText());
           
        }
        catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não Foi possivel enviar a mensagem", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_msg_sendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chat_client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chat_client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chat_client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chat_client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chat_client().setVisible(true);
            }
        });
        
        try{          
            String msgin="";
            s = new Socket("192.168.15.11",1201);
            dis = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            while(!msgin.equals("exit")){
                msgin = dis.readUTF();
                msg_area.setText(msg_area.getText()+"\n Server: " + msgin);
            }
        }
        catch(Exception e){
            
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_usuario;
    private static javax.swing.JTextArea msg_area;
    private javax.swing.JButton msg_send;
    private javax.swing.JTextField msg_text;
    // End of variables declaration//GEN-END:variables
}
