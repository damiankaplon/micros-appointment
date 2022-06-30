PROJECT = security-micro
VOLUME = database-images-data
VOLUME_SECURITY = database-security-data
NETWORK_SECURITY = network-security
COMPOSE_YAML = src/main/docker/dev-env.yaml

dev-env-up:
	docker volume create --name=$(VOLUME)
	docker volume create --name=$(VOLUME_SECURITY)
	docker network create $(NETWORK_SECURITY)
	docker-compose -p $(PROJECT) -f $(COMPOSE_YAML) up -d --build --remove-orphans

run-tests:
	mvn test

dev-env-down:
	docker-compose -p $(PROJECT) -f $(COMPOSE_YAML) down -v --remove-orphans

dev-env-clean: dev-env-down
	docker volume remove $(VOLUME)
	docker volume remove $(VOLUME_SECURITY)
	docker network prune