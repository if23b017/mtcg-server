**Verwendung von Docker:**
1. Docker installieren
2. PostgreSQL Container starten: 
- Verwende diesen Befehl um den PostgreSQL Container zu starten:
```sh
docker run -d --rm --name posgtresdb -e POSTGRES_USER=user1 -e POSTGRES_PASSWORD=Password12345 -e POSGTRES_DB=mtcg -p 5432:5432 -v pgdata:/var/lib/postgresql/data postgres 
```
3. Mit der Datenbank verbinden:
- Verwende diesen Befehl um dich mit der Datenbank zu verbinden:
```sh
docker exec -it posgtresdb psql -U user1 -d mtcg
```
4. Datenbank-Columns erstellen:
- Führe folgenden Befehl aus, um die `users`-Tabelle zu erstellen:
```sql
CREATE TABLE users (id SERIAL PRIMARY KEY, username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL, coins INT DEFAULT 20, token VARCHAR(255));
```

**Erstellung der `config.properties` Datei:**
1. Eine neue Datei mit dem Name `config.properties` im Ordner `src/main/resources` erstellen.
2. Folgenden Inhalt in die Datei einfügen:
```properties
db.url=jdbc:postgresql://localhost:5432/mtcg
db.user=user1
db.password=Password12345
```