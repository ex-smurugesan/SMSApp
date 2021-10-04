package om.apps.sms.beans;

import java.io.Serializable;

import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class InitializeUserTypePhaseListener implements Serializable {
    public InitializeUserTypePhaseListener() {

    }
    String instanceName = "";

    String userType = "";
    int i = 0;
    int j = 0;

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void beforePhase(PhaseEvent phaseEvent) {
        if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            if ((i == 0) && ((userType.equals("")) || (userType == null))) {
                boolean isEBSUser;
                i++;
                BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                OperationBinding operationBindingUserType = bindings.getOperationBinding("isEBSUser");
                isEBSUser = (Boolean) operationBindingUserType.execute();
                if (isEBSUser)
                    userType = "EBS";
                else
                    userType = "NON-EBS";
                if (!operationBindingUserType.getErrors().isEmpty()) {
               
                }

            }


            if ((j == 0) || (instanceName == null) || (instanceName.equals(""))) {
                getitemRequestURL(phaseEvent);
                j++;
                BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                OperationBinding operationBindingApplyVC = bindings.getOperationBinding("getInstanceName");
                instanceName = (String) operationBindingApplyVC.execute();
                System.out.println("Instance Name is : " + instanceName);

                System.out.println("Language Name is : " + phaseEvent.getFacesContext()
                                                                     .getCurrentInstance()
                                                                     .getViewRoot()
                                                                     .getLocale());


                if (!operationBindingApplyVC.getErrors().isEmpty()) {
                    System.out.println("## Error in Apply VC = ");
                    //return result_applyVC;
                }
            }
        }
    }

    public void getitemRequestURL(PhaseEvent phaseEvent) {
        if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            ExternalContext ectx = fctx.getExternalContext();
            HttpSession userSession = (HttpSession) ectx.getSession(true);

            String session_serverName = (String) userSession.getAttribute("serverName");

            Locale locale = ADFContext.getCurrent().getLocale();
            String str_locale = locale.toString();
            userSession.setAttribute("setLang", "US");

            if (str_locale.equals("pt_BR")) {
                userSession.setAttribute("setLang", "PTB");
            } else if (str_locale.equals("tr_TR")) {
                userSession.setAttribute("setLang", "TR");

            } else if (str_locale.equals("de_DE")) {
                userSession.setAttribute("setLang", "D");

            }


            //            if (session_serverName == null) {
            //                HttpServletRequest request =
            //                    (HttpServletRequest)ectx.getRequest();
            //                String serverName = request.getServerName();
            //                int serverPort = request.getServerPort();
            //               // System.out.println("Server name"+serverName);
            //               // System.out.println(serverPort);
            //                userSession.setAttribute("serverName", serverName);
            //                userSession.setAttribute("serverPort", serverPort);
            //                String itemRquestURL =
            //                    "http://" + serverName + ":" + "7780" + "/ADF/ItemRequestForm/faces/ItemRequest.jspx";
            //                System.out.println(itemRquestURL);
            //                userSession.setAttribute("itemRquestURL", itemRquestURL);
            //
            //            }

        }
    }
}
