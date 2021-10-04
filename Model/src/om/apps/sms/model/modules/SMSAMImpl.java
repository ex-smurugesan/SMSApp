package om.apps.sms.model.modules;

import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import om.apps.sms.model.modules.common.SMSAM;
import om.apps.sms.model.views.XxhceHeSmsHeadersEOVORowImpl;

import om.apps.sms.model.views.XxhceHeSmsLinesEOVORowImpl;

import om.apps.sms.model.views.XxhceHeSmsSiteMtncEOVORowImpl;

import om.apps.sms.model.views.XxhceHeSmsSiteTypeEOVORowImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.domain.Number;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Aug 23 10:35:08 IST 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SMSAMImpl extends ApplicationModuleImpl implements SMSAM {
    /**
     * This is the default constructor (do not remove).
     */
    public SMSAMImpl() {
    }
    
    
    public void initializeHdr(){
        try{
            ViewObjectImpl hdrvo=this.getXxhceHeSmsHeadersEOVO();
            XxhceHeSmsHeadersEOVORowImpl row=(XxhceHeSmsHeadersEOVORowImpl)hdrvo.createRow();
            hdrvo.setCurrentRow(row);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void createAndSave(){
        try{
            //this.getDBTransaction().commit();
            ViewObjectImpl hdrvo=this.getXxhceHeSmsHeadersEOVO();
            hdrvo.clearCache();
            XxhceHeSmsHeadersEOVORowImpl row=(XxhceHeSmsHeadersEOVORowImpl)hdrvo.createRow();
            hdrvo.setCurrentRow(row);
            hdrvo.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addDetails(){
        try{
           //added for insert 10 rows while click on ADD
           ViewObjectImpl dtlvo=this.getXxhceHeSmsLinesEOVO1();
            if(dtlvo!=null && dtlvo.getEstimatedRowCount()>0){
                 RowSetIterator rsi = dtlvo.createRowSetIterator(null);
                 if (rsi == null) {
                     rsi = dtlvo.createRowSetIterator(null);
                 }
                 rsi.reset();
                 while (rsi.hasNext()) {
                     XxhceHeSmsLinesEOVORowImpl row = (XxhceHeSmsLinesEOVORowImpl) rsi.first();
                     if(row.getSmsRequestDate()!=null || row.getItemId()!=null || row.getActivity()!=null || row.getQuantity()!=null){
                        for(int i=1; i<=10; i++){
                            XxhceHeSmsLinesEOVORowImpl newrow=(XxhceHeSmsLinesEOVORowImpl)dtlvo.createRow();                                
                            rsi.insertRow(newrow);
                            newrow.setRevision(new BigDecimal(0));
                            newrow.setTaxRate(new BigDecimal(0));
                            newrow.setTaxAmount(new BigDecimal(0));
                            newrow.setQuantity(new BigDecimal(0));
                            newrow.setListPrice(new BigDecimal(0));
                            newrow.setUnitPrice(new BigDecimal(0));
                            newrow.setSubTotal(new BigDecimal(0));
                            newrow.setTicketLineStatus("New");
                            newrow.setOrigin("SMS");
                            rsi.setCurrentRow(newrow);
                        }
                        return;
                    }
                    row=(XxhceHeSmsLinesEOVORowImpl)dtlvo.next();
                }
                 rsi.closeRowSetIterator();
             }else{
                RowSetIterator rsi = dtlvo.createRowSetIterator(null);
                if (rsi == null) {
                    rsi = dtlvo.createRowSetIterator(null);
                }
                rsi.reset();
                 for(int i=1; i<=10; i++){
                     XxhceHeSmsLinesEOVORowImpl row=(XxhceHeSmsLinesEOVORowImpl)dtlvo.createRow();                                
                     dtlvo.insertRowAtRangeIndex(rsi.getCurrentRowIndex()+1, row);
                     row.setRevision(new BigDecimal(0));
                     row.setTaxRate(new BigDecimal(0));
                     row.setTaxAmount(new BigDecimal(0));
                     row.setQuantity(new BigDecimal(0));
                     row.setListPrice(new BigDecimal(0));
                     row.setUnitPrice(new BigDecimal(0));
                     row.setSubTotal(new BigDecimal(0));
                     row.setTicketLineStatus("New");
                     row.setOrigin("SMS");
                     rsi.setCurrentRow(row);
                 } 
                rsi.closeRowSetIterator();
            }
         }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void deleteDetails(){
        try{
            //this.getDBTransaction().commit();
           ViewObjectImpl dtlvo=this.getXxhceHeSmsLinesEOVO1();
            XxhceHeSmsLinesEOVORowImpl row=(XxhceHeSmsLinesEOVORowImpl)dtlvo.getCurrentRow();
            row.remove();
         }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void clearHdrDtl(){
        try{
            //header clear
            ViewObjectImpl hdrvo=this.getXxhceHeSmsHeadersEOVO();
//            hdrvo.executeEmptyRowSet();
//            hdrvo.clearCache();
//            hdrvo.executeQuery();
            XxhceHeSmsHeadersEOVORowImpl row=(XxhceHeSmsHeadersEOVORowImpl)hdrvo.createRow();
//             
             hdrvo.setCurrentRow(row);
             hdrvo.executeQuery();
            //this.initializeHdr();
            
             //details clear
            ViewObjectImpl dtlvo=this.getXxhceHeSmsLinesEOVO1();
            dtlvo.executeEmptyRowSet();
           
         }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveValidData() {
        try {
            ViewObjectImpl dtlvo = this.getXxhceHeSmsLinesEOVO1();
            //dtlvo.executeQuery();
            RowSetIterator rsi = dtlvo.createRowSetIterator(null);
            if (rsi == null) {
                rsi = dtlvo.createRowSetIterator(null);
            }
            rsi.reset();
            while (rsi.hasNext()) {
                XxhceHeSmsLinesEOVORowImpl row = (XxhceHeSmsLinesEOVORowImpl) rsi.next();
                BigDecimal zero = new BigDecimal(0);
                if (row.getItemId() != null) {
                    if (row.getSmsRequestDate() != null && row.getSubTotal() != null &&
                        !(row.getSubTotal().compareTo(zero) == 0) && row.getActivity() != null) {
                       // if (((row.getListPrice().compareTo(row.getUnitPrice()) == 0))|| !((row.getListPrice().compareTo(row.getUnitPrice()) == 0))) {
                            if(row.getSmsNumber()==null || row.getSmsNumber().equals("")){
                                Number id = this.getSequenceNumber("XXHCE_HE_SMS_SERVICE_NO_S");
                                row.setSmsNumber(id);
                       //     }
                        } 
                    } else {
                        row.remove();
                    }
                }else{
                    row.remove();
                }
            }
            rsi.closeRowSetIterator();
            this.getDBTransaction().commit();
            //dtlvo.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Number getSequenceNumber(String seq){
        try{
            oracle.jbo.server.SequenceImpl theseq=new oracle.jbo.server.SequenceImpl(seq, getDBTransaction());
            Number seqNextval =theseq.getSequenceNumber();
            return seqNextval;
        }catch(Exception e){
           return null;
        }
    }

    public boolean isEBSUser(String userName) {
        userName="VKARNATI";
        boolean isEBSUser = false;
        DBTransaction trans = getDBTransaction();
        Statement statement = null;
        ResultSet rows;
        String plsql =
            " select user_name from apps.fnd_user where UPPER(user_name) = UPPER('" +
            userName + "')";

        statement = trans.createStatement(2);
        try {
            rows = statement.executeQuery(plsql);
            while (rows.next()) {
                String user_name = rows.getString("user_name");
                //_logger.info("user_name is : " + user_name);
                isEBSUser = true;
            }
        } catch (Exception obj) {
            //_logger.severe("Error in user name");
            //_logger.severe(obj.toString());
            obj.printStackTrace();
        } finally {
            try {if(!statement.isClosed()){
                statement.close();}
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return isEBSUser;
    }
    public String getInstanceName() {

        String instanceName = "";
        DBTransaction trans = getDBTransaction();
        Statement statement = null;
        ResultSet rows;
        String plsql = " select * from v$instance";

        statement = trans.createStatement(2);
        try {
            rows = statement.executeQuery(plsql);
            while (rows.next()) {
                instanceName = rows.getString("instance_name");
                //_logger.info("Instance Name is : " + instanceName);
            }
        } catch (Exception obj) {
            //_logger.severe("Error in instance name");
            //_logger.severe(obj.toString());
            obj.printStackTrace();
        } finally {
            try {if(!statement.isClosed()){
                statement.close();}
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return instanceName;

    }

    public void addSetup() {
        try {
            ViewObjectImpl setupvo = this.getXxhceHeSmsSiteMtncEOVO();
            XxhceHeSmsSiteMtncEOVORowImpl row = (XxhceHeSmsSiteMtncEOVORowImpl) setupvo.createRow();
            setupvo.insertRowAtRangeIndex(setupvo.getCurrentRowIndex() + 1, row);
            setupvo.setCurrentRow(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveSetup() {
        try {
            ViewObjectImpl setupvo = this.getXxhceHeSmsSiteMtncEOVO();
            RowSetIterator rsi = setupvo.createRowSetIterator(null);
            if (rsi == null) {
                rsi = setupvo.createRowSetIterator(null);
            }
            rsi.reset();
            while (rsi.hasNext()) {
                XxhceHeSmsSiteMtncEOVORowImpl row = (XxhceHeSmsSiteMtncEOVORowImpl) rsi.next();
                if(row.getSiteId() == null){
                    Number id = this.getSequenceNumber("XXHCE_HE_SMS_SITE_MTNC_TBL_S");
                    row.setSiteId(id);
                }
            }
            rsi.closeRowSetIterator();
            this.getDBTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addTypes() {
        try {
            ViewObjectImpl typevo = this.getXxhceHeSmsSiteTypeEOVO();
            XxhceHeSmsSiteTypeEOVORowImpl row = (XxhceHeSmsSiteTypeEOVORowImpl) typevo.createRow();
            typevo.insertRowAtRangeIndex(typevo.getCurrentRowIndex() + 1, row);
            typevo.setCurrentRow(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteTypes(){
        try{
            //this.getDBTransaction().commit();
            ViewObjectImpl typevo = this.getXxhceHeSmsSiteTypeEOVO();
            XxhceHeSmsSiteTypeEOVORowImpl row=(XxhceHeSmsSiteTypeEOVORowImpl)typevo.getCurrentRow();
            row.remove();
         }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Container's getter for XxhceHeSmsHeadersEOVO1.
     * @return XxhceHeSmsHeadersEOVO1
     */
    public ViewObjectImpl getXxhceHeSmsHeadersEOVO() {
        return (ViewObjectImpl) findViewObject("XxhceHeSmsHeadersEOVO");
    }


    /**
     * Container's getter for XxhceHeSmsLinesEOVO1.
     * @return XxhceHeSmsLinesEOVO1
     */
    public ViewObjectImpl getXxhceHeSmsLinesEOVO1() {
        return (ViewObjectImpl) findViewObject("XxhceHeSmsLinesEOVO1");
    }

    /**
     * Container's getter for XxhceHeSmsHeadersVOToXxhceHeSmsLinesVOVL1.
     * @return XxhceHeSmsHeadersVOToXxhceHeSmsLinesVOVL1
     */
    public ViewLinkImpl getXxhceHeSmsHeadersVOToXxhceHeSmsLinesVOVL1() {
        return (ViewLinkImpl) findViewLink("XxhceHeSmsHeadersVOToXxhceHeSmsLinesVOVL1");
    }


    /**
     * Container's getter for XxhceHeSmsSiteMtncEOVO1.
     * @return XxhceHeSmsSiteMtncEOVO1
     */
    public ViewObjectImpl getXxhceHeSmsSiteMtncEOVO() {
        return (ViewObjectImpl) findViewObject("XxhceHeSmsSiteMtncEOVO");
    }

    /**
     * Container's getter for XxhceHeSmsSiteTypeEOVO1.
     * @return XxhceHeSmsSiteTypeEOVO1
     */
    public ViewObjectImpl getXxhceHeSmsSiteTypeEOVO() {
        return (ViewObjectImpl) findViewObject("XxhceHeSmsSiteTypeEOVO");
    }

    /**
     * Container's getter for XxhceHeSmsSiteMtncVOToXxhceHeSmsSiteTypeVOVL1.
     * @return XxhceHeSmsSiteMtncVOToXxhceHeSmsSiteTypeVOVL1
     */
    public ViewLinkImpl getXxhceHeSmsSiteMtncVOToXxhceHeSmsSiteTypeVOVL() {
        return (ViewLinkImpl) findViewLink("XxhceHeSmsSiteMtncVOToXxhceHeSmsSiteTypeVOVL");
    }
}
