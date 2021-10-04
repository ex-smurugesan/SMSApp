package om.apps.sms.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import om.apps.sms.view.utils.ADFUtils;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewRowImpl;

public class SetupBean implements Serializable{
    private RichPopup typePopupProp;
    private RichTable setupTBLProp;

    public SetupBean() {
    }

    public void addSetupAL(ActionEvent actionEvent) {
        try{
            OperationBinding op=ADFUtils.findOperation("addSetup");
            op.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveAL(ActionEvent actionEvent) {
        OperationBinding op=ADFUtils.findOperation("saveSetup");
        op.execute();
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                             ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle","TRANSACTION_SUCCESSFULLY_SAVED"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void typesAL(ActionEvent actionEvent) {
        this.getTypePopupProp().show(new RichPopup.PopupHints());
    }

    public void setTypePopupProp(RichPopup typePopupProp) {
        this.typePopupProp = typePopupProp;
    }

    public RichPopup getTypePopupProp() {
        return typePopupProp;
    }

    public void addTypeAL(ActionEvent actionEvent) {
        try{
            OperationBinding op=ADFUtils.findOperation("addTypes");
            op.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteTypeAL(ActionEvent actionEvent) {
        try{
            OperationBinding op=ADFUtils.findOperation("deleteTypes");
            op.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void duplicateSiteCheckAL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (null != vce.getNewValue() || !(vce.getNewValue().equals(""))) {
            System.out.println("vce.getNewValue()"+vce.getNewValue());
            ViewObject vo = ADFUtils.findIterator("XxhceHeSmsSiteMtncEOVOIterator").getViewObject();
            ViewRowImpl row = (ViewRowImpl) vo.getCurrentRow();
            Row[] filteredRows = vo.getFilteredRows("OrganizationCode", vce.getNewValue());
            if (filteredRows.length > 0) {
                for (int i = 1; i < filteredRows.length; i++) {
                    System.out.println("Filtered rows");
                    System.out.println("row.getAttribute(\"OrganizationCode\")" + row.getAttribute("OrganizationCode"));
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                         ADFUtils.getMessageFromBundle("om.apps.sms.bundle.viewcontrollerBundle",
                                                                       "SITE_CODE_ALREADY_EXIST"));
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    row.setAttribute("OrganizationCode", null);
                    row.setAttribute("OrganizationId", null);
                    row.setAttribute("SiteDesc", null);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getSetupTBLProp().getParent());
            
        }
    }

    public void setSetupTBLProp(RichTable setupTBLProp) {
        this.setupTBLProp = setupTBLProp;
    }

    public RichTable getSetupTBLProp() {
        return setupTBLProp;
    }
}
