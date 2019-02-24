/*
 Az alábbi javításokat kell elvégezni:
- Formázzuk az összegek kiírását --> Garantált nyeremény vastagítása hiányzik
- Legyen kérdés szerkesztő 
    - kérdés szerkesztő belépéséhez kérjen jelszót
    - belépés után listázza ki a kérdéseket
    - A kilistázott kérdéseket lehessen szerkeszteni vagy törölni
    - Lehessen új kérdést felvinni
    - Kérdésekre a válasz max 60 karakter lehet
- A játék végén vagy vesztés esetén lehessen újra indítani
 */
package legyenonismilliomos;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.DBModel;
import model.IModel;
import model.Kerdes;
import model.Nyeremeny;

/**
 *
 * @author Akos
 */
public class MilliomosFoablak extends javax.swing.JFrame {

    private IModel model;
    int kerdesnr = 0;   // A kérdése száma a játék alatt
    int btn = 0;
    int valasz = 0;     // A válasz száma 0-3 mert 4 válasz lehetőség van
    int nyeremenysor = 0;
    int garnyer = 0;
    List<Kerdes> kerdesek;  // A kérdések listája
    List<Nyeremeny> nyeremenyek;
    ArrayList<Integer> kerdesszam;
    
   
    

    public void exitProc() {
        if (model != null) {
            try {
                model.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex, "Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    // A kérdések sorsolása
    public void sorsol() {
        Random rnd = new Random();       
        kerdesnr = rnd.nextInt(kerdesek.size());
    }

    public MilliomosFoablak() {
        initComponents();  // összerakja a grafikus elrendezést
        lstNyeremeny.setForeground(Color.orange); // A nyeremény lista szöveg színe
        jlKerdes.setForeground(Color.white); // A kérdés szöveg színe
        btnValasz1.setForeground(Color.white); // A gomb szöveg színe
        btnValasz2.setForeground(Color.white); // A gomb szöveg színe
        btnValasz3.setForeground(Color.white); // A gomb szöveg színe
        btnValasz4.setForeground(Color.white); // A gomb szöveg színe
        

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProc();  // külön metódus az átláthatóság kedvéért
                dispose();  // megszünteti a JFram-et
                System.exit(0);  // kilép a programból
            }
        });

        setLocationRelativeTo(null);
        setTitle("Legyen Ön is Milliomos V0.1");

        String connURL = "jdbc:mysql://localhost:3306/java_vizsga";
        String user = "root";
        String pass = "12345678";

        Connection conn;
        try {
            conn = DriverManager.getConnection(connURL, user, pass);
            model = new DBModel(conn);
            kerdesek = model.getAllKerdes();
            System.out.println("Kerdesek: "+kerdesek.size());
            System.out.println("kérdésszám: "+kerdesnr);
            
            List<Nyeremeny> nyeremenyek = model.getAllNyeremeny();
            Collections.reverse(nyeremenyek);  // megfordítja a lista sorrendjét
            lstNyeremeny.setListData(nyeremenyek.toArray());
            System.out.println("lstNyeremeny: "+nyeremenyek.get(14));
            lstNyeremeny.setSelectedIndex(lstNyeremeny.getLastVisibleIndex());
            
            kerdesszam = new ArrayList<Integer>();
            sorsol();
            listaKerdesFeltolt();
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex, "Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }
  
    private void Gombok() { 
           
        if (btn == valasz) {
            nyeremenysor++;
            if (lstNyeremeny.getSelectedIndex() <= 0) {
                  lstNyeremeny.setSelectedIndex(0);   
            } else {
                lstNyeremeny.setSelectedIndex(lstNyeremeny.getSelectedIndex() - 1);
            }

        } else if ( btn != valasz && nyeremenysor >= 5 && nyeremenysor < 10 ) {
            JOptionPane.showMessageDialog(rootPane, "A játéknak vége. A nyereménye 100 000 Forint", "Játék Vége", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        } else if ( btn != valasz && nyeremenysor >= 10 && nyeremenysor < 15 ) {
            JOptionPane.showMessageDialog(rootPane, "A játéknak vége. A nyereménye 1 500 000 Forint", "Játék Vége", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        } else 
               {
            JOptionPane.showMessageDialog(rootPane, "Sajnos veszített, vége a játéknak !", "Játék Vége", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        if (nyeremenysor == 15){
            JOptionPane.showMessageDialog(rootPane, "Ön nyert, gratulálok ! Nyereménye 40 000 000 Forint", "Játék Vége", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
            }
    }

    private void listaKerdesFeltolt() {
        
        jlKerdes.setText("  " + kerdesek.get(kerdesnr).getKerdes());
        btnValasz1.setText(kerdesek.get(kerdesnr).getValasz1());
        btnValasz2.setText(kerdesek.get(kerdesnr).getValasz2());
        btnValasz3.setText(kerdesek.get(kerdesnr).getValasz3());
        btnValasz4.setText(kerdesek.get(kerdesnr).getValasz4());
        valasz = kerdesek.get(kerdesnr).getHelyesvalasz();
        kerdesszam.add(kerdesnr); 
        System.out.println("valasz: "+ valasz);     // Helyes válasz kiratása konzolra
        System.out.println("kérdés száma:"+kerdesek.get(kerdesnr).getId());
    }
    
    // Ellenőrzi, hogy a kérdés volt-e ha igen újra sorsol
    private void kerdesellenoriz() {  // addig megy a ciklus amíg a random olyan számot nem ad ami nincs a listában
           while (kerdesszam.contains(kerdesnr)) {
               sorsol();
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstNyeremeny = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jlKerdes = new javax.swing.JLabel();
        btnValasz1 = new javax.swing.JButton();
        btnValasz2 = new javax.swing.JButton();
        btnValasz3 = new javax.swing.JButton();
        btnValasz4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));

        lstNyeremeny.setBackground(new java.awt.Color(0, 0, 102));
        lstNyeremeny.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
        lstNyeremeny.setFont(new java.awt.Font("Dialog", 0, 25)); // NOI18N
        jScrollPane1.setViewportView(lstNyeremeny);

        jLabel1.setBackground(new java.awt.Color(204, 204, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/legyenonismilliomos/milliomos logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addGap(70, 70, 70)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jlKerdes.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jlKerdes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0), 4, true));

        btnValasz1.setBackground(new java.awt.Color(0, 0, 102));
        btnValasz1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnValasz1.setText("jButton1");
        btnValasz1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 4));
        btnValasz1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnValasz1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnValasz1MouseExited(evt);
            }
        });
        btnValasz1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValasz1ActionPerformed(evt);
            }
        });

        btnValasz2.setBackground(new java.awt.Color(0, 0, 102));
        btnValasz2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnValasz2.setText("jButton2");
        btnValasz2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 4));
        btnValasz2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnValasz2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnValasz2MouseExited(evt);
            }
        });
        btnValasz2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValasz2ActionPerformed(evt);
            }
        });

        btnValasz3.setBackground(new java.awt.Color(0, 0, 102));
        btnValasz3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnValasz3.setText("jButton3");
        btnValasz3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 4));
        btnValasz3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnValasz3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnValasz3MouseExited(evt);
            }
        });
        btnValasz3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValasz3ActionPerformed(evt);
            }
        });

        btnValasz4.setBackground(new java.awt.Color(0, 0, 102));
        btnValasz4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnValasz4.setText("jButton4");
        btnValasz4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 4));
        btnValasz4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnValasz4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnValasz4MouseExited(evt);
            }
        });
        btnValasz4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValasz4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnValasz3, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnValasz4, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnValasz1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22)
                        .addComponent(btnValasz2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlKerdes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jlKerdes, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnValasz2)
                        .addGap(27, 27, 27)
                        .addComponent(btnValasz4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnValasz1)
                        .addGap(28, 28, 28)
                        .addComponent(btnValasz3, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnValasz1, btnValasz2, btnValasz3, btnValasz4});

        jMenu1.setText("Kilép");
        jMenu1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jMenuItem1.setText("Kilép");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Adatbázis");
        jMenu2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jMenuItem2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jMenuItem2.setText("Adatbázis");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnValasz1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValasz1ActionPerformed
        btn = 1; 
        Gombok();
        sorsol();
        kerdesellenoriz();
        listaKerdesFeltolt();
    }//GEN-LAST:event_btnValasz1ActionPerformed

    private void btnValasz2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValasz2ActionPerformed
        btn = 2; 
        Gombok();
        sorsol();
        kerdesellenoriz();
        listaKerdesFeltolt();
    }//GEN-LAST:event_btnValasz2ActionPerformed

    private void btnValasz3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValasz3ActionPerformed
        btn = 3; 
        Gombok();
        sorsol();
        kerdesellenoriz();
        listaKerdesFeltolt();
    }//GEN-LAST:event_btnValasz3ActionPerformed

    private void btnValasz4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValasz4ActionPerformed
        btn = 4;
        Gombok();
        sorsol();
        kerdesellenoriz();
        listaKerdesFeltolt();
    }//GEN-LAST:event_btnValasz4ActionPerformed

    private void btnValasz1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValasz1MouseEntered
        btnValasz1.setForeground(Color.gray);     
    }//GEN-LAST:event_btnValasz1MouseEntered

    private void btnValasz1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValasz1MouseExited
        btnValasz1.setForeground(Color.white); 
    }//GEN-LAST:event_btnValasz1MouseExited

    private void btnValasz2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValasz2MouseEntered
        btnValasz2.setForeground(Color.gray);    
    }//GEN-LAST:event_btnValasz2MouseEntered

    private void btnValasz2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValasz2MouseExited
       btnValasz2.setForeground(Color.white); 
    }//GEN-LAST:event_btnValasz2MouseExited

    private void btnValasz3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValasz3MouseEntered
        btnValasz3.setForeground(Color.gray);
    }//GEN-LAST:event_btnValasz3MouseEntered

    private void btnValasz3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValasz3MouseExited
        btnValasz3.setForeground(Color.white); 
    }//GEN-LAST:event_btnValasz3MouseExited

    private void btnValasz4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValasz4MouseEntered
        btnValasz4.setForeground(Color.gray);
    }//GEN-LAST:event_btnValasz4MouseEntered

    private void btnValasz4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValasz4MouseExited
       btnValasz4.setForeground(Color.white); 
    }//GEN-LAST:event_btnValasz4MouseExited

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        KerdesDialog kd = new KerdesDialog(this, true, model);
        kd.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
            java.util.logging.Logger.getLogger(MilliomosFoablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MilliomosFoablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MilliomosFoablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MilliomosFoablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MilliomosFoablak().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnValasz1;
    private javax.swing.JButton btnValasz2;
    private javax.swing.JButton btnValasz3;
    private javax.swing.JButton btnValasz4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlKerdes;
    private javax.swing.JList lstNyeremeny;
    // End of variables declaration//GEN-END:variables
}
