db = db.getSiblingDB('database-security');
db.createCollection('accounts');
db.createUser(
    {
        user: "admin",
        pwd: "admin",
        roles: [
            {
                role: "readWrite",
                db: "database-security"
            }
        ]
    }
);
