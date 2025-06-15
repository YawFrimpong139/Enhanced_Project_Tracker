package com.codewithzea.myprojecttracker.audit;


import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getLogs(@RequestParam(required = false) String entityType,
                                                     @RequestParam(required = false) String actorName) {
        return ResponseEntity.ok(auditLogService.getLogs(entityType, actorName));
    }
}



