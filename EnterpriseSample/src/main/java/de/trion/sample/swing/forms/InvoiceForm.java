package de.trion.sample.swing.forms;

import de.trion.sample.controller.InvoiceController;
import de.trion.sample.model.Invoice;
import de.trion.sample.service.InvoiceService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class InvoiceForm extends javax.swing.JPanel
{
    private InvoiceService invoiceService;
    private InvoiceController invoiceController;
    private Map<Integer, Invoice> invoiceMap;
    
    /**
     * Creates new form InvoiceForm
     */
    public InvoiceForm(InvoiceService invoiceService, InvoiceController invoiceController)
    {
        this.invoiceService = invoiceService;
        this.invoiceController = invoiceController;
        
        invoiceController.registerChangeListener(this);
        
        initComponents();
        updateModel();
        jTable1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();

                if(column != 3)
                {
                    return;
                }
                Invoice invoice = invoiceMap.get(row);
                invoiceController.markPaid(invoice);
            }
        });
    }

    final public void updateModel()
    {
        TableModel model = jTable1.getModel();
        
        List<Invoice> invoices = invoiceService.getInvoices();
        invoiceMap = new HashMap<>();
        for (int i = 0; i < invoices.size(); i++)
        {
            Invoice invoice = invoices.get(i);
            invoiceMap.put(i, invoice);
            
            model.setValueAt(invoice.getNumber(), i, 0);
            model.setValueAt(invoice.getDate(), i, 1);
            model.setValueAt(invoice.getCompany(), i, 2);
            if(invoice.isPaid())
            {
                model.setValueAt("paid", i, 3);
            }
            else
            {
                model.setValueAt("mark paid", i, 3);
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
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Number", "Date", "Company", "Status"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
