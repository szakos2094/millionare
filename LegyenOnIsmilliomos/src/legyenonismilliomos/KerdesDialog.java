/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legyenonismilliomos;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.UIManager;
import model.IModel;
import model.Kerdes;


/**
 *
 * @author Akos
 */
public class KerdesDialog extends javax.swing.JDialog {

    private IModel model;
    private java.awt.Frame parent;

    private void listaFeltolt() {
        try {
            List<Kerdes> kerdesek = model.getAllKerdes();
            lstKerdesek.setListData(kerdesek.toArray()); 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex, "Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public KerdesDialog(java.awt.Frame parent, boolean modal, IModel model) {
        super(parent, modal);
        initComponents();
        this.model = model; // ez is az IModel-en keresztül fog kapcsolódni az adatbázishoz IModel model-t kellett adni a SzemelyekGialoghoz
        this.parent = parent;
        setTitle("Kérdések kezelése");  // Ablak fejléc megnevezése
        setLocationRelativeTo(parent);  // Középre helyezi az indításnál parent a szülő ablakhoz igazítja
        listaFeltolt();
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
        lstKerdesek = new javax.swing.JList();
        btnUj = new javax.swing.JButton();
        btnModosit = new javax.swing.JButton();
        btnTorol = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lstKerdesek.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(lstKerdesek);

        btnUj.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnUj.setText("Új");
        btnUj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUjActionPerformed(evt);
            }
        });

        btnModosit.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnModosit.setText("Módosít");
        btnModosit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModositActionPerformed(evt);
            }
        });

        btnTorol.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnTorol.setText("Töröl");
        btnTorol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTorolActionPerformed(evt);
            }
        });

        btnOK.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUj, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModosit)
                    .addComponent(btnTorol)
                    .addComponent(btnOK))
                .addGap(28, 28, 28))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnModosit, btnOK, btnTorol, btnUj});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUj, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnModosit)
                        .addGap(18, 18, 18)
                        .addComponent(btnTorol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
                        .addComponent(btnOK))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnModosit, btnOK, btnTorol, btnUj});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
       setVisible(false);  // bezárja az ablakot
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnUjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUjActionPerformed
     KerdesAdatokDialog kad = new KerdesAdatokDialog(parent, true, null);
     kad.setVisible(true);
     // amíg nyitva van az KerdesAdatokDialog ablak addig itt megáll a program
     
        if (kad.isMentes()) {
            Kerdes kerdes = kad.getKerdes();
            
         try {
             model.addKerdes(kerdes); 
             listaFeltolt();
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(rootPane, ex, "Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
         }
        }
    }//GEN-LAST:event_btnUjActionPerformed

    private void btnModositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModositActionPerformed
        Kerdes selected = (Kerdes) lstKerdesek.getSelectedValue();
        if (selected != null) {
            KerdesAdatokDialog szad = new KerdesAdatokDialog(parent,true,selected);
            szad.setVisible(true);
            // amíg nyitva van az KerdesAdatokDialog ablak addig itt megáll a program
            if (szad.isMentes()) {
                try {
                   model.updateKerdes(selected);
                   listaFeltolt();
                } catch (SQLException ex) {
                   JOptionPane.showMessageDialog(rootPane, ex, "Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
                }
            }        
        }else {
            JOptionPane.showMessageDialog(rootPane, "Válassz kérdést a módosításhoz", "Kiválasztási hiba", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModositActionPerformed

    private void btnTorolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTorolActionPerformed
        Kerdes selected = (Kerdes) lstKerdesek.getSelectedValue();
        UIManager.put("OptionPane.yesButtonText", "Igen"); // A biztos törölni szeretnéfd ablak gombok szövege
        UIManager.put("OptionPane.noButtonText", "Nem");  // A biztos törölni szeretnéfd ablak gombok szövege
        if (selected != null) {
            try {
                    model.removeKerdes(selected);
                  } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(rootPane, ex, "Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
                  }
                    int ablakbiztos = JOptionPane.showConfirmDialog(null, "Biztos törölni szeretnéd ?", "Kérdés törlése", JOptionPane.YES_NO_OPTION);
                    // 0=Igen, 1=Nem
                if (ablakbiztos == 0 ) {
                    listaFeltolt();  // Lista frissítése az lstKerdesek ablakban
                    showMessageDialog(null, "A kérdés törölve.");
                }
                  
        } else {
            JOptionPane.showMessageDialog(rootPane, "Válassz kérdést a törléshez", "Kiválasztási hiba", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTorolActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModosit;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnTorol;
    private javax.swing.JButton btnUj;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstKerdesek;
    // End of variables declaration//GEN-END:variables
}
