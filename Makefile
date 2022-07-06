PROJECT = appointment-micros
SECURITY_MICRO_DB_VOLUME = database-security-data
IMAGE_STORAGE_MICRO_DB_VOLUME = database-images-data
IMAGE_STORAGE_MICRO_VOLUME = image-storage-volume
NETWORK_SECURITY = network-security
NETWORK_IMAGE_STORAGE = network-image-storage
COMPOSE_DEV_YAML = docker/dev-env.yaml
COMPOSE_TEST_YAML = docker/test-env.yaml

dev-env-setup:
	docker volume create --name=$(SECURITY_MICRO_DB_VOLUME)
	docker volume create --name=$(IMAGE_STORAGE_MICRO_DB_VOLUME)
	docker volume create --name=$(IMAGE_STORAGE_MICRO_VOLUME)
	docker network create $(NETWORK_SECURITY)
	docker network create $(NETWORK_IMAGE_STORAGE)
	docker-compose -p $(PROJECT) -f $(COMPOSE_DEV_YAML) up -d --build --remove-orphans

dev-env-up:
	docker-compose -p $(PROJECT) -f $(COMPOSE_DEV_YAML) up -d --build --remove-orphans

setup-tests:
	docker volume create --name=$(SECURITY_MICRO_DB_VOLUME)
	docker-compose -p $(PROJECT) -f $(COMPOSE_TEST_YAML) up -d --build --remove-orphans

run-tests: setup-tests
	mvn --no-transfer-progress test

dev-env-down:
	docker-compose -p $(PROJECT) -f $(COMPOSE_DEV_YAML) down -v --remove-orphans

dev-env-clean: dev-env-down
	docker volume remove $(SECURITY_MICRO_DB_VOLUME)
	docker volume remove $(IMAGE_STORAGE_MICRO_VOLUME)
	docker volume remove $(IMAGE_STORAGE_MICRO_DB_VOLUME)
	docker network prune
