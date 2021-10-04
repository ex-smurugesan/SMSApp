package om.apps.sms.model.entities;

import oracle.jbo.AlreadyLockedException;
import oracle.jbo.RowAlreadyDeletedException;
import oracle.jbo.RowInconsistentException;
import oracle.jbo.server.EntityImpl;

public class SMSEntityImpl extends EntityImpl{
    public void lock() {

        try {
            super.lock();
        } catch (RowInconsistentException e) {
            refresh(REFRESH_WITH_DB_ONLY_IF_UNCHANGED | REFRESH_WITH_DB_FORGET_CHANGES | REFRESH_REMOVE_NEW_ROWS | REFRESH_CONTAINEES);
            super.lock();
        } 
//        catch (AlreadyLockedException e) {
//            refresh(REFRESH_WITH_DB_FORGET_CHANGES | REFRESH_REMOVE_NEW_ROWS | REFRESH_CONTAINEES);
//            throw e;
//        } catch (RowAlreadyDeletedException e) {
//            throw e;
//        }

    }
}
