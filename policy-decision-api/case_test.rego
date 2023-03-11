package wks.authz

import future.keywords

test_all_methods_allowed_when_user_profile if {
    allow with input as { 
        "realm_access": { "roles": ["user"] },
        "host": "app.wkspower.local",
        "method": "GET",
        "path": "case"
    }
    allow with input as { 
        "realm_access": { "roles": ["user"] },
        "host": "app.wkspower.local",
        "method": "POST",
        "path": "case"
    }
    allow with input as { 
        "realm_access": { "roles": ["user"] },
        "host": "app.wkspower.local",
        "method": "DELETE",
        "path": "case"
    }
    allow with input as { 
        "realm_access": { "roles": ["user"] },
        "host": "app.wkspower.local",
        "method": "OPTION",
        "path": "case"
    }
    allow with input as { 
        "realm_access": { "roles": ["user"] },
        "host": "app.wkspower.local",
        "method": "HEAD",
        "path": "case"
    }
}