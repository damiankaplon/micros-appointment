PROJECT = security-micro
VOLUME = database-security-data
COMPOSE_YAML = src/main/docker/dev-env.yaml

dev-env-up:
	docker volume create --name=$(VOLUME)
	docker-compose -p $(PROJECT) -f $(COMPOSE_YAML) up -d --build --remove-orphans

run-tests:
	mvn surefire:test

dev-env-down:
	docker-compose -p $(PROJECT) -f $(COMPOSE_YAML) down -v --remove-orphans

dev-env-clean: dev-env-down
	docker volume remove $(VOLUME)