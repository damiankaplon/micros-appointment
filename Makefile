PROJECT = appointment-micros
SECURITY_MICRO_DB_VOLUME = database-securit-data
COMPOSE_YAML = docker/dev-env.yaml

dev-env-up:
	docker volume create --name=$(SECURITY_MICRO_DB_VOLUME)
	docker-compose -p $(PROJECT) -f $(COMPOSE_YAML) up -d --build --remove-orphans

run-tests:
	mvn surefire:test

dev-env-down:
	docker-compose -p $(PROJECT) -f $(COMPOSE_YAML) down -v --remove-orphans

dev-env-clean: dev-env-down
	docker volume remove $(SECURITY_MICRO_DB_VOLUME)