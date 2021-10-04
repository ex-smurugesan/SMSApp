package om.apps.sms.beans;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import om.apps.sms.view.utils.ADFUtils;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewRowImpl;

public class ServiceBean implements Serializable{
    @SuppressWarnings("oracle.jdeveloper.java.field-not-serializable")
    private RichPopup deletePopupProp;
    @SuppressWarnings("oracle.jdeveloper.java.field-not-serializable")
    private RichPopup saveMissingPopupProp;
    private RichTable linesTBLProp;

    public ServiceBean() {
    }
    public void init(){
        OperationBinding op=ADFUtils.findOperation("initializeHdr");
        op.execute();
    }

    public void saveAL(ActionEvent actionEvent) {
       ViewObject vo=ADFUtils.findIterator("XxhceHeSmsLinesEOVO1Iterator").getViewObject();
       if (vo != null && vo.getEstimatedRowCount() > 0) {
           this.saveandValidateRows();
           //save();
        } else {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                 ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle","PLEASE_CREATE_ATLEAST_ONE_DETAILS_RECORD"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
    }
    
    public void saveandValidateRows(){
        String status="";
        ViewObject vo=ADFUtils.findIterator("XxhceHeSmsLinesEOVO1Iterator").getViewObject();
        if (vo != null && vo.getEstimatedRowCount() > 0) {
            StringBuilder sb = new StringBuilder("<html><body>");
             RowSetIterator rsi = vo.findRowSetIterator("cleanIterator");
             if (rsi == null) {
                 rsi = vo.createRowSetIterator("cleanIterator");
             }
             rsi.reset();
             int i=0;             
             while (rsi.hasNext()) {
                 ViewRowImpl row = (ViewRowImpl) rsi.next();
                 if (row != null) {
                     
                     BigDecimal itemId = (BigDecimal) row.getAttribute("ItemId");
                     String itemName = (String) row.getAttribute("ItemIdTr");
                     java.sql.Timestamp rDate = (java.sql.Timestamp) row.getAttribute("SmsRequestDate");
                     String activity = (String) row.getAttribute("Activity");
                     BigDecimal total = (BigDecimal) row.getAttribute("SubTotal");
                     BigDecimal zero = new BigDecimal(0);
                     if (itemId != null) {
                        if (rDate == null || (total == null || total.compareTo(zero) == 0) ||
                            (activity == null || activity.equalsIgnoreCase(""))) {
                            i++;
                            if (rDate == null) {
                                                                sb.append("<p>" +"Line "+ i +" . "+
                                                                          ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle",
                                                                                                        "SERVICE_DATE") + ",");
                               // sb.append("<p>" + "SERVICE_DATE" + ",");
                            }
                            if (total.compareTo(zero) == 0) {
                                
                                if(rDate != null){
                                    sb.append(" " + "Line "+ i +" . "+
                                              ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle",
                                                                            "QUANTITY")+" ");
                                }else{
                                    sb.append(" " + 
                                              ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle",
                                                                            "QUANTITY")+" ");
                                }
                                                               
                                //sb.append(" " + "QUANTITY");
                            }
                            sb.append(ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle",
                                                                                                "IS_REQUIRED_FOR_THE_SALES_ITEM")+itemName);
                            //sb.append("IS_REQUIRED_FOR" + itemId);
                            sb.append("</p>");
                            status = "MISSING";
                            //this.getSaveMissingPopupProp().show(new RichPopup.PopupHints());
                            //return;
                            
                        } else{
                            
                             oracle.jbo.domain.Number smsNumber=(oracle.jbo.domain.Number)row.getAttribute("SmsNumber");
                             if(null==smsNumber || smsNumber.equals("")){
                                 OperationBinding op=ADFUtils.findOperation("getSequenceNumber");
                                 op.getParamsMap().put("seq", "XXHCE_HE_SMS_SERVICE_NO_S");
                                 oracle.jbo.domain.Number  id=(oracle.jbo.domain.Number)op.execute(); 
                                 row.setAttribute("SmsNumber", id);
                             }
                            i++;
                             
                         }
                      
                     }else{
                         row.remove();
                     }
                 }
             }
             rsi.closeRowSetIterator();
             if(status.equalsIgnoreCase("MISSING")){
                 sb.append("</body></html>");
                 ADFUtils.showFacesMessage(sb.toString(),
                                          FacesMessage.SEVERITY_ERROR); 
                return;
             }else{
                 save();
             }
             
             
//             Row[] filteredRows = vo.getFilteredRows("ItemId", null);
//             if (filteredRows.length > 0) {
//                 for (int i = 0; i < filteredRows.length; i++) {
//                     ViewRowImpl itemrow = (ViewRowImpl) filteredRows[i];
//                     itemrow.remove();
//                 }
//             }
             //save();
             //vo.executeQuery();
             
         } else {
             FacesMessage msg =
                 new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                  ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle","PLEASE_CREATE_ATLEAST_ONE_DETAILS_RECORD"));
             FacesContext.getCurrentInstance().addMessage(null, msg);
             return;
         }
    }

    public void createSaveAL(ActionEvent actionEvent) {        
        this.saveandValidateRows();
        OperationBinding op=ADFUtils.findOperation("createAndSave");
        op.execute();
    }

    public void addDetailAL(ActionEvent actionEvent) {
        try{
            OperationBinding op=ADFUtils.findOperation("addDetails");
            op.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
       
    }

    public void deleteDetailAL(ActionEvent actionEvent) {
        this.getDeletePopupProp().show(new RichPopup.PopupHints());
    }

    public void clearAL(ActionEvent actionEvent) {
        OperationBinding op=ADFUtils.findOperation("clearHdrDtl");
        op.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getLinesTBLProp().getParent());
    }

    public void setDeletePopupProp(RichPopup deletePopupProp) {
        this.deletePopupProp = deletePopupProp;
    }

    public RichPopup getDeletePopupProp() {
        return deletePopupProp;
    }

    public void cancelConfirmAL(ActionEvent actionEvent) {
        this.getDeletePopupProp().hide();
    }

    public void deleteConfirmAL(ActionEvent actionEvent) {
        OperationBinding op=ADFUtils.findOperation("deleteDetails");
        op.execute();
        this.getDeletePopupProp().hide();
    }
    public static DCBindingContainer getDCBindingContainer(){
        return (DCBindingContainer)getDCBindingContainer();
    }

    public void setSaveMissingPopupProp(RichPopup saveMissingPopupProp) {
        this.saveMissingPopupProp = saveMissingPopupProp;
    }

    public RichPopup getSaveMissingPopupProp() {
        return saveMissingPopupProp;
    }

    public void confirmSaveAL(ActionEvent actionEvent) {
                OperationBinding op=ADFUtils.findOperation("saveValidData");
                op.execute();
                this.getSaveMissingPopupProp().hide();
    }

    public void cancelConfirmSaveAL(ActionEvent actionEvent) {
        ViewObject vo=ADFUtils.findIterator("XxhceHeSmsLinesEOVO1Iterator").getViewObject();
        this.getSaveMissingPopupProp().hide();
    }
    public void save(){
        OperationBinding op=ADFUtils.findOperation("Commit");
        op.execute();
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                             ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle","TRANSACTION_SUCCESSFULLY_SAVED"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }

    public void setLinesTBLProp(RichTable linesTBLProp) {
        this.linesTBLProp = linesTBLProp;
    }

    public RichTable getLinesTBLProp() {
        return linesTBLProp;
    }
}
