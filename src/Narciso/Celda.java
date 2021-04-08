/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Narciso;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author conej
 */
public class Celda extends DefaultTableCellRenderer{

    
    String p;
    Celda(){
      
    }
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(isSelected){
            this.setBackground(table.getSelectionBackground());
            this.setForeground(table.getSelectionForeground());
        }else{
            this.setBackground(table.getBackground());
            this.setForeground(table.getForeground());
        }
        if(value!=null){
           
            setIcon(new ImageIcon(getClass().getResource("/imagenes/icon1.png")));
            setText(value.toString());
        }
        setHorizontalAlignment(JLabel.LEFT);
        return this;
    }
    
}
