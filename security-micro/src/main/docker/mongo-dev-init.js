db = db.getSiblingDB('database-security');
db.createCollection('accounts');
db.createUser({
    user: 'sa',
    pwd: 'sa',
    roles: [
        {
            role: 'dbOwner',
            db: 'database-security',
        },
    ],
});

db.accounts.insertMany([
    {
        email: 'email',
        password: 'password',
        name: 'name',
        surname: 'surname',
        groups: ["ADMIN"]
    }
]);

