

package com.tothenew.ecommerce.event;

import com.tothenew.ecommerce.entity.AuditRevisionEntity;
import com.tothenew.ecommerce.services.CurrentUserService;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
public class AuditRevisionListener implements RevisionListener {
    @Autowired
    CurrentUserService currentUserService;

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;

        audit.setUsername(currentUserService.getUser());
    }
}
