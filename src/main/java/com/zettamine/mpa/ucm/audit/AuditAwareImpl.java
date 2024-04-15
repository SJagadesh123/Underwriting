package com.zettamine.mpa.ucm.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuditorAware interface to provide the current auditor for auditing purposes.
 */
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * Retrieves the current auditor for auditing purposes.
     * @return An Optional containing the current auditor name.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        // Since it's an example implementation, always returns "ANONYMOUS" as the auditor.
        return Optional.of("ANONYMOUS");
    }	
}