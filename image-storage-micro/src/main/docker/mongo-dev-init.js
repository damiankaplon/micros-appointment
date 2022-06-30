db = db.getSiblingDB('database-images');
db.createCollection('images');
db.createUser({
    user: 'sa',
    pwd: 'sa',
    roles: [
        {
            role: 'dbOwner',
            db: 'database-images',
        },
    ],
});

