/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Narciso;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/*
 * @author conej
 */
public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    private  int tamTotal;
    private int progress;
    private  List<String> archivos;
    private boolean insertarTextoPrimera;
    ImageIcon img1;
 JLabel lseleccionada;
    public NewJFrame() {
       
        this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/ar.png")).getImage());
        this.tamTotal=0;
        this.progress=0;
        initComponents();
        this.archivos= new ArrayList<String>();
        this.buscar.setEnabled(false);
        insertarTextoPrimera=false;
        DefaultTableModel model=(DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
        
        this.pack();
        this.setResizable(false);
        this.setSize(559, 455);
        this.setLocationRelativeTo(null); //JUSTO DESPUES DE MI "setSize()"
        this.setVisible(true);
    }

   private void progresBar(){
      
      int p=((this.progress*this.tamTotal)%100);
     
       this.jProgressBar1.setValue(p);
       String cadena=""+p+"%";
       this.jProgressBar1.setString(cadena);
        this.jProgressBar1.setStringPainted (true);
        this.jProgressBar1.repaint();
        this.jProgressBar1.update(this.jProgressBar1.getGraphics());
   }
    private void buscarDocx(String ruta) {
      
        try {
            File archivo = new File(ruta);
        
            FileInputStream fis = new FileInputStream(archivo);
            InputStream entradaArch = fis; 
            
            //Se crea un documento que la POI entiende pasandole el stream
            XWPFDocument ardocx = new XWPFDocument(OPCPackage.open(fis));

            //instanciamos el obj para extraer contenido pasando el documento
            XWPFWordExtractor xwpf_we = new XWPFWordExtractor(ardocx);

            //Y leemos el texto usando ese objeto creado:
            //leer el texto para un .docx 
            String texto = xwpf_we.getText();
            if(texto.contains(frase.getText())){
                archivos.add(ruta);

            }
                // se imprime 
            //System.out.println(texto);
            
        } catch(Exception ex ) {

        }
    }
    private void  buscarDoc(String ruta){

        try {
            File archivo = new File(ruta);
            FileInputStream fis;
            fis = new FileInputStream(archivo);
            InputStream entradaArch = fis; 
             //Creamos el extractor pasandole el stream
             WordExtractor we = new WordExtractor(entradaArch);

        
            //Leemos y guardamos en un String
            String texto = we.getText();
            if(texto.contains(frase.getText())){
                archivos.add(ruta);
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void buscarTxt(){
        Scanner entrada = null;
        String linea;
        int numeroDeLinea = 1;
        boolean contiene = false;
        try {
            JFileChooser jf = new JFileChooser();
            File archivo = new File(this.Ruta.getText());
            entrada = new Scanner(archivo);
            while (entrada.hasNext()) { //mientras no se llegue al final del fichero
                linea = entrada.nextLine();  //se lee una línea
                
                if (linea.contains(this.frase.getText())) {   //si la línea contiene el texto buscado se muestra por pantalla         
                    
                    contiene = true;
                }
                numeroDeLinea++; //se incrementa el contador de líneas
            }

        } catch (NullPointerException e) {
          
        } catch (Exception e) {
           
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        
    }
    
    private void busqeudaArchivos(String f){
        // TODO add your handling code here:
        if(this.frase.getText()==""|| this.frase.getText()=="Debe introducir una frase para buscar");
        DefaultTableModel model=(DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        this.archivos.clear();
        
        File archivo1 = new File(f);
        File listadoArchivos[]=archivo1.listFiles();
        this.tamTotal+=listadoArchivos.length;
        //this.jProgressBar1.setMaximum(this.tamTotal);
        
        if(listadoArchivos==null || listadoArchivos.length==0){
            //JOptionPane.showMessageDialog(rootPane,"No hay archivos dentro de la carpeta");
        }else{
            for(int i=0; i<listadoArchivos.length;i++){
                this.progress++;
                File arch= listadoArchivos[i];
                progresBar();
//                for(int j=0;j<50000;j++){
//                }
                if(arch.isFile()){
                    
                    String nombre= arch.getName();
                    String salir=nombre.substring(0,1);
                  
                    if(salir=="~"){
                        
                        break;
                          
                    }else{
                        String extension= nombre.substring(nombre.lastIndexOf("."));
                       
                    
                    switch(extension){
                        case ".docx":{
                            buscarDocx(listadoArchivos[i].getAbsolutePath());
                            break;
                        }
                        case ".doc":{
                            buscarDoc(listadoArchivos[i].getAbsolutePath());
                            break;
                        }
                        case ".txt":{
                            //buscarTxt();
                            break;
                        }
                        default:{
                            
                        }
                    }
                    
                    }
                }else if( arch.isDirectory()){
                    busqeudaArchivos(listadoArchivos[i].getAbsolutePath());
                }
            }
           
        }
    }
    public void mostrarResultados(){
        DefaultTableModel model=(DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
         if(!archivos.isEmpty()){
                model.setRowCount(0);
                for(int i=0;i<archivos.size();i++){
                    String ruta=this.archivos.get(i);
                    File archivo = new File(ruta);
                    String nombreArchivo= archivo.getName();
                    model.addRow(new Object []{nombreArchivo,ruta});
                }
                this.jProgressBar1.setValue(100);
                this.jProgressBar1.setString ("Completado");
                this.jProgressBar1.setStringPainted (true);
            }else{
                JOptionPane.showMessageDialog(null, "No se han encontrado archivos con esa palabra");
                this.jProgressBar1.setValue(100);
                this.jProgressBar1.setString ("Completado");
                this.jProgressBar1.setStringPainted (true);
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

        jLabel2 = new javax.swing.JLabel();
        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFileChooser1 = new javax.swing.JFileChooser();
        jFrame3 = new javax.swing.JFrame();
        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        jDialog3 = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Ruta = new javax.swing.JTextField();
        SelectArchivo = new javax.swing.JButton();
        frase = new javax.swing.JTextField();
        buscar = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(32, 33, 35));

        jPanel2.setBackground(new java.awt.Color(32, 33, 35));

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Buscador de  textos");

        jPanel1.setBackground(new java.awt.Color(32, 33, 35));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Ruta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        SelectArchivo.setText("Seleccionar Archivo");
        SelectArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectArchivoActionPerformed(evt);
            }
        });

        frase.setForeground(new java.awt.Color(102, 102, 102));
        frase.setText("Inserte frase a buscar");
        frase.setToolTipText("");
        frase.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        frase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fraseMouseClicked(evt);
            }
        });

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SelectArchivo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(frase, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SelectArchivo))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(frase, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Ruta.getAccessibleContext().setAccessibleName("Ruta");

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Archivo", "Ruta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(25);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(0).setHeaderValue("Archivo");
            jTable1.getColumnModel().getColumn(0).setCellRenderer(new Celda());
            jTable1.getColumnModel().getColumn(1).setHeaderValue("Ruta");
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 17, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectArchivoActionPerformed
        // TODO add your handling code here:
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jf.showOpenDialog(this);
        File archivo = jf.getSelectedFile();
        if (archivo != null) {
            //System.out.println(this.frase.getText());
            Ruta.setText(archivo.getAbsolutePath());

        }
    }//GEN-LAST:event_SelectArchivoActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        //System.out.println(this.frase.getText());
        this.tamTotal=0;
        this.progress=0;
        for(int j=0;j<500000;j++){
                   // System.out.println(j);
                }
        DefaultTableModel model=(DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
        jTable1.update(jTable1.getGraphics());
        this.jProgressBar1.setMinimum(0);
        this.jProgressBar1.setMaximum(100);
        this.jProgressBar1.setValue(progress);
        String cadena=""+progress+"%";
       this.jProgressBar1.setString(cadena);
        this.jProgressBar1.repaint();
        this.jProgressBar1.update(this.jProgressBar1.getGraphics());
        
       // setCursor (Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        busqeudaArchivos(this.Ruta.getText());
        mostrarResultados();
        
        
    }//GEN-LAST:event_buscarActionPerformed

    private void fraseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fraseMouseClicked
        // TODO add your handling code here:
        if(this.insertarTextoPrimera){
            
        }else{
            this.buscar.setEnabled(true);
        this.insertarTextoPrimera=true;
        this.frase.setText("");
        this.frase.setForeground(Color.BLACK);
        }
        
       
    }//GEN-LAST:event_fraseMouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            
            String nombre=this.jTable1.getValueAt(this.jTable1.getSelectedRow(),1).toString();
           
           
            
        try{ 
        
           File path = new File (nombre);
           Desktop.getDesktop().open(path);
         
          }catch(IOException e){
             e.printStackTrace();
          }catch(IllegalArgumentException e){
             JOptionPane.showMessageDialog(null, "No se pudo encontrar el archivo","Error",JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
         }  
}
            
       
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Ruta;
    private javax.swing.JButton SelectArchivo;
    private javax.swing.JButton buscar;
    private javax.swing.JTextField frase;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
