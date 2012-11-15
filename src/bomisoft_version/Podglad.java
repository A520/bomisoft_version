/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomisoft_version;

import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class Podglad extends javax.swing.JFrame {

   /**
    * Wywołuje pełną aktualizacje danych na podstawie wskazanego obiektu DB
    * @param cel wskazany obiekt DB
    */
   public void WERSJA(){
       this.WER_APLI.setText("Wersja aplikacji: " + GlobalDataStore.VERSION);
   }
   public void Aktualizacja(DB cel){
       ID(cel);
       BLOZ(cel);
       BLSM(cel);
       VERS(cel);
       REC(cel);
       RAP(cel);
   } 
   /**
    * Wprowadzenie do pola ID danych z obiektu DB
    * @param cel wskazany obiekt DB
    */
   public void ID(DB cel){
       cel.GET_ID();
       this.ID_a.setText(Integer.toString(cel.ID));
   }
   /**
    * Wprowadzenie do pola BLOZ danych z obiektu DB
    * @param cel wskazany obiekt DB
    */
   public void BLOZ(DB cel){
       cel.GET_BLOZ();
       this.BLOZ.setText(cel.BLOZ);
   }
   /**
    * Wprowadzenie do pola ID danych z obiektu DB
    * @param cel wskazany obiekt DB
    */
   public void BLSM(DB cel){
       cel.GET_BLOZ_SM();
       this.BLSM.setText(cel.BLSM);
   }
   public void VERS(DB cel){
       cel.GET_AKT();
       this.PROGV.setText(cel.VER);
       this.VERS.setText(cel.AKT);
   }
   public void REC(DB cel){
       cel.GET_REC();
       this.REC.setText(cel.REC_BAZ);
   }
   public void RAP(DB cel){
       cel.GET_RAPORT();
       this.RAP2.setText(cel.RAP);
   }
    /**
     * Creates new form Podglad
     */
    public Podglad() {
        initComponents();
        DB local_srv = new DB();
        local_srv.PROP(GlobalDataStore.jarDir + "/db.properties");
        local_srv.CONNECT();
        Aktualizacja(local_srv);
        local_srv.DISCONNECT();
    }
    public void Odswiez(){
        DB local_srv = new DB();
        local_srv.PROP(GlobalDataStore.jarDir + "/db.properties");
        local_srv.CONNECT();
        Aktualizacja(local_srv);
        local_srv.DISCONNECT();
    }
    
    public void KonfLoad(){
        DB local_srv = new DB();
        local_srv.PROP(GlobalDataStore.jarDir + "/db.properties");
        //local_srv.CONNECT();
        if("oracle".equals(local_srv.TYP.toLowerCase()))
            TYPY.setSelectedIndex(0);
        else
            TYPY.setSelectedIndex(1);
        HOSTY.setText(local_srv.SHOW_HOST());
        PORTY.setText(local_srv.SHOW_PORT());
        SCHEMATY.setText(local_srv.SHOW_SCHEM());
        USERY.setText(local_srv.SHOW_USER());
        PASSY.setText("****");
        KATALOGI.setText(local_srv.SHOW_DIR());
        //local_srv.DISCONNECT();
    }

    public boolean KonfSave(){
        Properties prop = new Properties();
        try {
    		//save properties to project root folder
                if(PASSY.getText().matches(".*\\*"))
                {
                    System.out.println("Musisz podac hasło!!");
                    if(GlobalDataStore.IfGUI)
                        JOptionPane.showMessageDialog(null, "Hasło nie może zawierać * (gwiazdek) !!");
                    return false;
                }
                else
                {
                    prop.store(new FileOutputStream(GlobalDataStore.jarDir + "/db.properties"), null);
                    if(GlobalDataStore.IfGUI)
                        JOptionPane.showMessageDialog(null, "Zapisano ustawienia");
                    //set the properties value
                    String tmpTYP;
                    if(TYPY.getSelectedIndex()==0)
                        tmpTYP = "oracle";
                    else
                        tmpTYP = "firebird";
        		prop.setProperty("typ", tmpTYP);
        		prop.setProperty("host", HOSTY.getText());
        		prop.setProperty("port", PORTY.getText());
                    prop.setProperty("schem", SCHEMATY.getText());
                    prop.setProperty("user", USERY.getText());
                    prop.setProperty("pass", PASSY.getText());
                    prop.setProperty("dir", KATALOGI.getText());
                    return true;
                }
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
                return false;
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ID_a = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        BLOZ = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        BLSM = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        VERS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        REC = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        RAP = new javax.swing.JScrollPane();
        RAP2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        WYSLIJ = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        PROGV = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        PANELAKT = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        TYPY = new javax.swing.JComboBox();
        HOSTY = new javax.swing.JTextField();
        PORTY = new javax.swing.JTextField();
        SCHEMATY = new javax.swing.JTextField();
        USERY = new javax.swing.JTextField();
        KATALOGI = new javax.swing.JTextField();
        PASSY = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        WER_APLI = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bomisoft Version");

        jLabel1.setText("ID apteki:");

        jLabel2.setText("BLOZ:");

        jLabel3.setText("BLSM:");

        jLabel4.setText("Wersja:");

        jLabel5.setText("Recepty z:");

        jLabel6.setText("Raporty:");

        RAP2.setColumns(20);
        RAP2.setRows(5);
        RAP.setViewportView(RAP2);

        jButton1.setText("ODŚWIEŻ");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        WYSLIJ.setText("WYSLIJ");
        WYSLIJ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                WYSLIJMouseClicked(evt);
            }
        });

        jLabel14.setText("Aktualizacja:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ID_a, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(BLOZ)
                            .addComponent(BLSM))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(VERS, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(REC)
                            .addComponent(PROGV))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(WYSLIJ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(RAP)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(ID_a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addComponent(PROGV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(BLOZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(VERS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(BLSM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(REC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(7, 7, 7)
                        .addComponent(WYSLIJ)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 295, Short.MAX_VALUE))
                    .addComponent(RAP))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Informacje", jPanel3);

        jButton2.setText("Załaduj");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("Aktualizuj");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jLabel7.setText("TYP");

        jLabel8.setText("HOST");

        jLabel9.setText("PORT");

        jLabel10.setText("SCHEMAT");

        jLabel11.setText("USER");

        jLabel12.setText("PASS");

        jLabel13.setText("KATALOG");

        TYPY.setEditable(true);
        TYPY.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Oracle", "FireBird" }));
        TYPY.setToolTipText("");
        TYPY.setEnabled(false);
        TYPY.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TYPYItemStateChanged(evt);
            }
        });

        HOSTY.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        HOSTY.setText("127.0.0.1");
        HOSTY.setEnabled(false);

        PORTY.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        PORTY.setText("1521");
        PORTY.setEnabled(false);

        SCHEMATY.setText("XE");
        SCHEMATY.setEnabled(false);

        USERY.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        USERY.setText("apw_user");
        USERY.setEnabled(false);

        KATALOGI.setText("C:/KS/APW/BACKUP");
        KATALOGI.setEnabled(false);

        PASSY.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        PASSY.setText("****");
        PASSY.setEnabled(false);

        javax.swing.GroupLayout PANELAKTLayout = new javax.swing.GroupLayout(PANELAKT);
        PANELAKT.setLayout(PANELAKTLayout);
        PANELAKTLayout.setHorizontalGroup(
            PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PANELAKTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(60, 60, 60)
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCHEMATY)
                    .addComponent(PORTY)
                    .addComponent(HOSTY)
                    .addComponent(TYPY, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(USERY)
                    .addComponent(KATALOGI, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(PASSY))
                .addContainerGap(246, Short.MAX_VALUE))
        );
        PANELAKTLayout.setVerticalGroup(
            PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PANELAKTLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TYPY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(HOSTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(PORTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(SCHEMATY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(USERY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(PASSY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PANELAKTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(KATALOGI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(168, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(PANELAKT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(PANELAKT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Konfiguracja", jPanel4);

        jLabel15.setText("Autor: Opłotny Marek");

        jLabel16.setText("Firma: Bomisoft Sp. z o. o.");

        WER_APLI.setText("Wersja aplikacji: null");

        jLabel17.setText("Kontakt: oplotny.marek@gmail.com");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(WER_APLI)
                    .addComponent(jLabel17))
                .addContainerGap(546, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WER_APLI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addContainerGap(360, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Kontakt", jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        Odswiez();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        KonfLoad();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        if("Aktualizuj".equals(jButton3.getText()))
        {
            TYPY.setEnabled(true);
            HOSTY.setEnabled(true);
            PORTY.setEnabled(true);
            SCHEMATY.setEnabled(true);
            USERY.setEnabled(true);
            PASSY.setEnabled(true);
            KATALOGI.setEnabled(true);
            jButton3.setText("Zapisz");
        }
        else
        {
            if(KonfSave())
            {
                TYPY.setEnabled(false);
                HOSTY.setEnabled(false);
                PORTY.setEnabled(false);
                SCHEMATY.setEnabled(false);
                USERY.setEnabled(false);
                PASSY.setEnabled(false);
                KATALOGI.setEnabled(false);
                jButton3.setText("Aktualizuj");
                KonfLoad();
            }
                
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void WYSLIJMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WYSLIJMouseClicked
        // TODO add your handling code here:
        DB local_srv = new DB();
        local_srv.PROP("/db.properties");
        local_srv.CONNECT();
        local_srv.GET_BLOZ();
        local_srv.GET_BLOZ_SM();
        local_srv.GET_ID();
        local_srv.GET_AKT();
        local_srv.GET_REC();
        local_srv.GET_RAPORT();
        local_srv.DISCONNECT();
        DB dest_srv = new DB();
        GlobalDataStore.downloadPropWithAuth("http://37.28.152.194/auth/dest.properties", "A520", "rce", dest_srv);
        dest_srv.CONNECT();
        if (local_srv.conn != null) {
                if (local_srv.ID != 0) {
                    //Wysłanie raportu do serwera docelowego
                    dest_srv.SEND_RAPORT(local_srv);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Brak danych do wysłania!!");
                    //System.out.println("Brak danych do wysłania!!");
                }
            }
        dest_srv.DISCONNECT();
    }//GEN-LAST:event_WYSLIJMouseClicked

    private void TYPYItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TYPYItemStateChanged
        // TODO add your handling code here:
        if(this.TYPY.getSelectedIndex()==0)
        {
            this.PORTY.setText("1521");
            this.SCHEMATY.setText("XE");
        }
        else
            if(this.TYPY.getSelectedIndex()==1)
            {
                this.PORTY.setText("3050");
                this.SCHEMATY.setText("C:/KSBAZA/KS-APW/wapteka.fdb");
            }
    }//GEN-LAST:event_TYPYItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Podglad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Podglad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Podglad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Podglad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Podglad().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BLOZ;
    private javax.swing.JTextField BLSM;
    private javax.swing.JTextField HOSTY;
    private javax.swing.JTextField ID_a;
    private javax.swing.JTextField KATALOGI;
    private javax.swing.JPanel PANELAKT;
    private javax.swing.JTextField PASSY;
    private javax.swing.JTextField PORTY;
    private javax.swing.JTextField PROGV;
    private javax.swing.JScrollPane RAP;
    private javax.swing.JTextArea RAP2;
    private javax.swing.JTextField REC;
    private javax.swing.JTextField SCHEMATY;
    private javax.swing.JComboBox TYPY;
    private javax.swing.JTextField USERY;
    private javax.swing.JTextField VERS;
    private javax.swing.JLabel WER_APLI;
    private javax.swing.JButton WYSLIJ;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
