PROJECT = appointment-micros
SECURITY_MICRO_DB_VOLUME = database-security-data
COMPOSE_DEV_YAML = docker/dev-env.yaml
COMPOSE_TEST_YAML = docker/test-env.yaml

dev-env-up:
	docker volume create --name=$(SECURITY_MICRO_DB_VOLUME)
	docker-compose -p $(PROJECT) -f $(COMPOSE_DEV_YAML) up -d --build --remove-orphans

setup-tests:
	docker volume create --name=$(SECURITY_MICRO_DB_VOLUME)
	docker-compose -p $(PROJECT) -f $(COMPOSE_TEST_YAML) up -d --build --remove-orphans

run-tests: setup-tests
	mvn test

dev-env-down:
	docker-compose -p $(PROJECT) -f $(COMPOSE_DEV_YAML) down -v --remove-orphans

dev-env-clean: dev-env-down
	docker volume remove $(SECURITY_MICRO_DB_VOLUME)